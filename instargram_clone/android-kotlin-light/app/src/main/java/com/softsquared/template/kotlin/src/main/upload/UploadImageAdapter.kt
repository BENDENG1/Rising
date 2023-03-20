package com.softsquared.template.kotlin.src.main.upload

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.UploadImageListBinding
import com.softsquared.template.kotlin.src.main.upload.models.UploadPhoto

class UploadImageAdapter(var uploadPhoto: List<UploadPhoto>,private val clickListener : UploadImageClickListener<UploadPhoto>)
    : RecyclerView.Adapter<UploadImageAdapter.UploadImageViewHolder>() {


    interface UploadImageClickListener<T>{
        fun onClickListener(view : UploadImageListBinding,pos : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImageViewHolder {
        val binding = UploadImageListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return UploadImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UploadImageViewHolder, position: Int) {
        holder.bind(uploadPhoto[position])

        holder.binding.ivUploadStorageImage.setOnClickListener {
            clickListener.onClickListener(holder.binding,position)
        }
    }

    override fun getItemCount(): Int {
        if(uploadPhoto.size > 32)
            return 32
        return uploadPhoto.size
    }

    inner class UploadImageViewHolder(val binding : UploadImageListBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind (uploadPhoto: UploadPhoto){
            Glide.with(itemView).load(uploadPhoto.photoUrl).into(binding.ivUploadStorageImage)
        }
    }
}