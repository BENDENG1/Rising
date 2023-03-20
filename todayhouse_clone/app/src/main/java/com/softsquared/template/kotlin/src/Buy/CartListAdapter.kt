package com.softsquared.template.kotlin.src.Buy

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.BuyCartListBinding
import com.softsquared.template.kotlin.src.Buy.models.*
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.shopping.models.ProductsResponse
import java.text.DecimalFormat

open class CartListAdapter(var cartList: MutableList<Cart>, private val clickListener: BuyClickListener<Cart>) //param : DetailClickListener
    :RecyclerView.Adapter<CartListAdapter.cartListViewHolder>() , BuyFragmentInterface{

    private val userNum = ApplicationClass.sSharedPreferences.getInt("userNum",0)
    private lateinit var binding : BuyCartListBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cartListViewHolder {
        binding = BuyCartListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return cartListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: cartListViewHolder, position: Int) {
        holder.bind(cartList[position])

        binding.ivCartListThumbnail.setOnClickListener{
            clickListener.onItemClick(position,cartList[position])
        }
        binding.tvCartListName.setOnClickListener {
            clickListener.onItemClick(position,cartList[position])
        }
        binding.ibtnCartListRemove.setOnClickListener {
            Log.d("----","${userNum}, ${cartList[position].orderCnt}, ${cartList[position].ordersNum}")
            BuyService(this@CartListAdapter).tryDeleteCarts(ordersNum =cartList[position].ordersNum)
            removeItem(position)
        }
        binding.btnCartListCount.setOnClickListener {
            clickListener.onViewClick(binding, position)
            Log.d("----어댑터 테스트","$position , $userNum , ${cartList[position].ordersNum} ${cartList[position].orderCnt}")
//            BuyService(this@CartListAdapter).tryPatchCarts(userNum,CartPatchRequest(cartList[position].ordersNum, 7))
        }
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    inner class cartListViewHolder(binding : BuyCartListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(cart: Cart){
            val dec = DecimalFormat("###,###,###")
            Glide.with(itemView).load(cart.thumbnail).into(binding.ivCartListThumbnail)
            binding.cbCartListSelect.isChecked = true
            binding.tvCartListName.text = cart.productName
            binding.tvCartListName.text = cart.productName
            binding.btnCartListCount.text = cart.orderCnt.toString()
            binding.tvCartListPrice.text = dec.format(cart.price) + "원"
            binding.tvCartListPrice2.text = dec.format(cart.price) + "원"

        }
    }

    fun removeItem(position: Int){
        cartList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,cartList.size)
    }


    override fun onGetCartsSuccess(response: CartResponse) {}

    override fun onGetCartsFailure(message: String) {}

    override fun onOrderSuccess(response: OrderResponse) {}

    override fun onOrderFailure(message: String) {}

    override fun onGetProductSuccess(response: ProductsResponse) {}

    override fun onGetProductFailure(message: String) {}

    override fun onPatchCartSuccess(response: CartPatchResponse) {
//        Log.d("----어댑터 테스트","$response.result, ${response.message.toString()}, ${response.code}")
    }

    override fun onPatchCartFailure(message: String) {
//        Log.d("----", message)
    }

    override fun onDeleteCartSuccess(response: CartDeleteResponse) {
        Log.d("----",response.result)
        Log.d("----",response.message.toString())
        Log.d("----",response.code.toString())
    }

    override fun onDeleteCartFailure(message: String) {
        Log.d("----",message)
    }
}