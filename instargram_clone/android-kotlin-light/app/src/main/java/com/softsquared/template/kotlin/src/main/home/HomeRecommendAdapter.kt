package com.softsquared.template.kotlin.src.main.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.HomeRecommendListBinding
import com.softsquared.template.kotlin.src.main.search.models.SearchIdResult

class HomeRecommendAdapter(var searchIdResult: List<SearchIdResult>, private val clickListener : HomeRecommendClickListener<SearchIdResult>)
    : RecyclerView.Adapter<HomeRecommendAdapter.HomeRecommendViewHolder>()  {

    interface HomeRecommendClickListener<T>{
        fun onFollowClick(view : HomeRecommendListBinding, pos : Int)
        fun onProfileClick(view : HomeRecommendListBinding, pos :Int)
    }

    private lateinit var binding : HomeRecommendListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecommendViewHolder {
        binding = HomeRecommendListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HomeRecommendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeRecommendViewHolder, position: Int) {
        holder.bind(searchIdResult[position])

        holder.binding.btnHomeRecommendFollow.setOnClickListener {
            clickListener.onFollowClick(holder.binding,position)
        }
        holder.binding.tvHomeRecommendName.setOnClickListener {
            clickListener.onProfileClick(holder.binding,position)
        }
    }

    override fun getItemCount(): Int {
        return searchIdResult.size
    }

    inner class HomeRecommendViewHolder(val binding : HomeRecommendListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind (searchIdResult: SearchIdResult){
            Glide.with(itemView).load(searchIdResult.profile_image_url).into(binding.civHomeRecommend)
            binding.tvHomeRecommendName.text = searchIdResult.name
            binding.tvHomeRecommendNickname.text = searchIdResult.nickname

            binding.civHomeRecommend.setOnClickListener {
                clickListener.onProfileClick(binding,adapterPosition)
            }
            binding.btnHomeRecommendFollow.setOnClickListener {
                clickListener.onFollowClick(binding,adapterPosition)
            }
        }
    }
}