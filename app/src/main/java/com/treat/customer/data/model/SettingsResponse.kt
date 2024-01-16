package com.treat.customer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class SettingsResponse(
    @SerializedName("data") var data: SettingsData? = SettingsData()

) : TreatResponse()

@Parcelize
data class SettingsData(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("name_en") var nameEn: String? = null,
    @SerializedName("accept_booking_duration_minites") var acceptBookingDurationMinites: String? = null,
    @SerializedName("point_for_one_riyal") var pointForOneRiyal: String? = null,
    @SerializedName("name_ar") var nameAr: String? = null,
    @SerializedName("note_en") var noteEn: String? = null,
    @SerializedName("note_ar") var noteAr: String? = null,
    @SerializedName("address_en") var addressEn: String? = null,
    @SerializedName("address_ar") var addressAr: String? = null,
    @SerializedName("whatsapp") var whatsapp: String? = null,
    @SerializedName("facebook") var facebook: String? = null,
    @SerializedName("twitter") var twitter: String? = null,
    @SerializedName("linkedin") var linkedin: String? = null,
    @SerializedName("banner") var banner: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("video") var video: String? = null,
    @SerializedName("address_lat") var addressLat: String? = null,
    @SerializedName("address_long") var addressLong: String? = null,
    @SerializedName("terms") var terms: String? = null

) : Parcelable