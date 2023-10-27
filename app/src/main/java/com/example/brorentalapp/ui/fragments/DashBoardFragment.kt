package com.example.brorentalapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.brorentalapp.R
import com.example.brorentalapp.BroRentalApplication
import com.example.brorentalapp.databinding.FragmentDashBoardBinding


class DashBoardFragment : Fragment() {

    private var _binding : FragmentDashBoardBinding?=null
    private  val binding get()=_binding!!

    lateinit var app: BroRentalApplication

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding=FragmentDashBoardBinding.inflate(inflater,container,false)

        app=BroRentalApplication()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
}