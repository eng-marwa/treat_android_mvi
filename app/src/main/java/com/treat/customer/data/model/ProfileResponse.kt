package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("data") var data: ProfileData = ProfileData(),
)

data class ProfileData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("client_status") var accountStatus: String? = null,
    @SerializedName("has_gifts") var hasGifts: Boolean? = false,
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("address_lat") var addressLat: String? = null,
    @SerializedName("address_long") var addressLong: String? = null,
    @SerializedName("verify_phone") var verifyPhone: String? = null,
    @SerializedName("completed_profile") var completedProfile: Boolean? = null,
    @SerializedName("token") var token: String? = null
) : TreatResponse()