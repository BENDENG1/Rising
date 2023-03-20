package com.gyroh.a5th_week.Main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gyroh.a5th_week.Main.Models.Data
import com.gyroh.a5th_week.databinding.RestaurantListBinding
import com.gyroh.a5th_week.databinding.RestaurantReviewBinding

class MainReviewAdapter(var restaurantReviewList : List<Data>) : RecyclerView.Adapter<MainReviewAdapter.MainReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainReviewViewHolder {
        val binding = RestaurantReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return MainReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainReviewViewHolder, position: Int) {
        holder.bind(restaurantReviewList[position])
    }

    override fun getItemCount(): Int {
        return restaurantReviewList.size
    }

    inner class MainReviewViewHolder(val binding : RestaurantReviewBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: Data){
            binding.tvReviewNamePlace.text = "${data.name} - ${data.address}"
            binding.tvReviewMainFood.text = data.mainFood
        }
    }
}