package com.softsquared.template.kotlin.src.main.shopping.Detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.DetailImagelistBinding

class DetailImageAdapter(var detailImageSlider: List<String>)
    : RecyclerView.Adapter<DetailImageAdapter.imageViewHolder>() {

    private lateinit var binding : DetailImagelistBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewHolder {
        binding = DetailImagelistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return imageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: imageViewHolder, position: Int) {
        holder.bind(detailImageSlider[position])
    }

    override fun getItemCount(): Int {
        if(detailImageSlider.size > 2)
            return 3
        return detailImageSlider.size
    }

    inner class imageViewHolder(binding : DetailImagelistBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(string: String) {
            Glide.with(itemView).load(string).into(binding.ivDetailProduct)
            binding.tvDetailImageNum.text = "${adapterPosition+1}/${detailImageSlider.size}"
        }
    }
}