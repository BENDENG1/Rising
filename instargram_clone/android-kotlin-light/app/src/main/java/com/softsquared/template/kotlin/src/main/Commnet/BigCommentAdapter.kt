package com.softsquared.template.kotlin.src.main.Commnet

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.BigCommentListBinding
import com.softsquared.template.kotlin.databinding.CommentUserListBinding
import com.softsquared.template.kotlin.src.main.Commnet.models.BigCommentResult
import com.softsquared.template.kotlin.util.TimeConversion
import java.util.regex.Pattern


class BigCommentAdapter(var bigCommentResult: List<BigCommentResult>
    ,private val clickListener : BigCommentClickListener<BigCommentResult>)
    : RecyclerView.Adapter<BigCommentAdapter.BigCommentViewHolder>() {


    interface BigCommentClickListener<T>{
        fun onViewClick(view : BigCommentListBinding,pos : Int)
    }

    private lateinit var binding : BigCommentListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigCommentViewHolder {
        binding = BigCommentListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return BigCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BigCommentViewHolder, position: Int) {
        holder.bind(bigCommentResult[position])
    }

    override fun getItemCount(): Int {
        return bigCommentResult.size
    }

    inner class BigCommentViewHolder(binding : BigCommentListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(bigCommentResult: BigCommentResult){
            Glide.with(itemView).load(bigCommentResult.profilePicture).circleCrop().into(binding.ibtnBigCommentUserProfile)
            binding.tvBigCommentUserId.text = bigCommentResult.profileName
            binding.tvBigCommentUserCreated.text = TimeConversion.intervalBetweenDateText(bigCommentResult.createdAt)
            binding.tvBigCommentUserComment.text = bigCommentResult.comment
            commentTagTextColor(binding.tvBigCommentUserComment)
            binding.tvBigCommentUserLikeCount.text = bigCommentResult.likeCount.toString()


            binding.ibtnBigCommentUserLike.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }

        }
    }


    private fun commentTagTextColor(textView: TextView){
        val pattern = Pattern.compile("@\\w+")

        val spannable = SpannableString(textView.text)
        val matcher = pattern.matcher(spannable)

        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()

            val colorSpan = ForegroundColorSpan(Color.BLUE)
            spannable.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        textView.text = spannable
    }
}