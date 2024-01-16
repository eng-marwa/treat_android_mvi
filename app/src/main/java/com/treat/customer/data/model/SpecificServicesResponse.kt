package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class SpecificServicesResponse(
    @SerializedName("data") var data: List<Services> = arrayListOf()
):TreatResponse()

