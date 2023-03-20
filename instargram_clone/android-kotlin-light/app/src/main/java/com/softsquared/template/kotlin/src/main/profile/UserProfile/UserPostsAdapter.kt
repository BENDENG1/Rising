package com.softsquared.template.kotlin.src.main.profile.UserProfile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.UserpostslistBinding
import com.softsquared.template.kotlin.src.main.profile.models.ProfilePostResult
import com.softsquared.template.kotlin.util.TimeConversion.Companion.intervalBetweenDateText

class UserPostsAdapter(var profilePostResult : List<ProfilePostResult>,private val clickListener : UserPostsClickListener<ProfilePostResult>)
    : RecyclerView.Adapter<UserPostsAdapter.UserPostsViewHolder>() {

    interface UserPostsClickListener<T>{
        fun onViewClick(view : UserpostslistBinding,pos : Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostsViewHolder {
        val binding = UserpostslistBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return UserPostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserPostsViewHolder, position: Int) {
        holder.bind(profilePostResult[position])
    }

    override fun getItemCount(): Int {
        return profilePostResult.size
    }

    inner class UserPostsViewHolder(val binding : UserpostslistBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(profilePostResult: ProfilePostResult){
            Glide.with(itemView).load(profilePostResult.profilePicture).circleCrop().into(binding.ivUserPostsProfile)
            binding.tvUserPostsName.text = profilePostResult.profileName
            binding.tvUserPostsName2.text = profilePostResult.profileName
            binding.tvUserPostsLikeName.text = "좋아요 ${profilePostResult.likeCount}개"

            if(profilePostResult.photos.size <=1){
                binding.tvUserPostsPageCount.setBackgroundColor(R.color.transparent)
                binding.tvUserPostsPageCount.visibility = View.GONE
            }

            if(profilePostResult.userStoryOn ==1){
                binding.ivUserPostsProfile.borderColor = R.color.transparent
            }

            if(profilePostResult.likeOn.on ==1){
                Glide.with(itemView).load(R.drawable.ic_home_pheed_like_on).into(binding.ibtnUserPostsLike)
            }else{
                Glide.with(itemView).load(R.drawable.ic_home_like).into(binding.ibtnUserPostsLike)
            }

            if(profilePostResult.scrapOn.on ==1){
                Glide.with(itemView).load(R.drawable.ic_scrap_on).into(binding.ibtnUserPostsScrap)
            }else{
                Glide.with(itemView).load(R.drawable.ic_home_scrap).into(binding.ibtnUserPostsScrap)
            }
            if(profilePostResult.content!!.isNotEmpty()){
                binding.tvUserPostsDetail.text = profilePostResult.content
            }
            binding.tvUserPostsWhen.text = intervalBetweenDateText(profilePostResult.createdAt.toString())
            if(profilePostResult.updatedAt!!.isNotEmpty()){
                binding.tvUserPostsWhen.text = intervalBetweenDateText(profilePostResult.updatedAt.toString())
            }

            binding.ivUserPostsProfile.setOnClickListener{
                clickListener.onViewClick(binding,adapterPosition)
            }

            binding.ibtnUserPostsLike.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }
            binding.ibtnUserPostsComment.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }

            binding.ibtnUserPostsScrap.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }
            binding.tvUserPostsName.setOnClickListener{
                clickListener.onViewClick(binding,adapterPosition)
            }

            binding.vpUserPostsPicture.apply {
                adapter = UserPostsPictureAdapter(profilePostResult.photos)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    @SuppressLint("SetTextI18n")
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int,
                    ) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                        binding.tvUserPostsPageCount.text = "${position + 1}/${binding.vpUserPostsPicture.adapter?.itemCount}"
                    }
                })
            }
        }

    }
}