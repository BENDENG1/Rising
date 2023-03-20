package com.softsquared.template.kotlin.src.main.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass.Companion.sSharedPreferences
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentSearchBinding
import com.softsquared.template.kotlin.databinding.SearchIdListBinding
import com.softsquared.template.kotlin.databinding.SearchPostListBinding
import com.softsquared.template.kotlin.src.main.Story.StoryFragment
import com.softsquared.template.kotlin.src.main.profile.UserProfile.UserProfileFragment
import com.softsquared.template.kotlin.src.main.search.SearchFragmentInterface
import com.softsquared.template.kotlin.src.main.search.SearchIdAdapter
import com.softsquared.template.kotlin.src.main.search.SearchPostAdapter
import com.softsquared.template.kotlin.src.main.search.SearchService
import com.softsquared.template.kotlin.src.main.search.models.*
import com.softsquared.template.kotlin.util.GridSpacingItemDecoration

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search),
    SearchFragmentInterface {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SearchService(this).tryGetSearchPost()
        
        binding.btnTest.setOnClickListener {
            val searchId = binding.edtSearchNickName.text.toString()
            if(searchId.isNotEmpty()){
                SearchService(this).tryGetSearchId(searchId)
            }
        }

        binding.edtSearchNickName.setOnEditorActionListener { textView, i, keyEvent ->
            if(keyEvent!= null &&keyEvent.action ==EditorInfo.IME_ACTION_DONE){
                Log.d("testtest키보드","제발!!!")
                val searchId = binding.edtSearchNickName.text.toString()
                if(searchId.isNotEmpty()){
                    SearchService(this).tryGetSearchId(searchId)
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun onGetSearchPostSuceess(response: GetSearchPostResponse) {
        binding.rvSearchPost.apply {
            setHasFixedSize(true)
            adapter = SearchPostAdapter(response.searchPostResult,object : SearchPostAdapter.SearchClickListener<SearchPostResult> {
                override fun onViewClick(view: SearchPostListBinding, pos: Int) {
                    Log.d("testtest",response.searchPostResult.toString())
                    Log.d("testtest",response.message.toString())
                    Log.d("testtest",response.code.toString())
                    Log.d("testtest",response.isSuccess.toString())
                    sSharedPreferences.edit().putInt("post_id",response.searchPostResult[pos].postId).apply()
                    parentFragmentManager.beginTransaction().replace(R.id.main_frm,SearchPostFragment()).addToBackStack("searchOne").commit()
                }
            })
            layoutManager = GridLayoutManager(context,3)
            addItemDecoration(GridSpacingItemDecoration(5,3))
        }
    }

    override fun onGetSearchPostFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onGetSearchIdSuccess(response: GetSearchIdResponse) {
        Log.d("testtest",response.searchIdResult.toString())
        Log.d("testtest",response.message.toString())
        Log.d("testtest",response.code.toString())
        Log.d("testtest",response.isSuccess.toString())
        binding.rvSearchPost.visibility = View.GONE
        binding.rvSearchId.apply {
            adapter = SearchIdAdapter(response.searchIdResult, object : SearchIdAdapter.SearchIdClickListener<SearchIdResult> {
                override fun onIDClick(view: SearchIdListBinding, pos: Int) {
                    Log.d("testtest",response.searchIdResult[pos].nickname.toString())
                    sSharedPreferences.edit().putInt("user_id",response.searchIdResult[pos].user_id).apply()
                    parentFragmentManager.beginTransaction().add(R.id.main_frm,UserProfileFragment()).addToBackStack("userProfile").commit()
                }

                override fun onProfileClick(view: SearchIdListBinding, pos: Int) {
                    if(response.searchIdResult[pos].story_status == 1){
                        val sharedpref = sSharedPreferences.edit()
                        sharedpref.putInt("user_id",response.searchIdResult[pos].user_id)
                        sharedpref.putBoolean("myStory",false)
                        sharedpref.apply()
                        parentFragmentManager.beginTransaction().add(R.id.main_frm,StoryFragment()).addToBackStack("story").commit()
                    }else{
                        sSharedPreferences.edit().putInt("user_id",response.searchIdResult[pos].user_id).apply()
                        parentFragmentManager.beginTransaction().add(R.id.main_frm,UserProfileFragment()).addToBackStack("userProfile").commit()
                    }
                }
            })
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }

    }

    override fun onGetSearchIdFailure(message: String) {
        showCustomToast("오류 : $message")
    }

    override fun onGetSearchOneSuccess(response: GetSearchOneResponse) {}

    override fun onGetSearchOneFailure(message: String) {}
}