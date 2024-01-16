package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class AvailableTimeResponse (
    @SerializedName("data"    ) var data    : ArrayList<AvailableTimeData> = arrayListOf())
data class AvailableTimeData (

    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("time" ) var time : String? = null,
    @SerializedName("type" ) var type : String? = null

)