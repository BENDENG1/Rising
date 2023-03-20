package com.softsquared.template.kotlin.src.main.Story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.FullStoriesListBinding
import com.softsquared.template.kotlin.src.main.Story.models.FullStoriesResult
import com.softsquared.template.kotlin.util.TimeConversion

class FullStoriesAdapter(var fullStoriesResult: MutableList<FullStoriesResult>) : RecyclerView.Adapter<FullStoriesAdapter.StoryViewHolder>() {

    private lateinit var binding : FullStoriesListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        binding = FullStoriesListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(fullStoriesResult[position])
    }

    override fun getItemCount(): Int {
        return fullStoriesResult.size
    }

    inner class StoryViewHolder(binding : FullStoriesListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(fullStoriesResult: FullStoriesResult){

            Glide.with(itemView).load(fullStoriesResult.story_url).into(binding.ivStoryContent)
            Glide.with(itemView).load(fullStoriesResult.profile_image_url).into(binding.civStoryProfile)
            binding.tvStoryCreated.text = TimeConversion.intervalBetweenDateText(fullStoriesResult.created_at)
            binding.tvStoryNickName.text = fullStoriesResult.nickname

        }
    }

    fun removeItem(position: Int) {
        fullStoriesResult.removeAt(position)
        notifyItemRemoved(position)
    }



}