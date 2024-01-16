package com.treat.customer.data.model

import com.google.gson.annotations.SerializedName

data class BranchDetailsResponse(
    @SerializedName("data") var data: BranchDetailsData? = BranchDetailsData()
) : TreatResponse()

data class BranchDetailsData(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("about") var about: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("parent_id") var parentId: String? = null,
    @SerializedName("logo") var logo: String? = null,
    @SerializedName("address_lat") var addressLat: String? = null,
    @SerializedName("address_long") var addressLong: String? = null,
    @SerializedName("start_work") var startWork: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("end_work") var endWork: String? = null,
    @SerializedName("status") var status: Int? = null,
    @SerializedName("status_trans") var statusTrans: String? = null,
    @SerializedName("service_type") var serviceType: String? = null,
    @SerializedName("verify_phone") var verifyPhone: String? = null,
    @SerializedName("services") var services: ArrayList<Services> = arrayListOf(),
    @SerializedName("is_special") var isSpecial: Boolean = false,
    @SerializedName("rate") var rate: String? = "0",
    @SerializedName("coupons") var coupons: Coupons? = null,
    @SerializedName("is_favourite") var isFavourite: Boolean = false,
    @SerializedName("distance") var distance: String? = null,
    @SerializedName("opening_hours") var openingHours: ArrayList<OpeningHours> = arrayListOf(),
    @SerializedName("rates_count") var rates_count: String? = "0",
    @SerializedName("address") var address: String? = null,
    @SerializedName("rates_status") var rates_status: String? = "0",
    @SerializedName("images") var images: ArrayList<Images> = arrayListOf(),
    @SerializedName("employees") var employees: ArrayList<Employees> = arrayListOf()

)

data class Images (

    @SerializedName("id"  ) var id  : Int?    = null,
    @SerializedName("url" ) var url : String? = null

)
data class Employees(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("job_description") var jobDescription: String? = null,
    @SerializedName("branch_id") var branchId: Int? = null,
    @SerializedName("services") var services: ArrayList<String> = arrayListOf()

)

data class Services(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("service_category_id") var serviceCategoryId: Int? = null,
    @SerializedName("service_category_name") var serviceCategoryName: String? = null,
    @SerializedName("branch_id") var branchId: Int? = null,
    @SerializedName("branch_name") var branchName: String? = null,
    @SerializedName("regular_price") var regularPrice: Int? = null,
    @SerializedName("sale_price") var salePrice: Int? = null,
    @SerializedName("estimated_time") var estimatedTime: Int? = null,
    @SerializedName("target_client_gender") var targetClientGender: String? = null,
    @SerializedName("service_type") var serviceType: String? = null

)

data class OpeningHours(
    @SerializedName("day") var day: String? = null,
    @SerializedName("start") var start: String? = null,
    @SerializedName("end") var end: String? = null

)

data class CouponType(
    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null
)

data class Coupons(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("branch_id") var branchId: String? = null,
    @SerializedName("user_id") var userId: String? = null,
    @SerializedName("service_id") var serviceId: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("date_from") var dateFrom: String? = null,
    @SerializedName("date_to") var dateTo: String? = null,
    @SerializedName("coupon_type") var couponType: CouponType? = CouponType(),
    @SerializedName("value") var value: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("title") var title: String? = null
)