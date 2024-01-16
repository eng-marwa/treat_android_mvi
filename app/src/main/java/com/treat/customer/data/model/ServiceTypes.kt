package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class ServiceTypesResponse(
    @SerializedName("data") var data: List<ServiceTypeData> = arrayListOf()
):TreatResponse()

data class ServiceTypeData(
    @SerializedName("id"   ) var id   : String?    = null,
    @SerializedName("name" ) var name : String? = null,
    @SerializedName("note" ) var note : String? = null

)