package com.softsquared.template.kotlin.src.main.profile.List

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.ProfilePostListBinding
import com.softsquared.template.kotlin.src.main.profile.models.GetProfileResponse
import com.softsquared.template.kotlin.src.main.profile.models.ProfilePostResult

class ProfilePostAdapter(var profilePostResult : List<ProfilePostResult>,private val clickListener : PostClickListener<ProfilePostResult>)
    : RecyclerView.Adapter<ProfilePostAdapter.ProfilePostViewHolder>() {

    private lateinit var binding : ProfilePostListBinding

    interface PostClickListener<T>{
        fun onViewClick(view : ProfilePostListBinding, post : Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePostViewHolder {
        binding = ProfilePostListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ProfilePostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfilePostViewHolder, position: Int) {
        holder.bind(profilePostResult[position])

        holder.binding.ibtnProfilePost.setOnClickListener {
            clickListener.onViewClick(binding,position)
        }
    }

    override fun getItemCount(): Int {
        return profilePostResult.size
    }

    inner class ProfilePostViewHolder(val binding : ProfilePostListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(profilePostResult: ProfilePostResult){
            Glide.with(itemView).load(profilePostResult.photos[0].photoUrl).into(binding.ibtnProfilePost)

        }
    }
}