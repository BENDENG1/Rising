package com.gyroh.a5th_week.Main


import com.gyroh.a5th_week.Main.Models.RestaurantResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Restaurant {

    //GET,POST,PUT,DELETE,HEAD중에서 작업 선택
    //전체 URL에서 BASEURL을 제외한 End Point
//    @GET("3053840/v1/uddi:9cb85e53-3592-4a8d-9b07-eec77edbc8c2")
//    fun getRestaurant(
//        @Query("소재지(지번)") address :String,
//        @Query("업소명") name : String,
//        @Query("업태명") sector :String,
//        @Query("연번") serial : Int,
//        @Query("주취급음식") mainFood :String,
//
//    ): Call<RestaurantResponse>
    @GET("3053840/v1/uddi:9cb85e53-3592-4a8d-9b07-eec77edbc8c2")
    fun getRestaurant(
        @Query("serviceKey") serviceKey : String,
        @Query("page") page : Int,
        @Query("perPage") perpage : Int
    ) : Call<RestaurantResponse> //응답이 왔을때 callback으로 불려질 타입 RestaurantResponse로 설정

}