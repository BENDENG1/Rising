package com.softsquared.template.kotlin.src.main.shopping

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.ShoppingProductListBinding
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.shopping.models.ProductResult
import java.text.DecimalFormat

class ShoppingProductsAdapter(
    var shoppingProductList: List<ProductResult>,
    private val clickListener : ItemClickListener<ProductResult>
) : RecyclerView.Adapter<ShoppingProductsAdapter.productViewHolder>() {

    private lateinit var binding : ShoppingProductListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
        binding = ShoppingProductListBinding.inflate(LayoutInflater.from(parent.context),parent,false)


        return productViewHolder(binding)
    }

    override fun onBindViewHolder(holder: productViewHolder, position: Int) {
        holder.bind(shoppingProductList[position])

        binding.root.setOnClickListener {
            clickListener.onItemClick(position,shoppingProductList[position])
        }
        binding.ivProductsScrap.setOnClickListener {
            Glide.with(holder.itemView).load(R.drawable.ic_scrap_on).into(binding.ivProductsScrap)
        }
    }

    override fun getItemCount(): Int {
        return shoppingProductList.size
    }

    inner class productViewHolder(binding: ShoppingProductListBinding):RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(productResult: ProductResult){
            val dec = DecimalFormat("#,###,###")
            val decimal = DecimalFormat("#.#")
            Glide.with(itemView).load(productResult.thumbnail).into(binding.ivProductThumbnail)
            binding.tvProductsCompany.text = productResult.productCom
            binding.tvProductName.text = productResult.productName
            binding.tvProductPrice.text = dec.format(productResult.productPrice)
            binding.tvProductStar.text = decimal.format(productResult.reviewAvg)
            binding.tvProductReview.text = "리뷰 " + productResult.reviewCnt.toString()


        }
    }
}