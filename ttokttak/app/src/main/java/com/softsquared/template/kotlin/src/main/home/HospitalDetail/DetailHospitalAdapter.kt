package com.softsquared.template.kotlin.src.main.home.HospitalDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.DetailHospitalListBinding
import com.softsquared.template.kotlin.src.main.home.models.DetailHospitalPicture

class DetailHospitalAdapter(var detailHospitalList : List<DetailHospitalPicture>)
    :RecyclerView.Adapter<DetailHospitalAdapter.ImageViewHolder>(){

    private lateinit var binding : DetailHospitalListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = DetailHospitalListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ImageViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(detailHospitalList[position])
    }

    override fun getItemCount(): Int {
        return detailHospitalList.size
    }

    inner class ImageViewHolder(binding : DetailHospitalListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(detailHospitalPicture: DetailHospitalPicture){
            Glide.with(itemView).load(detailHospitalPicture.picture).into(binding.ivDetailHospitalPic)
            binding.tvDetailHospitalPage.text = "${adapterPosition+1}/${detailHospitalList.size}"
        }
    }
}