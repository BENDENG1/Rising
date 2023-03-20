package com.softsquared.template.kotlin.src.main.Story

import com.softsquared.template.kotlin.src.main.Story.models.GetFullStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.GetSpecificStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.PatchDeleteStoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryRetrofitInterface {


    @GET("/app/stories/")
    fun getFullStories(@Query("user-id") user_id : Int) : Call<GetFullStoriesResponse>

    @GET("/app/stories/{story-id}")
    fun getSpecificStories(@Path("story-id") story_id : Int) : Call<GetSpecificStoriesResponse>

    @PATCH("/app/stories/{story-id}")
    fun patchDeleteStory(@Path("story-id")story_id: Int) : Call<PatchDeleteStoryResponse>
}
