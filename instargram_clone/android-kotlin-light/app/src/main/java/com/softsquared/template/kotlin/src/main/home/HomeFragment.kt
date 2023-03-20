package com.softsquared.template.kotlin.src.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentHomeBinding
import com.softsquared.template.kotlin.databinding.HomePheedListBinding
import com.softsquared.template.kotlin.databinding.HomeRecommendListBinding
import com.softsquared.template.kotlin.databinding.HomeStoryListBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.Story.HomeStoryAdapter
import com.softsquared.template.kotlin.src.main.Story.StoryFragment
import com.softsquared.template.kotlin.src.main.Commnet.CommentFragment
import com.softsquared.template.kotlin.src.main.Commnet.models.GetBigCommentResponse
import com.softsquared.template.kotlin.src.main.Commnet.models.GetCommentUserResponse
import com.softsquared.template.kotlin.src.main.Follow.FollowFragmentInterface
import com.softsquared.template.kotlin.src.main.Follow.FollowService
import com.softsquared.template.kotlin.src.main.Follow.models.*
import com.softsquared.template.kotlin.src.main.home.Notification.NotificationFragment
import com.softsquared.template.kotlin.src.main.home.models.*
import com.softsquared.template.kotlin.src.main.profile.Fragment.ProfileFragment
import com.softsquared.template.kotlin.src.main.profile.UserProfile.UserProfileFragment
import com.softsquared.template.kotlin.src.main.search.SearchFragmentInterface
import com.softsquared.template.kotlin.src.main.search.SearchService
import com.softsquared.template.kotlin.src.main.search.models.GetSearchIdResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchOneResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchPostResponse
import com.softsquared.template.kotlin.src.main.search.models.SearchIdResult


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),
        HomeFragmentInterface,SearchFragmentInterface, FollowFragmentInterface{


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val myID = sSharedPreferences.getInt("my_id",0)

        //홈화면 피드 화면 구성
        HomeService(this@HomeFragment).tryGetHomePheed()
       //스토리 화면 구성
        HomeService(this@HomeFragment).tryGetHomeStory()

        //알림으로 이동
        moveNotification()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("scrollPosition",binding.nsvHome.scrollY)
        outState.putInt("recyclerViewPosition",binding.rvHomePheed.computeVerticalScrollOffset())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            binding.let { binding ->
                val nsvPosition = it.getInt("scrollPosition")
                val rvPosition = it.getInt("recyclerViewPosition")
                binding.nsvHome.post { binding.nsvHome.scrollTo(0, nsvPosition) }
                binding.rvHomePheed.post {binding.rvHomePheed.scrollToPosition(rvPosition)}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val mainAct = activity as MainActivity
        mainAct.hideNavigationBar(false)
    }

    private fun moveNotification(){
        binding.ibtnHomeToolBarNotification.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, NotificationFragment()).addToBackStack("notification").commit()
        }
    }


    override fun onGetHomePheedSuccess(response: GetHomePheedResponse) {
        Log.d("pheed", response.homePheedResult.toString())
        binding.rvHomePheed.adapter = null //초기화
        binding.rvHomePheed.layoutManager = null //초기화


        if(response.homePheedResult.isEmpty()){
            SearchService(this).tryGetSearchId("li")
            binding.clHomeRecommend.visibility = View.VISIBLE
        }
        else{
            response.homePheedResult.let {
                val adapter = HomePheedAdapter(response.homePheedResult,
                    object : HomePheedAdapter.PheedClickListener<HomePheedResult> {
                        @SuppressLint("SetTextI18n")
                        override fun onViewClick(view: HomePheedListBinding, pos: Int) {

                            view.ivHomePheedProfile.setOnClickListener{
                                //테스트용
                                if(response.homePheedResult[pos].userStoryOn == 1){
                                    val sSharepref = sSharedPreferences.edit()
                                    sSharepref.putInt("user_id",response.homePheedResult[pos].userId)
                                    sSharepref.putBoolean("myStory",false)
                                    sSharepref.apply()
                                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,StoryFragment()).addToBackStack("story").commit()
                                }else{
                                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,UserProfileFragment()).addToBackStack("userProfile").commit()
                                }
                            }
                            view.tvHomePheedName.setOnClickListener {
                                val sharedpref = sSharedPreferences.edit()
                                sharedpref.putInt("user_id",response.homePheedResult[pos].userId)
                                sharedpref.apply()
                                parentFragmentManager.beginTransaction().replace(R.id.main_frm,UserProfileFragment()).addToBackStack("userProfile").commit()
                            }

                            view.ibtnHomePheedScrap.setOnClickListener {
                                if (response.homePheedResult[pos].scrapOn.on ==0){
                                    HomeService(this@HomeFragment).tryPostPheedScrap(response.homePheedResult[pos].postId)
                                    Glide.with(it).load(R.drawable.ic_scrap_on).into(view.ibtnHomePheedScrap)
                                } else if(response.homePheedResult[pos].scrapOn.on ==1){
                                    HomeService(this@HomeFragment).tryPatchPheedScrap(response.homePheedResult[pos].scrapOn.id,false)
                                    Glide.with(it).load(R.drawable.ic_home_scrap).into(view.ibtnHomePheedScrap)
                                }
                            }

                            //좋아요
                            view.ibtnHomePheedLike.setOnClickListener {
                                val likeOn = response.homePheedResult[pos].homePheedLikeOn
                                if(likeOn.on == 1){
                                    HomeService(this@HomeFragment).tryPatchPheedLike(likeOn.id, false)
                                    Glide.with(it).load(R.drawable.ic_home_like).into(view.ibtnHomePheedLike)
                                }else if(likeOn.on == 0){
                                    HomeService(this@HomeFragment).tryPostPheedLike(response.homePheedResult[pos].postId)
                                    Glide.with(it).load(R.drawable.ic_home_pheed_like_on).into(view.ibtnHomePheedLike)
                                }
                                view.tvHomePheedLikeName.text = "좋아요 ${response.homePheedResult[pos].likeCount+1}개"
                            }

                            //댓글창 이동
                            view.ibtnHomePheedComment.setOnClickListener {
//                                sSharedPreferences.edit().putInt("group_id",response.homePheedResult[pos].userId).apply()
                                parentFragmentManager.beginTransaction()
                                    .replace(R.id.main_frm, CommentFragment().apply {
                                        arguments = Bundle().apply {
                                            putInt("postID", response.homePheedResult[pos].postId)
                                            putString("commentProfileName",response.homePheedResult[pos].profileName)
                                            putString("commentProfilePicture",response.homePheedResult[pos].profilePicture)
                                            putString("commentUpdatedAt",response.homePheedResult[pos].updatedAt)
                                            putString("commentContent",response.homePheedResult[pos].content)
                                        }
                                    }).addToBackStack("comment").commit()
                            }
                        }
                    })
                binding.rvHomePheed.adapter = adapter
                binding.rvHomePheed.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }
    override fun onGetHomePheedFailure(message: String) {
        showCustomToast("실패 사유 $message")
    }


    override fun onGetHomeStorySuccess(response: GetHomeStoryResponse) {
        binding.rvHomeStory.apply {
            adapter = HomeStoryAdapter(response.homeStoryResult, object : HomeStoryAdapter.HomeStoryClickListener {
                override fun onClickListener(pos: Int) {
                    val sharedpref = ApplicationClass.sSharedPreferences.edit()
                    if (response.homeStoryResult[pos].self_status == 1) {
                        sharedpref.putBoolean("myStory", true)
                        sharedpref.putInt("user_id", response.homeStoryResult[pos].user_id)
                        sharedpref.apply()
                    } else {
                        sharedpref.putBoolean("myStory", false)
                        sharedpref.putInt("user_id", response.homeStoryResult[pos].user_id)
                        sharedpref.apply()
                    }
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, StoryFragment()).addToBackStack("story")
                        .commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun onGetHomeStoryFailure(message: String) {
        showCustomToast("오류 : $message")
    }


    override fun onPostPheedLikeSuccess(response: PostPheedLikeRespose) {
    }

    override fun onPostPheedLikeFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPatchPheedLikeSuccess(response: PatchPheedLikeResponse) {
        Log.d("testtest_post",response.message.toString())
        Log.d("testtest_post",response.isSuccess.toString())
        Log.d("testtest_post",response.code.toString())
        Log.d("testtest_post",response.result.toString())
    }

    override fun onPatchPheedLikeFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostPheedScrapSuccess(response: PostPheedScrapResponse) {
    }

    override fun onPostPheedScrapFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPatchPheedScrapSuccess(response: PatchPheedScrapResponse) {
    }

    override fun onPatchPheedScrapFailure(message: String) {
        showCustomToast("오류 : $message")
    }


    override fun onGetSearchPostSuceess(response: GetSearchPostResponse) {}
    override fun onGetSearchPostFailure(message: String) {}

    override fun onGetSearchIdSuccess(response: GetSearchIdResponse) {
        binding.rvHomeRecommend.apply {
            adapter = HomeRecommendAdapter(response.searchIdResult, object : HomeRecommendAdapter.HomeRecommendClickListener<SearchIdResult>{
                override fun onFollowClick(view: HomeRecommendListBinding, pos: Int) {
                    val myId = sSharedPreferences.getInt("my_id",0)
                    FollowService(this@HomeFragment).tryPostDoFollowing(myId, response.searchIdResult[pos].user_id)
                    view.btnHomeRecommendFollow.setBackgroundResource(R.drawable.follow_border_gray_rectangle)
                    view.btnHomeRecommendFollow.text = "팔로잉"
                }
                @SuppressLint("CommitPrefEdits")
                override fun onProfileClick(view: HomeRecommendListBinding, pos: Int) {
                    sSharedPreferences.edit().putInt("user_id",response.searchIdResult[pos].user_id).apply()
                    sSharedPreferences.edit().putBoolean("is_user",true)
                    parentFragmentManager.beginTransaction().add(R.id.main_frm,UserProfileFragment()).addToBackStack("userProfile").commit()
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }
    override fun onGetSearchIdFailure(message: String) {
        showCustomToast("오류 : $message")
    }
    override fun onGetSearchOneSuccess(response: GetSearchOneResponse) {}
    override fun onGetSearchOneFailure(message: String) {}


    override fun onGetFollowFollowerSuccess(response: GetFollowFollowersResponse) {}
    override fun onGetFollowFollwerFailure(message: String) {}
    override fun onGetFollowFollowingSuccess(response: GetFollowFollowingResponse) {}
    override fun onGetFollowFollowingFailure(message: String) {}
    override fun onGetFollowAnotherSuccess(response: GetFollowAnotherResponse) {}
    override fun onGetFollowAnotherFailure(message: String) {}


    //이부분 이어서 해야함
    override fun onPostDoFollowingSuccess(response: PostDoFollowingResponse) {
        showCustomToast("테스트 : ${response.doFollowingResult.user_follow_id} 팔로잉 성공!")
    }

    override fun onPostDoFollowingFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onPostUnFollowingSuccess(response: PatchUnFollowingResponse) {

    }

    override fun onPostUnFollowingFailure(message: String) {
        showCustomToast("오류 : $message")
    }
}