package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("logo") var logo: String? = null,
    @SerializedName("address_lat") var addressLat: String? = null,
    @SerializedName("address_long") var addressLong: String? = null,
    @SerializedName("client_status") var accountStatus: String? = null,
    @SerializedName("has_gifts") var hasGifts: Boolean? = false,
    @SerializedName("completed_profile") var completedProfile: Boolean? = false,
    @SerializedName("birth_date") var birthDate: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("verify_phone") var verifyPhone: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("token") var token: String? = null
) : TreatResponse()
