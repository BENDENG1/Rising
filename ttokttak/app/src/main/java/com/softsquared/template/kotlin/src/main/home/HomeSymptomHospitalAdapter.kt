package com.softsquared.template.kotlin.src.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.HomeSymptomListBinding
import com.softsquared.template.kotlin.src.main.home.models.HomeSymptomHospital

class HomeSymptomHospitalAdapter(var homeSymptomHospital: List<HomeSymptomHospital>) : RecyclerView.Adapter<HomeSymptomHospitalAdapter.SymptomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        val binding = HomeSymptomListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SymptomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        holder.bind(homeSymptomHospital[position])
    }

    override fun getItemCount(): Int {
        return homeSymptomHospital.size
    }

    inner class SymptomViewHolder(val binding : HomeSymptomListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(homeSymptomHospital: HomeSymptomHospital){
            binding.tvHomeSymptomHospital.text = homeSymptomHospital.symptomName
        }
    }
}