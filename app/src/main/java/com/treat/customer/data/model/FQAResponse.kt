package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class FQAResponse(

    @SerializedName("data") var data: ArrayList<FQAData> = arrayListOf()

) : TreatResponse()

data class FQAData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("note") var note: String? = null

)