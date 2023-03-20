package com.softsquared.template.kotlin.src.main.Story

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.Story.models.GetFullStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.GetSpecificStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.PatchDeleteStoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryService(val storyFragmentInterface: StoryFragmentInterface) {

    fun tryGetFullStories(user_id : Int){
        val storyRetrofitInterface = ApplicationClass.sRetrofit.create(StoryRetrofitInterface::class.java)
        storyRetrofitInterface.getFullStories(user_id = user_id).enqueue(object : Callback<GetFullStoriesResponse>{
            override fun onResponse(call: Call<GetFullStoriesResponse>, response: Response<GetFullStoriesResponse>, ) {
                storyFragmentInterface.onGetFullStoriesSuccess(response.body() as GetFullStoriesResponse)
            }

            override fun onFailure(call: Call<GetFullStoriesResponse>, t: Throwable) {
                storyFragmentInterface.onGetFullStoriesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetSpecificStory(story_id : Int){
        val storyRetrofitInterface = ApplicationClass.sRetrofit.create(StoryRetrofitInterface::class.java)
        storyRetrofitInterface.getSpecificStories(story_id = story_id).enqueue(object:Callback<GetSpecificStoriesResponse>{
            override fun onResponse(call: Call<GetSpecificStoriesResponse>, response: Response<GetSpecificStoriesResponse>, ) {
                storyFragmentInterface.onGetSpecificStoriesSuccess(response.body() as GetSpecificStoriesResponse)
            }

            override fun onFailure(call: Call<GetSpecificStoriesResponse>, t: Throwable) {
                storyFragmentInterface.onGetSpecificStoriesFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPatchDelteStory(story_id : Int){
        val storyRetrofitInterface = ApplicationClass.sRetrofit.create(StoryRetrofitInterface::class.java)
        storyRetrofitInterface.patchDeleteStory(story_id = story_id).enqueue(object :Callback<PatchDeleteStoryResponse>{
            override fun onResponse(
                call: Call<PatchDeleteStoryResponse>,
                response: Response<PatchDeleteStoryResponse>,
            ) {
                storyFragmentInterface.onPatchDeleteStorySuccess(response.body() as PatchDeleteStoryResponse)
            }

            override fun onFailure(call: Call<PatchDeleteStoryResponse>, t: Throwable) {
                storyFragmentInterface.onPatchDeleteStoryFailure(t.message ?: "통신 오류")
            }
        })
    }


}