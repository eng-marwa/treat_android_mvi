package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class BranchesResponse(
    @SerializedName("data") var data: BranchesData? = BranchesData()
) : TreatResponse()

data class BranchesData(

    @SerializedName("notification_count") var notificationCount: String? = null,
    @SerializedName("branches") var branches: ArrayList<Branches> = arrayListOf()
)

data class Branches(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("parent_id") var parentId: String? = null,
    @SerializedName("logo") var logo: String? = null,
    @SerializedName("address_lat") var addressLat: String? = null,
    @SerializedName("address_long") var addressLong: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("is_special") var isSpecial: Boolean = false,
    @SerializedName("service_type") var serviceType: String? = null,
    @SerializedName("verify_phone") var verifyPhone: String? = null,
    @SerializedName("target_client_gender") var targetClientGender: String? = null,
    @SerializedName("rate") var rate: String? = null,
    @SerializedName("rates_count") var ratesCount: String? = null,
    @SerializedName("rates_status") var ratesStatus: String? = null,
    @SerializedName("coupons") var coupons: Coupons? = Coupons(),
    @SerializedName("is_favourite") var isFavourite: Boolean = false,
    @SerializedName("distance") var distance: String? = null
)
