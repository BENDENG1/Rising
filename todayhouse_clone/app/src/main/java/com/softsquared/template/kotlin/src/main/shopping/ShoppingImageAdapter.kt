package com.softsquared.template.kotlin.src.main.shopping

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.ShoppingImagelistBinding
import com.softsquared.template.kotlin.src.main.shopping.models.ShoppingImageData

class ShoppingImageAdapter(var shoppingImageSlider : List<ShoppingImageData>)
    : RecyclerView.Adapter<ShoppingImageAdapter.imageViewHolder>() {

    private lateinit var binding : ShoppingImagelistBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewHolder {
        binding = ShoppingImagelistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return imageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: imageViewHolder, position: Int) {
        holder.bind(shoppingImageSlider[position])
    }

    override fun getItemCount(): Int {
        return shoppingImageSlider.size
    }

    inner class imageViewHolder(binding: ShoppingImagelistBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(shoppingImageData: ShoppingImageData){
            Glide.with(itemView).load(shoppingImageData.image).into(binding.ivShoppingImage)
            binding.tvShoppingImageNum.text = "${adapterPosition+1}/${shoppingImageSlider.size}"
        }
    }
}