package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.home.models.HospitalDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val homeFragmentInterface: HomeFragmentInterface) {

    fun tryGetHospital(apiKey : String){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getHospital(apiKey = apiKey).enqueue(object :Callback<HospitalDataResponse>{
            override fun onResponse(call: Call<HospitalDataResponse>, response: Response<HospitalDataResponse>, ) {
                homeFragmentInterface.onGetHospitalSuccess(response.body() as HospitalDataResponse)
            }

            override fun onFailure(call: Call<HospitalDataResponse>, t: Throwable) {
                homeFragmentInterface.onGetHospitalFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetDetailHospital(startNum : Int, endNum : Int){
        val homeRetrofitInterface = ApplicationClass.sRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getDetailHospital(startNum, endNum).enqueue(object :Callback<HospitalDataResponse>{
            override fun onResponse(call: Call<HospitalDataResponse>, response: Response<HospitalDataResponse>, ) {
                homeFragmentInterface.onGetDetailHospitalSuccess(response.body() as HospitalDataResponse)
            }

            override fun onFailure(call: Call<HospitalDataResponse>, t: Throwable) {
                homeFragmentInterface.onGetDetailHospitalFailure(t.message ?: "통신 오류")
            }
        })
    }

}
