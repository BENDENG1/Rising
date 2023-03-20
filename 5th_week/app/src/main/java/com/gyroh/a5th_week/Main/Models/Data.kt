package com.gyroh.a5th_week.Main.Models

import com.google.gson.annotations.SerializedName

data class Data(

    //카이트는 신인가?
    @SerializedName("소재지(지번)")val address :String,
    @SerializedName("업소명")val name :String,
    @SerializedName("업태명")val sector :String,
    @SerializedName("연번")val serial :String,
    @SerializedName("주취급음식")val mainFood :String

)
