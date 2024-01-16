package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

open class TreatResponse(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("status") val status: String = "",
    @SerializedName("message") val message: String = "",

)