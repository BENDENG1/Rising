package com.gyroh.a5th_week.Main.Map

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gyroh.a5th_week.Main.Models.Data
import com.gyroh.a5th_week.Main.RestaurantAdapter
import com.gyroh.a5th_week.databinding.RestaurantmapListBinding

class RestaurantMapAdapter(var restaurantMapList : List<Data>) : RecyclerView.Adapter<RestaurantMapAdapter.RestaurantMapViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantMapViewHolder {
        val binding = RestaurantmapListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return RestaurantMapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantMapViewHolder, position: Int) {
        holder.bind(restaurantMapList[position])
    }


    override fun getItemCount(): Int {
        return restaurantMapList.size
    }

    inner class RestaurantMapViewHolder(val binding: RestaurantmapListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: Data){
            binding.tvMapSerial.text = "${data.serial}."
            binding.tvMapName.text = data.name
            binding.tvMapLocation.text = data.address
        }
    }


}