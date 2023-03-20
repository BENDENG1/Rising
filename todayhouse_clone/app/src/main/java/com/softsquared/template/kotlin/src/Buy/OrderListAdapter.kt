package com.softsquared.template.kotlin.src.Buy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.databinding.BuyOrderListBinding
import com.softsquared.template.kotlin.src.Buy.models.Cart
import com.softsquared.template.kotlin.src.main.ItemClickListener

class OrderListAdapter(var orderList : List<Cart>)
    :RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder>(){

    private lateinit var binding : BuyOrderListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {
        binding = BuyOrderListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return OrderListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    inner class OrderListViewHolder(binding : BuyOrderListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(cart: Cart){
            binding.tvOrderListCompanyName.text = "회사이름 데이터 받아와야함"
            Glide.with(itemView).load(cart.thumbnail).override(60,60).into(binding.ivOrderListPicture)
            binding.tvOrderListProductName.text = cart.productName
            binding.tvOrderListPrice.text = cart.price.toString()
            binding.tvOrderListCompanyName.text = cart.productCom
            binding.tvOrderListOrderCnt.text = cart.orderCnt.toString()
        }
    }
}