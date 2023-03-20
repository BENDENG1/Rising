package com.softsquared.template.kotlin.src.main.shopping.Detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.DetailReviewListBinding
import com.softsquared.template.kotlin.src.main.ItemClickListener
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.DetailReviewResponse
import com.softsquared.template.kotlin.src.main.shopping.Detail.models.ReviewResult
import java.text.SimpleDateFormat

class DetailReviewAdapter(var detailReviewList :List<ReviewResult>, private val clickListener: ItemClickListener<ReviewResult>)
    : RecyclerView.Adapter<DetailReviewAdapter.DetailReviewViewHolder>(){

    private lateinit var binding : DetailReviewListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        binding = DetailReviewListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return DetailReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        holder.bind(detailReviewList[position])

        binding.root.setOnClickListener{
            clickListener.onItemClick(position,detailReviewList[position])
        }
    }

    override fun getItemCount(): Int {
        return if(detailReviewList.size > 3) {
            3
        } else {
            detailReviewList.size
        }
    }

    inner class DetailReviewViewHolder(binding : DetailReviewListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(reviewResult: ReviewResult){
            Glide.with(itemView).load(R.drawable.ic_bnv_mypage).circleCrop().into(binding.ivDetailReviewListProfile)
            binding.tvDetailReviewListNickname.text = reviewResult.userNickName
            binding.rbDetailReviewListStar.rating = reviewResult.pointAvg.toFloat()
            binding.tvDetailReviewListCreatedAt.text = convertTimestampToDate(reviewResult.createdAt)
            if(reviewResult.reviewImg == ""){
                binding.ivDetailReviewListProductPicture.visibility = View.GONE
            }else{
                Glide.with(itemView).load(reviewResult.reviewImg).into(binding.ivDetailReviewListProductPicture)
            }
            binding.tvDetailReviewListReview.text = reviewResult.reviewContent
            binding.tvDetailReviewListProductName.text = reviewResult.productName
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun convertTimestampToDate(timestamp: Long) :String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf.format(timestamp)
        return date
    }
}