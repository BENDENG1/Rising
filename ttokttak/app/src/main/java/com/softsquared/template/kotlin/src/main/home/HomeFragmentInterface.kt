package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.src.main.home.models.HospitalDataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface HomeFragmentInterface {
    fun onGetHospitalSuccess(response : HospitalDataResponse)

    fun onGetHospitalFailure(message : String)

    fun onGetDetailHospitalSuccess(response: HospitalDataResponse)

    fun onGetDetailHospitalFailure(message: String)
}