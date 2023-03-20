package com.softsquared.template.kotlin.src.main.search

import com.softsquared.template.kotlin.src.main.search.models.GetSearchIdResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchOneResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchPostResponse

interface SearchFragmentInterface {

    fun onGetSearchPostSuceess(response : GetSearchPostResponse)

    fun onGetSearchPostFailure(message : String)

    fun onGetSearchIdSuccess(response : GetSearchIdResponse)

    fun onGetSearchIdFailure(message: String)

    fun onGetSearchOneSuccess(response: GetSearchOneResponse)

    fun onGetSearchOneFailure(message: String)

}