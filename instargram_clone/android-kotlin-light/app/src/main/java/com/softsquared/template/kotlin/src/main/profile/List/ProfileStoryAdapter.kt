package com.softsquared.template.kotlin.src.main.profile.List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.ProfileStoryListBinding
import com.softsquared.template.kotlin.src.main.profile.models.ProfileStoryList

class ProfileStoryAdapter(var profileStoryList: List<ProfileStoryList>) : RecyclerView.Adapter<ProfileStoryAdapter.ProfileStoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileStoryViewHolder {
        val binding = ProfileStoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ProfileStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileStoryViewHolder, position: Int) {
        holder.bind(profileStoryList[position])
    }

    override fun getItemCount(): Int {
        return profileStoryList.size
    }

    inner class ProfileStoryViewHolder(val binding : ProfileStoryListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(profileStoryList: ProfileStoryList){
            //나중에 서버 구축되면 수정
            Glide.with(itemView).load(profileStoryList.image).into(binding.ivProfileStory)
//            Glide.with(itemView).load(profileStoryList.image).into(binding.)
            binding.tvProfileStoryName.text = "스토리 테스트"
        }

    }
}