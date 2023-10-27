package com.example.brorentalapp.ui.fragments

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brorentalapp.BroRentalApplication
import com.example.brorentalapp.R
import com.example.brorentalapp.databinding.FragmentOTPScreenBinding
import com.example.brorentalapp.utils.SessionConfig
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit


class OTPScreenFragment : Fragment() {

    private var _binding: FragmentOTPScreenBinding? = null
    private val binding get() = _binding!!
    private val TAG:String="OTPScreenFragment__"

    private lateinit var mAuth: FirebaseAuth
    private var mVerificationId: String? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null
    private lateinit var phone: String
    private lateinit var referCode: String
    private lateinit var dialog: ProgressDialog
    private lateinit var t: Thread
    private lateinit var mFirestore: FirebaseFirestore
    lateinit var app: BroRentalApplication
    lateinit var sessionConfig: SessionConfig

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOTPScreenBinding.inflate(inflater, container, false)
        app = requireActivity().application as BroRentalApplication
        sessionConfig = app.sharedPref

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
      //  phone = "+91 " + arguments?.getString("phone")
        phone = "+91 " + sessionConfig.getPhone()


        binding.phone2.text = phone
        binding.phone2.visibility = View.VISIBLE
        binding.login.isEnabled = false

        dialog = ProgressDialog(requireContext())
        mFirestore = FirebaseFirestore.getInstance()

        binding.otp.addTextChangedListener(mTextWatcher)

        if (isNetworkConnected()) {
            sendOtp(phone)
            resendCountFunction()
        } else {
            openAlertDialog1()
        }


        binding.login.setOnClickListener {
            val code = binding.otp.text.toString().trim()
            if (code.isEmpty() || code.length < 6) {
                binding.otp.error = "Enter valid code"
                binding.otp.requestFocus()
                return@setOnClickListener
            }
            dialog.setMessage("Please wait...")
            verifyVerificationCode(code)
        }

        binding.resendOtp.setOnClickListener {
            binding.resendOTPTV.visibility = View.GONE
            binding.resendOtp.visibility = View.GONE
            resendCountFunction()
            Toast.makeText(requireContext(), "Resending OTP", Toast.LENGTH_SHORT).show()
            sendOtp(phone)
        }

    }

    private val mTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (binding.otp.text.toString().length == 6) {
                binding.login.isEnabled = true
            }
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }

    private fun resendCountFunction() {
        binding.otpTimer.visibility = View.VISIBLE
        t = object : Thread() {
            override fun run() {
                for (i in 0 until 30) {
                    try {
                        val a = i
                        requireActivity().runOnUiThread {
                            if (a == 29) {
                                binding.otpTimer.visibility = View.GONE
                                binding.resendOtp.visibility = View.VISIBLE
                                binding.resendOTPTV.visibility = View.VISIBLE
                                interrupt()
                            }
                            binding.otpTimer.text = "Resend Code in " + (30 - (a + 1))
                        }
                        sleep(1000)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        t.start()
    }
    private fun sendOtp(phone: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            val code = credential.smsCode
            Log.v("OtpActivity.java", code + "")
            if (dialog.isShowing) {
                dialog.dismiss()
            }
            if (code != null) {
                dialog.setMessage("Wait while generating account")
                dialog.show()
                binding.otp.setText(code)
                verifyVerificationCode(code)
            } else {
                binding.login.isEnabled = true
            }

        }


        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)
            if (dialog.isShowing) dialog.dismiss()
            if (e is FirebaseAuthInvalidCredentialsException) {
                Toast.makeText(requireContext(), "Invalid request", Toast.LENGTH_SHORT).show()
            } else if (e is FirebaseTooManyRequestsException) {
                Toast.makeText(requireContext(), "Otp limits exceeded", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            Log.d(TAG, "onCodeSent:$verificationId")
            if (dialog.isShowing) {
                dialog.dismiss()
            }
            Toast.makeText(requireContext(), "Otp sent on $phone successfully", Toast.LENGTH_SHORT).show()
            mVerificationId = verificationId
            mResendToken = token
        }
    }

    private fun verifyVerificationCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(mVerificationId.orEmpty(), code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(), OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    if (isNetworkConnected()) {
                        isUserExist(phone)
                    } else {
                        openAlertDialog()
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "OTP is invalid", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun openAlertDialog1() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Check network connection!")
        builder.setCancelable(false)
        builder.setPositiveButton("Okay") { dialogInterface, i ->
            if (isNetworkConnected()) {
                Toast.makeText(requireContext(), "Network connected successfully", Toast.LENGTH_SHORT).show()
                sendOtp(phone)
                resendCountFunction()
            } else {
                openAlertDialog1()
            }
        }
        builder.create().show()
    }

    private fun openAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Check network connection!")
        builder.setCancelable(false)
        builder.setPositiveButton("Okay") { dialogInterface, i ->
            if (isNetworkConnected()) {
                isUserExist(phone)
                Toast.makeText(requireContext(), "Network connected successfully", Toast.LENGTH_SHORT).show()
            } else {
                openAlertDialog()
            }
        }
        builder.create().show()
    }

    private fun isUserExist(phone: String) {
        dialog.show()
        mFirestore.collection("users")
            .whereEqualTo("mobile", phone)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && !task.result.isEmpty) {
                    Toast.makeText(requireContext(), "Old users can't redeem referral code!", Toast.LENGTH_LONG).show()
                   /* val i = Intent(requireContext(), MainActivity::class.java)
                    i.putExtra("phone", phone)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    requireActivity().finish()*/
                    findNavController().navigate(R.id.action_OTPScreenFragment_to_termsAndconditionsFragment)
                    sessionConfig.setLogin(true)
                    dialog.dismiss()
                } else if (task.isSuccessful) {
                    try {
                       /* val i = Intent(requireContext(), MainActivity::class.java)
                        i.putExtra("phone", phone)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(i)
                        requireActivity().finish()*/
                        findNavController().navigate(R.id.action_OTPScreenFragment_to_termsAndconditionsFragment)
                        dialog.dismiss()
                        sessionConfig.setLogin(true)
                        Toast.makeText(requireContext(), "Sign-Up done Successfuly", Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Log.v("OtpActivity.java", e.toString())
                    }
                }
            }
    }

    private fun isNetworkConnected(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
    //    return networkInfo?.state == NetworkInfo.State.CONNECTED || networkInfo?.state == NetworkInfo.State.CONNECTED

        return true
    }


}