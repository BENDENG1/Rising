package com.gyroh.a5th_week.Main

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "https://api.odcloud.kr/api/"

    //conntecttimeout()을 호출하여 최대 요청시간을 줄 수 있음
    val okHttpClient = OkHttpClient.Builder()
//        .connectTimeout(120, TimeUnit.SECONDS)
//        .readTimeout(120,TimeUnit.SECONDS)
//        .writeTimeout(120,TimeUnit.SECONDS)
        .build()

    val gson = GsonBuilder()
        .setLenient() //setLenient -> parser로 하여금 허용을 자유롭게 (원래 엄격함)
        .create()


    //addConverterFactory -> 코틀린으로 사용할 수 있도록 데이터를 파싱하는 컨버터를 사용용
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) //baseUrl을 등록 (반드시 /로 마무리)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    val restaurant : Restaurant by lazy { retrofit.create(Restaurant::class.java) }
}