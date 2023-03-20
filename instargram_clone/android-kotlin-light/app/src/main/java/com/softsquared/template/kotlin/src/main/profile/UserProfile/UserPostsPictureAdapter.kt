package com.softsquared.template.kotlin.src.main.profile.UserProfile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.UserPostsPictureListBinding
import com.softsquared.template.kotlin.src.main.profile.models.ProfilePostPhoto

class UserPostsPictureAdapter(var userPostsPhoto : List<ProfilePostPhoto>) : RecyclerView.Adapter<UserPostsPictureAdapter.UserPostsPictureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostsPictureViewHolder {
        val binding = UserPostsPictureListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return UserPostsPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserPostsPictureViewHolder, position: Int) {
        holder.bind(userPostsPhoto[position])
    }

    override fun getItemCount(): Int {
        return userPostsPhoto.size
    }

    inner class UserPostsPictureViewHolder (val binding : UserPostsPictureListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(profilePostPhoto: ProfilePostPhoto){
            Glide.with(itemView).load(profilePostPhoto.photoUrl).into(binding.ivUserPostsPicture)
        }
    }

}