package com.softsquared.template.kotlin.src.Buy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.PurchaseIconListBinding
import com.softsquared.template.kotlin.src.Buy.models.PurchaseIconData
import com.softsquared.template.kotlin.src.main.ItemClickListener

class PurchaseIconAdapter(var purchaseIconData : List<PurchaseIconData>, private val clickListener: ItemClickListener<PurchaseIconData>)
    : RecyclerView.Adapter<PurchaseIconAdapter.purchaseIconViewHolder>(){

    private lateinit var binding : PurchaseIconListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): purchaseIconViewHolder {
        binding = PurchaseIconListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return purchaseIconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: purchaseIconViewHolder, position: Int) {
        holder.bind(purchaseIconData[position])

        binding.root.setOnClickListener{
            clickListener.onItemClick(position,purchaseIconData[position])
        }
    }

    override fun getItemCount(): Int {
        return purchaseIconData.size
    }

    inner class purchaseIconViewHolder(binding : PurchaseIconListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(purchaseIconData: PurchaseIconData) {
            Glide.with(itemView).load(purchaseIconData.purchaseIcon).override(35,35).into(binding.ivPurchaseIcon)
            binding.tvPurchaseTitle.text = purchaseIconData.purchaseName
            binding.tvPurchaseSubtitle.text = purchaseIconData.purchaseSub
            binding.clPurchaseBg.setBackgroundResource(R.drawable.purchase_gray_border)
        }
    }
}