package com.example.brorentalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class RentFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rent, container, false)
        // Set up the TabLayout and ViewPager
        val tabLayout: TabLayout = view.findViewById(R.id.tab_frame)
        val viewPager: ViewPager2 = view.findViewById(R.id.pager_frame)
        val adapter = TabLayoutPagerAdaptor(requireActivity().supportFragmentManager, lifecycle)
        tabLayout.addTab(tabLayout.newTab().setText("Rent"))
        tabLayout.addTab(tabLayout.newTab().setText("Rides"))

        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager,) { tab, position ->
            val tabNames = listOf("Rent","Rides")
            tab.text = tabNames[position]
        }.attach()

        return view
    }
}