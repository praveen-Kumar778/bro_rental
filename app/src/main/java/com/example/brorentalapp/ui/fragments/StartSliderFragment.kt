package com.example.brorentalapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController

import com.example.brorentalapp.BroRentalApplication
import com.example.brorentalapp.R
import com.example.brorentalapp.databinding.FragmentStartSliderBinding
import com.example.brorentalapp.utils.SessionConfig
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage


class StartSliderFragment : Fragment() {
    private var _binding: FragmentStartSliderBinding? = null
    private val binding get() = _binding!!

    private lateinit var fragmentManager: FragmentManager
    lateinit var app: BroRentalApplication
    lateinit var sessionConfig: SessionConfig



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStartSliderBinding.inflate(inflater, container, false)
         app = requireActivity().application as BroRentalApplication
         sessionConfig = app.sharedPref



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentManager = childFragmentManager
        val paperOnboardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding())
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.frame_layout, paperOnboardingFragment)
        fragmentTransaction.commit()


        binding.skipBtn.setOnClickListener{
            findNavController().navigate(R.id.action_startSliderFragment_to_loginFragment)
            app.sharedPref.setFirstTime(false)

        }

    }


    private fun getDataForOnboarding(): ArrayList<PaperOnboardingPage> {
        val source = PaperOnboardingPage(
            "Bro Rent Partner",
            "Welcome to Our App",
            Color.parseColor("#ffb174"),
            R.drawable.brorental_logo,
            R.drawable.circle_green
        )
        val source1 = PaperOnboardingPage(
            "Again ",
            "Something",
            Color.parseColor("#22eaaa"),
            R.drawable.brorental_logo,
            R.drawable.circle_green
        )
        val source2 = PaperOnboardingPage(
            "",
            " ",
            Color.parseColor("#ee5a5a"),
            R.drawable.brorental_logo,
            R.drawable.circle_green
        )


        val elements = ArrayList<PaperOnboardingPage>()
        elements.add(source)
        elements.add(source1)
        elements.add(source2)
        return elements
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}