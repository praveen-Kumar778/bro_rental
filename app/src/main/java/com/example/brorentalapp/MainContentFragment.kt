package com.example.brorentalapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainContentFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
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
        val view =  inflater.inflate(R.layout.fragment_main_content, container, false)
        recyclerView = view.findViewById(R.id.topRidesView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val rupeeSymbol = "\u20B9"
        val ride = listOf(
            TopRides(R.drawable.cycle_1,"A","Motorbike","$rupeeSymbol 100/day"),
            TopRides(R.drawable.cycle_2,"B","Scooty","$rupeeSymbol 200/day"),
            TopRides(R.drawable.cycle_3,"C","Scooty","$rupeeSymbol 300/day"),
            TopRides(R.drawable.cycle4,"D","Motorbike","$rupeeSymbol 400/day"),
            TopRides(R.drawable.cycle5,"E","Motorbike","$rupeeSymbol 500/day")
        )
        val rideAdapter = TopRidesRecycler(ride)
        recyclerView.adapter = rideAdapter
        return view
    }

}