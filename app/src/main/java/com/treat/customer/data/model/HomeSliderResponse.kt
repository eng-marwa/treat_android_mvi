package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class HomeSliderResponse(
    @SerializedName("data") var data: ArrayList<HomeSliderData> = arrayListOf()

)

data class HomeSliderData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("branch_id") var branchId: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("image") var image: String? = null

)