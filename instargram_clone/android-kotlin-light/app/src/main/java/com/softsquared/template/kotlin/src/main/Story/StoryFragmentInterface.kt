package com.softsquared.template.kotlin.src.main.Story

import com.softsquared.template.kotlin.src.main.Story.models.GetFullStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.GetSpecificStoriesResponse
import com.softsquared.template.kotlin.src.main.Story.models.PatchDeleteStoryResponse

interface StoryFragmentInterface {
    fun onGetFullStoriesSuccess(response: GetFullStoriesResponse)

    fun onGetFullStoriesFailure(message : String)

    fun onGetSpecificStoriesSuccess(response: GetSpecificStoriesResponse)

    fun onGetSpecificStoriesFailure(message: String)

    fun onPatchDeleteStorySuccess(response: PatchDeleteStoryResponse)

    fun onPatchDeleteStoryFailure(message: String)
}