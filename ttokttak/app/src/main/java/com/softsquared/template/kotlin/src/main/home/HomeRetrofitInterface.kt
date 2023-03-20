package com.softsquared.template.kotlin.src.main.home

import com.softsquared.template.kotlin.src.main.home.models.HospitalDataResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeRetrofitInterface {


    //666e556d666e6f673130395542784968
    @GET("{apiKey}/json/LOCALDATA_010101_NW/1/100/")
    fun getHospital(@Path("apiKey") apiKey : String) : Call<HospitalDataResponse>

    @GET("666e556d666e6f673130395542784968/json/LOCALDATA_010101_NW/{startNum}/{endNum}")
    fun getDetailHospital(
        @Path("startNum") startNum : Int,
        @Path("endNum") endNum : Int)
    :Call<HospitalDataResponse>
}
