package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName


data class TransferPointsResponse(
    @SerializedName("data") var data: TransferPointsData? = TransferPointsData()
)

data class TransferPointsData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("order_id") var orderId: Int? = null,
    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("paid") var paid: Boolean? = null,
    @SerializedName("created_at") var createdAt: String? = null

)