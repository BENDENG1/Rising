package com.softsquared.template.kotlin.src.main.profile.List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.RecommendFriendListBinding
import com.softsquared.template.kotlin.src.main.profile.models.RecommendFriendList


class RecommendFriendAdapter(var profileRecommendFriendList: List<RecommendFriendList>) : RecyclerView.Adapter<RecommendFriendAdapter.RecommendFriendViewHolder>() {


    //클릭리스너 +api해야함

    private lateinit var binding : RecommendFriendListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): RecommendFriendViewHolder {
        binding = RecommendFriendListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return RecommendFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendFriendViewHolder, position: Int) {
        holder.bind(profileRecommendFriendList[position])
    }

    override fun getItemCount(): Int {
        return profileRecommendFriendList.size
    }

    inner class RecommendFriendViewHolder(binding : RecommendFriendListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(recommendFriendList: RecommendFriendList){
            Glide.with(itemView).load(recommendFriendList.image).circleCrop().into(binding.civRecommentFriendImage) //circlecrop을 civ에도 반영 여부
            binding.tvRecommendFriendName.text = recommendFriendList.name
        }
    }
}