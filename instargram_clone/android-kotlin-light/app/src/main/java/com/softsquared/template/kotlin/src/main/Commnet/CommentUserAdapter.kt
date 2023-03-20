package com.softsquared.template.kotlin.src.main.Commnet

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.BigCommentListBinding
import com.softsquared.template.kotlin.databinding.CommentUserListBinding
import com.softsquared.template.kotlin.src.main.Commnet.models.*
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.home.HomeService
import com.softsquared.template.kotlin.src.main.home.models.GetHomePheedResponse
import com.softsquared.template.kotlin.src.main.home.models.GetHomeStoryResponse
import com.softsquared.template.kotlin.util.TimeConversion
import org.w3c.dom.Comment
import java.util.*
import java.util.regex.Pattern

class CommentUserAdapter(var commentResult: List<CommentResult> ,private val clickListener : CommentUserClickListener<CommentResult>)
    : RecyclerView.Adapter<CommentUserAdapter.CommentUserViewHolder>(),CommentFragmentInterface {

    private lateinit var binding : CommentUserListBinding

    interface CommentUserClickListener<T>{
        fun onViewClick(view : CommentUserListBinding, pos :Int)
        fun onReplyClick(view : CommentUserListBinding,pos :Int)
        fun onLikeClick(view : CommentUserListBinding,pos : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentUserViewHolder {
        binding = CommentUserListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return CommentUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentUserViewHolder, position: Int) {
        holder.bind(commentResult[position])

        holder.binding.tvCommentUserReply.setOnClickListener {
            clickListener.onViewClick(binding,position)
        }
        holder.binding.tvCommentUserReply.setOnClickListener {
            clickListener.onReplyClick(binding,position)
            CommentService(this@CommentUserAdapter).tryGetBigComment(commentResult[position].commentId)
        }
    }

    override fun getItemCount(): Int {
        return commentResult.size
    }

    inner class CommentUserViewHolder(val binding : CommentUserListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(commentResult: CommentResult){


            Glide.with(itemView).load(commentResult.profilePicture).circleCrop().into(binding.ibtnCommentUserProfile)
            binding.tvCommentUserId.text = commentResult.profileName
            binding.tvCommentUserCreated.text = TimeConversion.intervalBetweenDateText(commentResult.createdAt)
            binding.tvCommentUserComment.text = commentResult.comment
            commentTagTextColor(binding.tvCommentUserComment)

            if(commentResult.bigCommentCount == 0) {
                binding.dividerCommentUserReply.visibility = View.GONE
                binding.tvCommentUserReplyCount.visibility = View.GONE
            }else{
                binding.tvCommentUserReplyCount.text = "답글 ${commentResult.bigCommentCount}개 보기"
            }
            binding.tvCommentUserLikeCount.text = "${commentResult.likeCount}"
            if(commentResult.likeCount ==0){
                binding.tvCommentUserLikeCount.visibility = View.GONE
            }

            if(commentResult.commentLikeOn.on == 1){
                Glide.with(itemView).load(R.drawable.ic_comment_user_hearton).into(binding.ibtnCommentUserLike)
            }else if(commentResult.commentLikeOn.on ==0){
                Glide.with(itemView).load(R.drawable.ic_comment_user_heartoff).into(binding.ibtnCommentUserLike)
            }

            binding.ibtnCommentUserLike.setOnClickListener {
                clickListener.onLikeClick(binding,adapterPosition)
            }

            binding.ibtnCommentUserProfile.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }

            binding.tvCommentUserReply.setOnClickListener {
                clickListener.onReplyClick(binding,adapterPosition)
//                val sharedPref = ApplicationClass.sSharedPreferences.edit()
//                sharedPref.putInt("group_id", commentResult.commentId)
//                sharedPref.apply()
            }


//            binding.rvCommentBigComment.visibility = View.GONE // 음..

            binding.tvCommentUserReplyCount.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
                CommentService(this@CommentUserAdapter).tryGetBigComment(commentResult.commentId)
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


    override fun onGetPheedCommentSuccess(response: GetCommentUserResponse) {}

    override fun onGetPheedCommentFailure(message: String) {}

    override fun onGetBigCommentSuccess(response: GetBigCommentResponse) {
        Log.d("testtest",response.message.toString())
        Log.d("testtest",response.isSuccess.toString())
        Log.d("testtest",response.code.toString())
        Log.d("testtest",response.bigCommentResult.toString())

        binding.rvCommentBigComment.apply {
            adapter = BigCommentAdapter(response.bigCommentResult,object : BigCommentAdapter.BigCommentClickListener<BigCommentResult>{
                override fun onViewClick(view: BigCommentListBinding, pos: Int) {
                    //어차피 여기 다시 만져야함
                    view.ibtnBigCommentUserLike.setOnClickListener {
                        Glide.with(it).load(R.drawable.ic_comment_user_hearton).into(view.ibtnBigCommentUserLike)
                    }
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            visibility = View.VISIBLE
        }
    }

    override fun onGetBigCommentFailure(message: String) {
        Log.e("error","오류 : $message")
    }

    override fun onPostCommentSuccess(response: PostCommentResponse) {}

    override fun onPostCommentFailure(message: String) {}

    override fun onPostCommentLikeSuccess(response: PostCommentLikeResponse) {
    }

    override fun onPostCommentLikeFailure(message: String) {
    }

    override fun onPatchCommentLikeSuccess(response: PatchCommentLikeResponse) {
    }

    override fun onPatchCommentLikeFailure(message: String) {
    }
}