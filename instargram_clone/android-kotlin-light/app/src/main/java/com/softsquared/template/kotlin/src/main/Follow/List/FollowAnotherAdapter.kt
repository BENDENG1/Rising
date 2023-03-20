package com.softsquared.template.kotlin.src.main.Follow.List

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.FollowAnotherListBinding
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.FollowService
import com.softsquared.template.kotlin.src.main.Follow.models.*

class FollowAnotherAdapter(var another : List<ConnectedFollow>, private val clickListner: AnotherFollowClickListener<ConnectedFollow>)
    : RecyclerView.Adapter<FollowAnotherAdapter.FollowAnotherViewHolder>(),FollowFragmentInterface {

    private lateinit var binding : FollowAnotherListBinding
    val myId = ApplicationClass.sSharedPreferences.getInt("my_id",0)

    interface AnotherFollowClickListener<T>{
        fun onViewClick(view : FollowAnotherListBinding, pos : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowAnotherViewHolder {
        binding = FollowAnotherListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FollowAnotherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowAnotherViewHolder, position: Int) {
        holder.bind(another[position])

        holder.binding.btnAnotherFollow.setOnClickListener {
            clickListner.onViewClick(binding,position)
        }
    }

    override fun getItemCount(): Int {
        return another.size
    }


    inner class FollowAnotherViewHolder(val binding : FollowAnotherListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(another : ConnectedFollow){
            if(another.follow_status ==1){
                binding.btnAnotherFollow.apply {
                    text = "팔로잉"
                    setTextColor(ContextCompat.getColor(context, R.color.black))
                    setBackgroundResource(R.drawable.follow_border_gray_rectangle)
                }
            }
            else if (another.follow_status == 0) {
                binding.btnAnotherFollow.apply {
                    text = "팔로우"
                    setTextColor(ContextCompat.getColor(context, R.color.white))
                    setBackgroundResource(R.drawable.follow_border_blue_rectangle)
                }
            }

            binding.tvAnotherName.text = another.name
            binding.tvAnotherNickname.text = another.nickname
            Glide.with(itemView).load(another.profile_image_url).into(binding.civAnotherPicture)

//            binding.btnAnotherFollow.setOnClickListener {
//                if(another.follow_status == 1){
//                    FollowService(this@FollowAnotherAdapter).tryPatchUnFollowing(myId,another.user_id)
//                }
//                if(another.follow_status == 0){
//                    FollowService(this@FollowAnotherAdapter).tryPostDoFollowing(myId,another.user_id)
//                }
//            }
        }
    }

    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {
//        if(response.doFollowingResult.status == 1){
//            binding.btnAnotherFollow.apply {
//                text = "팔로잉"
//                setTextColor(ContextCompat.getColor(context,R.color.black))
//                setBackgroundResource(R.drawable.follow_border_gray_rectangle)
//            }
//        }
    }

    override fun onPostDoFollowingFailure(message: String) {
        Log.e("팔로잉 오류",message)
    }

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {
//        if(response.unFollowingResult.status == 0){
//            binding.btnAnotherFollow.apply {
//                text = "팔로우"
//                setTextColor(ContextCompat.getColor(context, R.color.white))
//                setBackgroundResource(R.drawable.follow_border_blue_rectangle)
//            }
//        }
    }

    override fun onPostUnFollowingFailure(message: String) {
        Log.e("팔로잉 오류",message)
    }

    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {}
    override fun onGetFollowAnotherFailure(message: String) {}
    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {}
    override fun onGetFollowFollwerFailure(message: String) {}
    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {}
    override fun onGetFollowFollowingFailure(message: String) {}
}