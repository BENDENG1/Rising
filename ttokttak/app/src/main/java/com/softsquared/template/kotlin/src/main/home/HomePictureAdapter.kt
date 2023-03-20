package com.softsquared.template.kotlin.src.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.HomePicutreListBinding
import com.softsquared.template.kotlin.src.main.home.models.HomePicture

class HomePictureAdapter(var homePictureList : List<HomePicture>) : RecyclerView.Adapter<HomePictureAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = HomePicutreListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(homePictureList[position])
    }

    override fun getItemCount(): Int {
        return homePictureList.size
    }

    inner class ImageViewHolder(val binding : HomePicutreListBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(homePicture : HomePicture) {
            Glide.with(itemView).load(homePicture.image).into(binding.ivHomeEventPicture)
            binding.tvHomeEventText.text = "${adapterPosition+1}/${homePictureList.size}"
            binding.ivHomeEventPicture.setBackgroundColor(homePicture.color)
        }
    }
}