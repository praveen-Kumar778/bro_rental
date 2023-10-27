package com.example.brorentalapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TopRidesRecycler(private val rides: List<TopRides>) :
    RecyclerView.Adapter<TopRidesRecycler.RidesAdapterHolder>() {


    inner class RidesAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.recy_img)
        val title: TextView = itemView.findViewById(R.id.recy_title)
        val nameTextView: TextView = itemView.findViewById(R.id.recy_name)
        val priceTextView: TextView = itemView.findViewById(R.id.recy_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RidesAdapterHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout, parent, false)
        return RidesAdapterHolder(itemView)
    }

    override fun onBindViewHolder(holder: RidesAdapterHolder, position: Int) {
        val currentItem = rides[position]
        Picasso.get()
            .load(currentItem.img)
            .into(holder.image)
        holder.title.text = currentItem.title
        holder.nameTextView.text = currentItem.name
        holder.priceTextView.text = currentItem.price
    }

    override fun getItemCount() = rides.size
}