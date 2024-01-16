package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName


data class GenderResponse(
    @SerializedName("data") var data: ArrayList<GenderData> = arrayListOf()

)

data class GenderData(

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null

)