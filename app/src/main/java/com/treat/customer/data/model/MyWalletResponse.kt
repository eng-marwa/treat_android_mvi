package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class MyWalletResponse(
    @SerializedName("data") var data : MyWalletData =MyWalletData()
)

data class MyWalletData(

    @SerializedName("paid") var paid: Int? = null,
    @SerializedName("unpaid") var unpaid: Int? = null,
    @SerializedName("details") var details: ArrayList<MyWalletDetails> = arrayListOf()

)

data class MyWalletDetails(

    @SerializedName("amount") var amount: Int? = null,
    @SerializedName("reason") var reason: String? = null,
    @SerializedName("created_at") var createdAt: String? = null

)