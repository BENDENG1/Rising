package com.softsquared.template.kotlin.src.main.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.SearchIdListBinding
import com.softsquared.template.kotlin.src.main.search.models.SearchIdResult

class SearchIdAdapter(var searchIdResult: List<SearchIdResult>,private val clickListener : SearchIdClickListener<SearchIdResult>)
    : RecyclerView.Adapter<SearchIdAdapter.SearchIdViewHolder>() {

    interface SearchIdClickListener<T>{
        fun onIDClick(view : SearchIdListBinding, pos : Int)
        fun onProfileClick(view : SearchIdListBinding, pos :Int)
    }
    private lateinit var binding : SearchIdListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchIdViewHolder {
        binding = SearchIdListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SearchIdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchIdViewHolder, position: Int) {
        holder.bind(searchIdResult[position])
    }

    override fun getItemCount(): Int {
        return searchIdResult.size
    }

    inner class SearchIdViewHolder(binding : SearchIdListBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind (searchIdResult: SearchIdResult){
            Glide.with(itemView).load(searchIdResult.profile_image_url).into(binding.civSearchIdProfile)
            binding.tvSearchIdNickname.text = searchIdResult.nickname

            if(searchIdResult.name!=null){
                binding.tvSearchIdName.text = searchIdResult.name
            }else{
                binding.tvSearchIdName.visibility = View.GONE
            }
            if(searchIdResult.connected_count > 0 && searchIdResult.connected_friend_nickname.isNotEmpty()){
                binding.tvSearchIdConnected.text = "${searchIdResult.connected_friend_nickname}님 외 ${searchIdResult.connected_count}명이 팔로우합니다"
            }else{
                binding.tvSearchIdConnected.visibility = View.GONE
            }

            if(searchIdResult.story_status ==1){
                binding.civSearchIdProfile.borderColor = R.color.transparent
            }

            binding.tvSearchIdNickname.setOnClickListener {
                clickListener.onIDClick(binding,adapterPosition)
            }
            binding.civSearchIdProfile.setOnClickListener {
                clickListener.onProfileClick(binding,adapterPosition)
            }

        }
    }
}