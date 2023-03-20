package com.softsquared.template.kotlin.src.main.search

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.search.models.GetSearchIdResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchOneResponse
import com.softsquared.template.kotlin.src.main.search.models.GetSearchPostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val searchFragmentInterface: SearchFragmentInterface) {

    fun tryGetSearchPost(){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSearchPost().enqueue(object  : Callback<GetSearchPostResponse>{
            override fun onResponse(call: Call<GetSearchPostResponse>, response: Response<GetSearchPostResponse>, ) {
                searchFragmentInterface.onGetSearchPostSuceess(response.body() as GetSearchPostResponse)
            }

            override fun onFailure(call: Call<GetSearchPostResponse>, t: Throwable) {
                searchFragmentInterface.onGetSearchPostFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryGetSearchId(user_keyword : String){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSearchId(user_keyword = user_keyword).enqueue(object : Callback<GetSearchIdResponse>{
            override fun onResponse(call: Call<GetSearchIdResponse>,response: Response<GetSearchIdResponse>, ) {
                val searchId = response.body()?.searchIdResult
                if(searchId == null || searchId.isEmpty()){
                    val searchIdResult = GetSearchIdResponse(emptyList())
                    searchFragmentInterface.onGetSearchIdSuccess(searchIdResult)
                }else{
                    searchFragmentInterface.onGetSearchIdSuccess(response.body() as GetSearchIdResponse)
                }
            }

            override fun onFailure(call: Call<GetSearchIdResponse>, t: Throwable) {
                searchFragmentInterface.onGetSearchIdFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetSearchOne(post_id : Int){
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSearchOne(post_id = post_id).enqueue(object : Callback<GetSearchOneResponse>{
            override fun onResponse(
                call: Call<GetSearchOneResponse>,
                response: Response<GetSearchOneResponse>,
            ) {
                searchFragmentInterface.onGetSearchOneSuccess(response.body() as GetSearchOneResponse)
            }

            override fun onFailure(call: Call<GetSearchOneResponse>, t: Throwable) {
                searchFragmentInterface.onGetSearchOneFailure(t.message ?: "통신 오류")
            }
        })
    }
}