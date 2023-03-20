package com.softsquared.template.kotlin.src.main.Commnet

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.CommentUserListBinding
import com.softsquared.template.kotlin.databinding.FragmentCommentBinding
import com.softsquared.template.kotlin.src.main.Commnet.models.*
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.home.HomeFragmentInterface
import com.softsquared.template.kotlin.src.main.home.HomeService
import com.softsquared.template.kotlin.src.main.home.models.GetHomePheedResponse
import com.softsquared.template.kotlin.src.main.home.models.GetHomeStoryResponse
import com.softsquared.template.kotlin.util.TimeConversion
import java.util.regex.Pattern

class CommentFragment : BaseFragment<FragmentCommentBinding>(FragmentCommentBinding::bind,R.layout.fragment_comment)
    ,CommentFragmentInterface{

    //대댓글인지
    private var isGroup = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val postID = requireArguments().getInt("postID",0)


        commentWriter() //작성자 bundle통해 받아옴과 동시에 진행

        CommentService(this).tryGetPheedComment(postId = postID)
//        context?.let { showLoadingDialog(it) }

        binding.tvCommentPost.setOnClickListener {
            val comment = binding.edtCommentDetail.text.toString()
//            val groupID = sSharedPreferences.getInt("group_id",0)
            if(isGroup == 0){
                CommentService(this).tryPostComment(PostCommentRequest(postID,0,comment))
            }else{
                CommentService(this).tryPostComment(PostCommentRequest(postID,isGroup,comment))
            }
            binding.edtCommentDetail.setText("")
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.edtCommentDetail.windowToken,0)
        }


        //댓글 작성할때 유효성 검사
        commentCheck(binding.edtCommentDetail)
        commentEmotion()
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(true)
    }


    private fun commentWriter(){
        val commentProfileName = requireArguments().getString("commentProfileName",null)
        val commentProfilePicture = requireArguments().getString("commentProfilePicture",null)
        val commentUpdatedAt =requireArguments().getString("commentUpdatedAt",null)
        val commentContent = requireArguments().getString("commentContent",null)
        val myProfile = sSharedPreferences.getString("myProfile", null)

        binding.tvCommentID.text = commentProfileName
        binding.tvCommentCreated.text = TimeConversion.intervalBetweenDateText(commentUpdatedAt)
        binding.tvCommentDetail.text = commentContent
        Glide.with(this@CommentFragment).load(commentProfilePicture).into(binding.civCommentProfile)
        Glide.with(this@CommentFragment).load(myProfile).into(binding.civCommentMyProfilePicture)

    }

    private fun commentCheck(editText: EditText){
        editText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.edtCommentDetail.text.toString().isEmpty()) {
                    binding.tvCommentPost.setTextColor(ContextCompat.getColor(context!!,R.color.tv_comment_post_before_blueSky))
                    binding.tvCommentPost.isClickable = false
                }else{
                    binding.tvCommentPost.setTextColor(ContextCompat.getColor(context!!,R.color.tv_comment_post_after_blueSky))
                    binding.tvCommentPost.isClickable = true

                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
    private fun commentEmotion(){
        binding.ibtnCommentEmotionHeart.setOnClickListener {
            binding.edtCommentDetail.text.append("❤️")
        }
        binding.ibtnCommentEmotionClap.setOnClickListener {
            binding.edtCommentDetail.text.append("\uD83D\uDE4C")
        }
        binding.ibtnCommentEmotionFire.setOnClickListener {
            binding.edtCommentDetail.text.append("\uD83D\uDD25")
        }
        binding.ibtnCommentEmotionHandClap.setOnClickListener {
            binding.edtCommentDetail.text.append("\uD83D\uDC4F")
        }
        binding.ibtnCommentEmotionCry.setOnClickListener {
            binding.edtCommentDetail.text.append("\uD83D\uDE22")
        }
        binding.ibtnCommentEmotionLove.setOnClickListener {
            binding.edtCommentDetail.text.append("\uD83D\uDE0D")
        }
        binding.ibtnCommentEmotionOh.setOnClickListener {
            binding.edtCommentDetail.text.append("\uD83D\uDE2E")
        }
        binding.ibtnCommentEmotionLaughCry.setOnClickListener {
            binding.edtCommentDetail.text.append("\uD83D\uDE02")
        }

    }


    override fun onGetPheedCommentSuccess(response: GetCommentUserResponse) {

        binding.ibtnCommentMeesage.setOnClickListener {
            //여기다가 message
        }

        binding.rvComment.apply {
            adapter = CommentUserAdapter(response.commentResult, object : CommentUserAdapter.CommentUserClickListener<CommentResult> {
                @SuppressLint("SetTextI18n")
                override fun onViewClick(view: CommentUserListBinding, pos: Int) {


                }

                @SuppressLint("SetTextI18n")
                override fun onReplyClick(view: CommentUserListBinding, pos: Int) {
//                    val sharedPref = ApplicationClass.sSharedPreferences.edit()
//                    sharedPref.putInt("group_id", response.commentResult[pos].groupId)
//                    sharedPref.apply()
                    isGroup = response.commentResult[pos].commentId
                    binding.edtCommentDetail.setText("@${response.commentResult[pos].profileName} ")
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(binding.edtCommentDetail,0)
                }

                override fun onLikeClick(view: CommentUserListBinding, pos: Int) {
                    Log.d("testtest", "클릭함")
                    if (response.commentResult[pos].commentLikeOn.on == 0) {
                        Glide.with(this@CommentFragment).load(R.drawable.ic_comment_user_hearton)
                            .into(view.ibtnCommentUserLike)
                        view.tvCommentUserLikeCount.text =
                            (response.commentResult[pos].likeCount + 1).toString()
                        view.tvCommentUserLikeCount.visibility = View.VISIBLE
                        CommentService(this@CommentFragment)
                            .tryPostCommentLike(response.commentResult[pos].commentId)
                    } else if (response.commentResult[pos].commentLikeOn.on == 1) {
                        if (response.commentResult[pos].likeCount - 1 <= 0) {
                            view.tvCommentUserLikeCount.visibility = View.GONE
                        }
                        Glide.with(this@CommentFragment).load(R.drawable.ic_comment_user_heartoff)
                            .into(view.ibtnCommentUserLike)
                        CommentService(this@CommentFragment)
                            .tryPatchCommentLike(response.commentResult[pos].commentLikeOn.id,
                                status = false)
                    }
                }
            })
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }

    }

    override fun onGetPheedCommentFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }

    override fun onGetBigCommentSuccess(response: GetBigCommentResponse) {}

    override fun onGetBigCommentFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostCommentSuccess(response: PostCommentResponse) {
        Log.d("testtest_postcomment",response.message.toString())
        Log.d("testtest_postcomment",response.isSuccess.toString())
        Log.d("testtest_postcomment",response.code.toString())
    }

    override fun onPostCommentFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostCommentLikeSuccess(response: PostCommentLikeResponse) {
    }

    override fun onPostCommentLikeFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPatchCommentLikeSuccess(response: PatchCommentLikeResponse) {
    }

    override fun onPatchCommentLikeFailure(message: String) {
        showCustomToast("오류 : $message")
    }

}