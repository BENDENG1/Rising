package com.softsquared.template.kotlin.src.main.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.databinding.HomePheedListBinding
import com.softsquared.template.kotlin.src.main.home.models.HomePheedResult
import java.text.SimpleDateFormat
import java.util.*

class HomePheedAdapter(var homePheedResult: List<HomePheedResult>, private val clickListener : PheedClickListener<HomePheedResult>)
    : RecyclerView.Adapter<HomePheedAdapter.HomePheedViewHolder>() {

    interface PheedClickListener<T> {
        fun onViewClick(view : HomePheedListBinding,pos:Int)
    }


//    private lateinit var binding : HomePheedListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePheedViewHolder {
        val binding = HomePheedListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return HomePheedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePheedViewHolder, position: Int) {
        holder.bind(homePheedResult[position])
    }

    override fun getItemCount(): Int {
        return homePheedResult.size
    }

    inner class HomePheedViewHolder(val binding : HomePheedListBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(homePheedResult: HomePheedResult){
            Log.d("----",homePheedResult.toString())
            Glide.with(itemView).load(homePheedResult.profilePicture).circleCrop().into(binding.ivHomePheedProfile)
            binding.tvHomePheedName.text = homePheedResult.profileName
            binding.tvHomePheedName2.text = homePheedResult.profileName
            binding.tvHomePheedLikeName.text = "좋아요 ${homePheedResult.likeCount}개"

            if(homePheedResult.homePheedPhotos.size <=1){
                binding.tvHomePheedPageCount.setBackgroundColor(R.color.transparent)
                binding.tvHomePheedPageCount.visibility =View.GONE
            }

            if(homePheedResult.userStoryOn ==1){
                binding.ivHomePheedProfile.borderColor = R.color.transparent
            }


            if(homePheedResult.homePheedLikeOn.on ==1){
                Glide.with(itemView).load(R.drawable.ic_home_pheed_like_on).into(binding.ibtnHomePheedLike)
            }else{
                Glide.with(itemView).load(R.drawable.ic_home_like).into(binding.ibtnHomePheedLike)
            }

            if(homePheedResult.scrapOn.on ==1){
                Glide.with(itemView).load(R.drawable.ic_scrap_on).into(binding.ibtnHomePheedScrap)
            }else{
                Glide.with(itemView).load(R.drawable.ic_home_scrap).into(binding.ibtnHomePheedScrap)
            }
            if(!homePheedResult.content.isNullOrEmpty()){
                binding.tvHomePheedDetail.text = homePheedResult.content
            }
            binding.tvHomePheedWhen.text = intervalBetweenDateText(homePheedResult.createdAt.toString())
            if(homePheedResult.updatedAt.isNotBlank()){
                binding.tvHomePheedWhen.text = intervalBetweenDateText(homePheedResult.updatedAt.toString())
            }

            binding.ivHomePheedProfile.setOnClickListener{
                clickListener.onViewClick(binding,adapterPosition)
            }

            binding.ibtnHomePheedLike.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }
            binding.ibtnHomePheedComment.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }

            binding.ibtnHomePheedScrap.setOnClickListener {
                clickListener.onViewClick(binding,adapterPosition)
            }
            binding.tvHomePheedName.setOnClickListener{
                clickListener.onViewClick(binding,adapterPosition)
            }

            binding.vpHomePheedPicture.apply {
                adapter = PheedPictureAdapter(homePheedResult.homePheedPhotos)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    @SuppressLint("SetTextI18n")
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int,
                    ) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                        binding.tvHomePheedPageCount.text = "${position + 1}/${binding.vpHomePheedPicture.adapter?.itemCount}"
                    }
                })
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTime(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val getTime = dateFormat.format(date)

        return getTime
    }
    @SuppressLint("SimpleDateFormat")
    private fun intervalBetweenDateText(beforeDate: String): String {
        //현재 시간
        val nowFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(getTime())
        val beforeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(beforeDate)

        val diffSec     = (nowFormat.time - beforeFormat.time) / 1000             //몇 초 전 -> 방금전
        val diffMin     = (nowFormat.time - beforeFormat.time) / (60*1000)         //몇분 전
        val diffHor     = (nowFormat.time - beforeFormat.time) / (60 * 60 * 1000)  //몇시간 전
        val diffDays    = diffSec / (24 * 60 * 60)                                //몇일 전

        if (diffDays > 0){
            return "${diffDays}일 전"
        }
        else if(diffHor > 0){
            return "${diffHor}시간 전"
        }
        else if(diffMin > 0){
            return "${diffMin}분 전"
        }
        else{
            return "방금 전"
        }

    }
}