package com.example.brorentalapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.brorentalapp.BroRentalApplication
import com.example.brorentalapp.R
import com.example.brorentalapp.databinding.FragmentSplashScreenBinding
import com.example.brorentalapp.utils.SessionConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Pending project not completed the project will have to be made in just 7 days but it uis not completed
class SplashScreenFragment : Fragment() {

    private var  _binding: FragmentSplashScreenBinding?=null
    private  val binding get()=_binding!!
    lateinit var app: BroRentalApplication
    lateinit var sessionConfig: SessionConfig



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding=FragmentSplashScreenBinding.inflate(inflater,container,false)
         val apps = requireActivity().application as BroRentalApplication
        app = apps
         sessionConfig = app.sharedPref

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(app.sharedPref.getFirstTime()){

            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                findNavController().navigate(R.id.action_splashScreenFragment_to_startSliderFragment)
            }
        }else if(!app.sharedPref.getLogin()){
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
            }
        }else if(!app.sharedPref.getTermConditions()){
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                findNavController().navigate(R.id.action_splashScreenFragment_to_termsAndconditionsFragment)
            }
        }
        else{
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)
                findNavController().navigate(R.id.action_splashScreenFragment_to_dashBoardFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}