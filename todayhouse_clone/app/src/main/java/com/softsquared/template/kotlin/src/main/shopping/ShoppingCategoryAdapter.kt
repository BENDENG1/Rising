package com.softsquared.template.kotlin.src.main.shopping

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.ShoppingCategoryListBinding
import com.softsquared.template.kotlin.src.main.shopping.models.ShoppingCategoryData

class ShoppingCategoryAdapter(var shoppingCategoryList : List<ShoppingCategoryData>)
    : RecyclerView.Adapter<ShoppingCategoryAdapter.categoryImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryImageViewHolder {
        val binding = ShoppingCategoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return categoryImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: categoryImageViewHolder, position: Int) {
        holder.bind(shoppingCategoryList[position])


    }

    override fun getItemCount(): Int {
        return shoppingCategoryList.size
    }

    inner class categoryImageViewHolder(val binding: ShoppingCategoryListBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor")
        fun bind(shoppingCategoryData: ShoppingCategoryData){

            binding.ibtnShoppingCategory.setImageResource(shoppingCategoryData.categoryImage)
            //Glide.with(itemView).load(shoppingCategoryData.categoryImage).into(binding.ibtnShoppingCategory)
            binding.tvShoppingCategory.text = shoppingCategoryData.categoryTitle

            if(adapterPosition == 0){
                binding.tvShoppingCategory.setTextColor(R.color.brighter_skyBlue)
                binding.clIcon.setBackgroundResource(R.color.shopping_category_skyblue)
            }
        }
    }
}