package com.softsquared.template.kotlin.src.main.Story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.HomeStoryListBinding
import com.softsquared.template.kotlin.src.main.Story.models.GetFullStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.GetSpecificStoriesResponse
import com.softsquared.template.kotlin.src.main.home.models.HomeStoryResult
import kotlin.coroutines.coroutineContext

class HomeStoryAdapter(var homeStoryResult: List<HomeStoryResult>,private val clickListener : HomeStoryClickListener)
    : RecyclerView.Adapter<HomeStoryAdapter.HomeStoryViewHolder>() {

    private lateinit var binding : HomeStoryListBinding

    interface HomeStoryClickListener{
        fun onClickListener(pos : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeStoryViewHolder {
        binding = HomeStoryListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HomeStoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeStoryViewHolder, position: Int) {
        holder.bind(homeStoryResult[position])
    }

    override fun getItemCount(): Int {
        return homeStoryResult.size
    }

    inner class HomeStoryViewHolder(binding : HomeStoryListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(homeStoryResult: HomeStoryResult){
            binding.ivHomeStoryProfile.setOnClickListener {
                clickListener.onClickListener(adapterPosition)
            }
            Glide.with(itemView).load(homeStoryResult.profile_image_url).into(binding.ivHomeStoryProfile)
            binding.tvHomeStoryName.text = homeStoryResult.nickname
            with(binding.ivHomeStoryProfile){
                if(homeStoryResult.self_status == 1){
                    borderColor = ContextCompat.getColor(itemView.context,R.color.transparent)
                }else {
                    if(homeStoryResult.view_status == 0){
                        borderColor = ContextCompat.getColor(itemView.context,R.color.btn_story_red)
                    }else if(homeStoryResult.view_status ==1){
                        borderColor = ContextCompat.getColor(itemView.context,R.color.btn_story_gray)
                    }
                }
            }
        }
    }
}