package com.softsquared.template.kotlin.src.main.shopping.Detail


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.DetailInformationlistBinding

class DetailInformationAdapter(var detailInformaitonList : List<String>)
    :RecyclerView.Adapter<DetailInformationAdapter.informationViewHolder>(){

    private lateinit var binding : DetailInformationlistBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): informationViewHolder {
        binding = DetailInformationlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return informationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: informationViewHolder, position: Int) {
        holder.bind(detailInformaitonList[position])
    }

    override fun getItemCount(): Int {
        return detailInformaitonList.size
    }

    inner class informationViewHolder(binding: DetailInformationlistBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(string: String){
            Glide.with(itemView).load(string).error(R.drawable.aaaaa).into(binding.ivDetailInformaiton)
        }
    }
}