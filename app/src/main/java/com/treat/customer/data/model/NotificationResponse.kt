package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("data") var data: NotificationData? = NotificationData(),

    )

data class NotificationData(

    @SerializedName("count") var count: Int? = 0,
    @SerializedName("notifications") var notifications: ArrayList<Notifications> = arrayListOf()

)

data class Notifications(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var body: String? = null,
    @SerializedName("sender") var sender: Sender? = Sender(),
    @SerializedName("read") var read: Boolean? = null,
    @SerializedName("order_id") var orderId: Int? = null,
    @SerializedName("classification") var classification: String? = null,
    @SerializedName("date") var date: String? = null,
    @SerializedName("date_1") var date1: String? = null
)

data class Sender(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null

)