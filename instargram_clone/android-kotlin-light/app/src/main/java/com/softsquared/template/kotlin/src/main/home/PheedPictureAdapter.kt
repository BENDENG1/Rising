package com.softsquared.template.kotlin.src.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.HomePheedPictureListBinding
import com.softsquared.template.kotlin.src.main.home.models.HomePheedPhotos

class PheedPictureAdapter(var homePheedPhotos : List<HomePheedPhotos>) : RecyclerView.Adapter<PheedPictureAdapter.PheedPictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PheedPictureViewHolder {
        val binding = HomePheedPictureListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return PheedPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PheedPictureViewHolder, position: Int) {
        holder.bind(homePheedPhotos[position])
    }

    override fun getItemCount(): Int {
        return homePheedPhotos.size
    }

    inner class PheedPictureViewHolder (val binding : HomePheedPictureListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(homePheedPhotos : HomePheedPhotos){
            Glide.with(itemView).load(homePheedPhotos.photoUrl).into(binding.ivHomePheedPicture)
        }
    }
}