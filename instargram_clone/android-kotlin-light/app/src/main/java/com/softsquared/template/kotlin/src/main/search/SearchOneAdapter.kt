package com.softsquared.template.kotlin.src.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.SearchOnePhotoListBinding
import com.softsquared.template.kotlin.src.main.search.models.SearchOnePhoto

class SearchOneAdapter(var searchOnePhoto: List<SearchOnePhoto>) : RecyclerView.Adapter<SearchOneAdapter.SearchOnePhotoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchOnePhotoViewHolder {
        val binding = SearchOnePhotoListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SearchOnePhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchOnePhotoViewHolder, position: Int) {
        holder.bind(searchOnePhoto[position])
    }

    override fun getItemCount(): Int {
        return searchOnePhoto.size
    }

    inner class SearchOnePhotoViewHolder (val binding : SearchOnePhotoListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(searchOnePhoto: SearchOnePhoto){
            Glide.with(itemView).load(searchOnePhoto.photoUrl).into(binding.ivSearchOnePicture)
        }
    }


}