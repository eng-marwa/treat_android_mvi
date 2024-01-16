package com.treat.customer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ServiceCategoriesResponse(
    @SerializedName("data") var data: ArrayList<ServiceCategoriesData> = arrayListOf()
)

@Parcelize
data class ServiceCategoriesData(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("service_type_id") var serviceTypeId: Int? = null,
    @SerializedName("service_type") var serviceType: String? = null

) : Parcelable