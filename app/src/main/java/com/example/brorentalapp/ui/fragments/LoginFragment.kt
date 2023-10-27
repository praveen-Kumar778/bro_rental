package com.example.brorentalapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brorentalapp.BroRentalApplication
import com.example.brorentalapp.R
import com.example.brorentalapp.databinding.FragmentLoginBinding
import com.example.brorentalapp.utils.SessionConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    var number : String =""
    // create instance of firebase auth
    lateinit var auth: FirebaseAuth

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var app: BroRentalApplication
    lateinit var sessionConfig: SessionConfig


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
         app = requireActivity().application as BroRentalApplication
         sessionConfig = app.sharedPref
        auth=FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

   /*     callbacks=object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                Log.d("GFG" , "onVerificationCompleted Success")

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.d("GFG" , "onVerificationFailed  $p0")
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                Log.d("GFG","onCodeSent: $p0")
                storedVerificationId = p0
                resendToken = p1



            }

        }*/

        binding.nextBtn.setOnClickListener{

            login()
        }


    }

    private fun login() {
        number = binding.eTMobileNumber.text.toString()

        if (number.length == 10) {
            sessionConfig.setPhone(number)
            findNavController().navigate(R.id.action_loginFragment_to_OTPScreenFragment)

        } else {
            Toast.makeText(requireContext(), "Please Enter valid phone Number!", Toast.LENGTH_SHORT)
                .show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}