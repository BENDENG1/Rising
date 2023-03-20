package com.softsquared.template.kotlin.src.main.Follow.List

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.FollowFollowingListBinding
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.FollowService
import com.softsquared.template.kotlin.src.main.Follow.models.*

class FollowFollowingAdapter(var following : List<Following>,private val clickListener : FollowingClickListner<Following>)
    : RecyclerView.Adapter<FollowFollowingAdapter.FollowFollowingViewHolder>(),FollowFragmentInterface {

    private lateinit var binding :FollowFollowingListBinding
    val myId = ApplicationClass.sSharedPreferences.getInt("my_id",0)

    interface FollowingClickListner<T>{
        fun onViewClick(view : FollowFollowingListBinding, pos : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowFollowingViewHolder {
        binding = FollowFollowingListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FollowFollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowFollowingViewHolder, position: Int) {
        holder.bind(following[position])
    }

    override fun getItemCount(): Int {
        return following.size
    }

    inner class FollowFollowingViewHolder(binding : FollowFollowingListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(following: Following){
            if(following.follow_status ==1){
                binding.btnFollowingFollow.apply {
                    text = "팔로잉"
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                    setBackgroundResource(R.drawable.follow_border_gray_rectangle)
                }
            }
            else if (following.follow_status ==0){
                binding.btnFollowingFollow.apply {
                    text = "팔로우"
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    setBackgroundResource(R.drawable.follow_border_blue_rectangle)
                }
            }

            binding.tvFollowingName.text = following.name
            binding.tvFollowingNickname.text = following.nickname
            Glide.with(itemView).load(following.profile_image_url).into(binding.civFollowingPicture)

            binding.btnFollowingFollow.setOnClickListener {
                if(following.follow_status ==1){
                    FollowService(this@FollowFollowingAdapter).tryPatchUnFollowing(myId,following.user_id)
                }
                else if(following.follow_status ==0){
                    FollowService(this@FollowFollowingAdapter).tryPostDoFollowing(myId,following.user_id)
                }
            }
        }
    }



    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {
        if(response.doFollowingResult.status == 1){
            binding.btnFollowingFollow.apply {
                text = "팔로잉"
                setTextColor(ContextCompat.getColor(context,R.color.black))
                setBackgroundResource(R.drawable.follow_border_gray_rectangle)
            }
        }
    }

    override fun onPostDoFollowingFailure(message: String) {
        Log.e("팔로잉 오류",message)
    }

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {
        if(response.unFollowingResult.status == 0){
            binding.btnFollowingFollow.apply {
                text = "팔로우"
                setTextColor(ContextCompat.getColor(context, R.color.white))
                setBackgroundResource(R.drawable.follow_border_blue_rectangle)
            }
        }
    }

    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {}

    override fun onGetFollowFollowingFailure(message: String) {}

    override fun onPostUnFollowingFailure(message: String) {
        Log.e("팔로잉 오류",message)
    }

    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {}
    override fun onGetFollowFollwerFailure(message: String) {}
    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {}
    override fun onGetFollowAnotherFailure(message: String) {}


}