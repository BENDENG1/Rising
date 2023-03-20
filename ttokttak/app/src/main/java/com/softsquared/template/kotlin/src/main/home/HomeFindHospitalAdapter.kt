package com.softsquared.template.kotlin.src.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.HomeFindHospitalListBinding
import com.softsquared.template.kotlin.src.main.home.models.HomeFindHospital

class HomeFindHospitalAdapter(var homeFindHospitalList: List<HomeFindHospital>) : RecyclerView.Adapter<HomeFindHospitalAdapter.HospitalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val binding = HomeFindHospitalListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HospitalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        holder.bind(homeFindHospitalList[position])
    }

    override fun getItemCount(): Int {
        return homeFindHospitalList.size
    }

    inner class HospitalViewHolder(val binding : HomeFindHospitalListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(homeFindHospital: HomeFindHospital){
            Glide.with(itemView).load(homeFindHospital.picture).into(binding.ivHomeFindHospitalPic)
            binding.tvHomeFindHospitalName.text = homeFindHospital.name
            binding.viewHomeFindHospitalBg.setBackgroundColor(homeFindHospital.color)

        }
    }
}