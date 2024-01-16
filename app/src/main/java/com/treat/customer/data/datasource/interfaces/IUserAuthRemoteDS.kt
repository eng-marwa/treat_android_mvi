package com.treat.customer.data.datasource.interfaces

import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.LoginResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.data.model.TreatResponse
import kotlinx.coroutines.flow.Flow

interface IUserAuthRemoteDS {
    suspend fun login(phone: String): Flow<NetworkResource<LoginResponse>>
    suspend fun logout(): Flow<NetworkResource<TreatResponse>>
    suspend fun getUserProfile(): Flow<NetworkResource<ProfileResponse>>
    suspend fun verifyAccount(phone: String, otp: String): Flow<NetworkResource<ProfileResponse>>
    suspend fun resendVerifyingOtp(phone: String): Flow<NetworkResource<TreatResponse>>
    suspend fun updateLocation(
        latitude: Double,
        longitude: Double
    ): Flow<NetworkResource<ProfileResponse>>

    suspend fun disableAccount(): Flow<NetworkResource<TreatResponse>>
    suspend fun getGender(): Flow<NetworkResource<GenderResponse>>
    suspend fun updateUserProfile(
        name: String,
        gender: String,
        email: String?,
        birthDate: String?
    ): Flow<NetworkResource<ProfileResponse>>


}