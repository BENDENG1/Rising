package com.gyroh.a5th_week.Main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gyroh.a5th_week.Main.Models.Data
import com.gyroh.a5th_week.databinding.RestaurantListBinding


class RestaurantAdapter(var restaurantList: List<Data>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = RestaurantListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantList[position])
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }


    inner class RestaurantViewHolder(val binding : RestaurantListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: Data){
            binding.tvListName.text = data.name
            binding.tvListAddress.text = data.address
            binding.tvSerial.text = "${data.serial}."
        }
    }
}