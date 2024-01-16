package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("data") var data: NotificationData? = NotificationData(),

    )

data class NotificationData(

    @SerializedName("count") var count: Int? = 0,
    @SerializedName("notifications") var notifications: ArrayList<String> = arrayListOf()

)