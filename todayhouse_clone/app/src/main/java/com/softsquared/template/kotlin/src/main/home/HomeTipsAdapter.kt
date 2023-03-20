package com.softsquared.template.kotlin.src.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.HomeTipslistBinding
import com.softsquared.template.kotlin.src.main.home.models.Content


class HomeTipsAdapter(var homeTipsList : List<Content>, private val clickListener: HomeTipClickListener<Content>)
    :RecyclerView.Adapter<HomeTipsAdapter.HomeTipsViewHolder>(){

    private lateinit var binding : HomeTipslistBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTipsViewHolder {
        binding = HomeTipslistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HomeTipsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTipsViewHolder, position: Int) {
        holder.bind(homeTipsList[position])

        binding.root.setOnClickListener{
            clickListener.onItemClick(position,homeTipsList[position])
        }


    }

    override fun getItemCount(): Int {
        return homeTipsList.size
    }

    inner class HomeTipsViewHolder (binding : HomeTipslistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(content: Content){
            Glide.with(itemView).load(content.contentImg).into(binding.ivHomeTipList)
            binding.tvHomeTipListTitle.text = content.contentTitle

            binding.ibtnHomeTipListScrap.setOnClickListener{
                clickListener.onViewClick(binding,adapterPosition)
//                if(binding.ibtnHomeTipListScrap.isSelected)
//                {
//                    Glide.with(itemView).load(R.drawable.ic_scrap_off).into(binding.ibtnHomeTipListScrap)
//                    binding.ibtnHomeTipListScrap.isSelected = true
//                }else{
//                    Glide.with(itemView).load(R.drawable.ic_scrap_on).into(binding.ibtnHomeTipListScrap)
//                    binding.ibtnHomeTipListScrap.isSelected = false
//                }

            }
        }
    }
}