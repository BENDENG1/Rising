package com.softsquared.template.kotlin.src.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.HomeRecommendlistBinding
import com.softsquared.template.kotlin.src.main.home.models.FamousContent

class HomeRecommendAdapter(var homeRecommendList : List<FamousContent>,private val clickListener: ItemClickListener<FamousContent>)
    : RecyclerView.Adapter<HomeRecommendAdapter.HomeRestaurantViewHolder>() {

    private lateinit var binding : HomeRecommendlistBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRestaurantViewHolder {
        binding = HomeRecommendlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HomeRestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeRestaurantViewHolder, position: Int) {
        holder.bind(homeRecommendList[position])

        binding.root.setOnClickListener{
            clickListener.onItemClick(position,homeRecommendList[position])
        }
    }

    override fun getItemCount(): Int {
        return homeRecommendList.size
    }

    inner class HomeRestaurantViewHolder(binding : HomeRecommendlistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(famousContent: FamousContent){
            with(binding){
                tvRecommendId.text = famousContent.userNickName
                Glide.with(itemView).load(famousContent.contentImg).override(270,270).into(binding.ivRecommendBackground)
                tvRecommendId.bringToFront()

                binding.imageView3

            }
        }
    }


}