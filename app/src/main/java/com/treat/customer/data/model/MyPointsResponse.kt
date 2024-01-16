package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName


data class MyPointsResponse(

    @SerializedName("data"    ) var data    : MyPointsData?    = MyPointsData()

) : TreatResponse()

data class MyPointsData(

    @SerializedName("paid") var paid: String? = null,
    @SerializedName("last_update") var lastUpdate: String? = null,
    @SerializedName("unpaid") var unpaid: String? = null,
    @SerializedName("details") var details: ArrayList<MyPointsDetails> = arrayListOf()

)

data class MyPointsDetails(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("order_id") var orderId: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("amount_in_sar") var amountInSar: Int? = null,
    @SerializedName("paid") var paid: Boolean? = null,
    @SerializedName("created_at") var createdAt: String? = null

)