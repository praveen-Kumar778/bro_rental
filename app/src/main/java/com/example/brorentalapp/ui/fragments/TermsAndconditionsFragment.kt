package com.example.brorentalapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brorentalapp.BroRentalApplication
import com.example.brorentalapp.DashboardActivity
import com.example.brorentalapp.R
import com.example.brorentalapp.databinding.FragmentTermsAndconditionsBinding
import com.example.brorentalapp.utils.SessionConfig

class TermsAndconditionsFragment : Fragment() {

    private var _binding: FragmentTermsAndconditionsBinding? = null
    private val binding get() = _binding!!

    
    lateinit var sessionConfig: SessionConfig
    lateinit var app: BroRentalApplication


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTermsAndconditionsBinding.inflate(inflater, container, false)
        app = requireActivity().application as BroRentalApplication
        sessionConfig = app.sharedPref
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitBtn.setOnClickListener{
            if(!binding.termsRBtn.isChecked){
                Toast.makeText(requireContext(),"please accept Terms and Conditions",Toast.LENGTH_SHORT).show()
            }else{
                sessionConfig.setTermConditions(true)
                val intent: Intent = Intent(context,DashboardActivity::class.java)
                startActivity(intent)

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}