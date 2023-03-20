package com.softsquared.template.kotlin.src.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.HomeIconlistBinding
import com.softsquared.template.kotlin.src.main.home.models.HomeIcon

class HomeIconAdapter(var homeIconList : List<HomeIcon>) : RecyclerView.Adapter<HomeIconAdapter.HomeIconViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeIconViewHolder {
        val binding = HomeIconlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HomeIconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeIconViewHolder, position: Int) {
        holder.bind(homeIconList[position])
    }

    override fun getItemCount(): Int {
        return homeIconList.size
    }

    inner class HomeIconViewHolder(private val binding : HomeIconlistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(homeIcon: HomeIcon){
            with(binding){
                ibtnHomeIcon.setImageResource(homeIcon.iconPicture)
                tvHomeIcons.text = homeIcon.iconName
            }
        }
    }
}