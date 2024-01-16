package com.treat.customer.data.datasource.remote.api

import com.treat.customer.data.model.AddFavoriteResponse
import com.treat.customer.data.model.AvailableTimeResponse
import com.treat.customer.data.model.BranchDetailsResponse
import com.treat.customer.data.model.BranchesResponse
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.HomeSliderResponse
import com.treat.customer.data.model.LoginResponse
import com.treat.customer.data.model.MyPointsResponse
import com.treat.customer.data.model.MyWalletResponse
import com.treat.customer.data.model.NotificationResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.data.model.ServiceCategoriesResponse
import com.treat.customer.data.model.ServiceTypesResponse
import com.treat.customer.data.model.SettingsResponse
import com.treat.customer.data.model.SpecificServicesResponse
import com.treat.customer.data.model.TransferPointsResponse
import com.treat.customer.data.model.TreatResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        fun createAuthService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }

    @FormUrlEncoded
    @POST("client/login")
    suspend fun login(
        @Field("phone") phone: String,
    ): Response<LoginResponse>

    @POST("client/logout")
    suspend fun logout(
    ): Response<TreatResponse>


    @FormUrlEncoded
    @POST("client/verifyAccount")
    suspend fun verifyAccount(
        @Field("phone") phone: String, @Field("otp") otp: String,
    ): Response<ProfileResponse>

    @FormUrlEncoded
    @POST("client/resendVerifiyOtp")
    suspend fun resendVerifyingOtp(
        @Field("phone") phone: String,
    ): Response<TreatResponse>

    @GET("client/getProfile")
    suspend fun getProfile(
    ): Response<ProfileResponse>

    @FormUrlEncoded
    @POST("client/updateProfile")
    suspend fun updateProfile(
        @Field("name") name: String,
        @Field("gender") gender: String,
        @Field("email") email: String?,
        @Field("birth_date") birthDate: String?
    ): Response<ProfileResponse>

    @FormUrlEncoded
    @POST("client/updateLocation")
    suspend fun updateLocation(
        @Field("address_lat") latitude: Double,
        @Field("address_long") longitude: Double
    ): Response<ProfileResponse>

    @POST("client/disableAccount")
    suspend fun disableAccount(
    ): Response<TreatResponse>

    @GET("general/genders")
    suspend fun getGenderType(
    ): Response<GenderResponse>

    @GET("general/faqs")
    suspend fun getFQA(
    ): Response<FQAResponse>

    @GET("client/wallet")
    suspend fun myWallet(
    ): Response<MyWalletResponse>

    @GET("client/point")
    suspend fun myPoints(
    ): Response<MyPointsResponse>

    @FormUrlEncoded
    @POST("client/pointtowallet")
    suspend fun pointTransfer(
        @Field("id") pointId: String
    ): Response<TransferPointsResponse>

    @GET("general/settings")
    suspend fun getAppSettings(
    ): Response<SettingsResponse>

    @GET("general/sliders")
    suspend fun getHomeSlider(
    ): Response<HomeSliderResponse>

    @GET("client/services/categories")
    suspend fun serviceCategories(
        @Query("service_type") id: String
    ): Response<ServiceCategoriesResponse>

    @GET("general/service-categories")
    suspend fun allCategories(): Response<ServiceCategoriesResponse>

//    @GET("client/branches")
//    suspend fun getBranches(
//        @Query("service_category_id") serviceCategoryId: String,
//        @Query("service_type") serviceType: String
//    ): Response<BranchesResponse>

    @POST("client/branches/location-filtration")
    suspend fun getBranches(
        @Query("categories[]") serviceCategoryIds: ArrayList<String>?,
        @Query("service_type") serviceType: String?,
        @Query("gender") gender: String?,
        @Query("address_lat") lat: String?,
        @Query("address_lng") lng: String?,
    ): Response<BranchesResponse>

    @GET("client/branches/{id}")
    suspend fun getBranchDetails(
        @Path("id") branchId: String
    ): Response<BranchDetailsResponse>

    @FormUrlEncoded
    @POST("client/branches/favourite")
    suspend fun addFavoriteBranch(
        @Field("branch_id") branchId: String
    ): Response<AddFavoriteResponse>

    @GET("client/branches/favourite")
    suspend fun getFavoriteBranches(
    ): Response<BranchesResponse>

    @GET("client/notifications")
    suspend fun getNotifications(
    ): Response<NotificationResponse>

    @GET("client/notifications/{notificationId}")
    suspend fun deleteNotifications(
        @Path("notificationId") notificationId: String
    ): Response<TreatResponse>

    @GET("client/notifications/readOneNotify/{notificationId}")
    suspend fun readNotification(
        @Path("notificationId") notificationId: String
    ): Response<TreatResponse>

    @GET("client/notifications/readNotify")
    suspend fun readAllNotifications(
    ): Response<TreatResponse>

    @GET("general/available-services")
    suspend fun getAvailableTimes(): Response<AvailableTimeResponse>

    @GET("client/services/days")
    suspend fun getScheduleDays(): Response<AvailableTimeResponse>

    @GET("client/services/schedule-specific-day")
    suspend fun getScheduleSpecificDays(
        @Query("service_id") serviceId: String,
        @Query("day") days: String
    ): Response<AvailableTimeResponse>

    @GET("client/services/hours")
    suspend fun getServiceHours(
        @Query("service_id") serviceId: String,
        @Query("day") day: String
    ): Response<AvailableTimeResponse>

    @GET("client/services/free-hours")
    suspend fun getFreeHours(
        @Query("schedule_id") scheduleId: String,
        @Query("date") date: String
    ): Response<AvailableTimeResponse>

    @GET("general/service-types")
    suspend fun serviceTypes(
    ): Response<ServiceTypesResponse>

    @GET("client/services/specific")
    suspend fun specificServices(
        @Query("category_id") categoryId: String, @Query("gender") gender: String
    ): Response<SpecificServicesResponse>

    @GET("general/genders/{serviceId}")
    suspend fun genderByServiceType(
        @Path("serviceId") serviceId: String,
    ): Response<GenderResponse>

}