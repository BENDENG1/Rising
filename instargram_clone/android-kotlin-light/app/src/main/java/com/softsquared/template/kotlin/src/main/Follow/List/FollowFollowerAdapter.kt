package com.softsquared.template.kotlin.src.main.Follow.List

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.databinding.FollowFollowerListBinding
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.FollowService
import com.softsquared.template.kotlin.src.main.Follow.models.*

class FollowFollowerAdapter(var follower: List<Follower>, private val clickListner : FollowClickListener<Follower>)
    : RecyclerView.Adapter<FollowFollowerAdapter.FollowFollowerViewHolder>(),
    FollowFragmentInterface {

    val myId = sSharedPreferences.getInt("my_id",0)


    interface FollowClickListener<T>{
        fun onViewClick(view : FollowFollowerListBinding, pos : Int)
    }

    private lateinit var binding : FollowFollowerListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowFollowerViewHolder {
        binding = FollowFollowerListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FollowFollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowFollowerViewHolder, position: Int) {
        holder.bind(follower[position])
    }

    override fun getItemCount(): Int {
        return follower.size
    }

    inner class FollowFollowerViewHolder(binding : FollowFollowerListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(follower : Follower){
            if(follower.follow_status == 1){
                binding.btnFollowersFollow.apply {
                    text = "팔로잉"
                    setTextColor(ContextCompat.getColor(context,R.color.black))
                    setBackgroundResource(R.drawable.follow_border_gray_rectangle)
                }
            }
            if (follower.follow_status == 0) {
                binding.btnFollowersFollow.apply {
                    text = "팔로우"
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    setBackgroundResource(R.drawable.follow_border_blue_rectangle)
                }
            }
            binding.tvFollowersNickname.text = follower.nickname
            binding.tvFollowersName.text = follower.name
            Glide.with(itemView).load(follower.profile_image_url).into(binding.civFollowersPicture)

            binding.btnFollowersFollow.setOnClickListener {
                if(follower.follow_status == 1){
                    FollowService(this@FollowFollowerAdapter).tryPatchUnFollowing(myId,follower.user_id)
                }
                if(follower.follow_status == 0){
                    FollowService(this@FollowFollowerAdapter).tryPostDoFollowing(myId,follower.user_id)
                }
            }

        }
    }


    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {

        if(response.doFollowingResult.status == 1){
            binding.btnFollowersFollow.apply {
                text = "팔로잉"
                setTextColor(ContextCompat.getColor(context,R.color.black))
                setBackgroundResource(R.drawable.follow_border_gray_rectangle)
            }
        }
    }

    override fun onPostDoFollowingFailure(message: String) {
        Log.e("팔로잉 오류",message)
    }

    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {}

    override fun onGetFollowFollwerFailure(message: String) {}

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {
        if(response.unFollowingResult.status == 0){
            binding.btnFollowersFollow.apply {
                text = "팔로우"
                setTextColor(ContextCompat.getColor(context, R.color.white))
                setBackgroundResource(R.drawable.follow_border_blue_rectangle)
            }
        }
    }

    override fun onPostUnFollowingFailure(message: String) {
        Log.e("팔로잉 오류",message)
    }

    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {
    }

    override fun onGetFollowFollowingFailure(message: String) {
    }

    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {
    }

    override fun onGetFollowAnotherFailure(message: String) {
    }
}