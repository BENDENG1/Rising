package com.softsquared.template.kotlin.src.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.SearchPostListBinding
import com.softsquared.template.kotlin.src.main.search.models.SearchPostResult

class SearchPostAdapter(var searchPostResult: List<SearchPostResult>,private val clickListener : SearchClickListener<SearchPostResult>)
    : RecyclerView.Adapter<SearchPostAdapter.SearchPostViewHolder>() {

    private lateinit var binding : SearchPostListBinding

    interface SearchClickListener<T>{
        fun onViewClick(view : SearchPostListBinding, pos :Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchPostViewHolder {
        binding = SearchPostListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SearchPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchPostViewHolder, position: Int) {
        holder.bind(searchPostResult[position])
    }

    override fun getItemCount(): Int {
        return searchPostResult.size
    }

    inner class SearchPostViewHolder(binding : SearchPostListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(searchPostResult: SearchPostResult){
            Glide.with(itemView).load(searchPostResult.firstPhotoUrl).into(binding.ibtnSearchPost)

            binding.ibtnSearchPost.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }

        }

    }
}