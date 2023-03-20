package com.softsquared.template.kotlin.src.main.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.HomeEventlistBinding
import com.softsquared.template.kotlin.src.main.home.models.HomeEventData

class HomeEventPictureAdapter(var homeEventImageSlider : List<HomeEventData>) : RecyclerView.Adapter<HomeEventPictureAdapter.imageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewHolder {
        val binding = HomeEventlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return imageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: imageViewHolder, position: Int) {
        holder.bind(homeEventImageSlider[position])
    }

    override fun getItemCount(): Int {
        return homeEventImageSlider.size
    }

    inner class imageViewHolder(val binding : HomeEventlistBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(homeEventData: HomeEventData){
            Glide.with(itemView).load(homeEventData.image).into(binding.ivHomeEventImage)
           binding.tvHomeEventNum.text = "${adapterPosition+1} / ${homeEventImageSlider.size}"
        }
    }
}