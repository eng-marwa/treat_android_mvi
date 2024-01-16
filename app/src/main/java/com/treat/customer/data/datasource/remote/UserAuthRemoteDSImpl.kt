package com.treat.customer.data.datasource.remote

import com.treat.customer.data.datasource.interfaces.IUserAuthRemoteDS
import com.treat.customer.data.datasource.remote.api.ApiService
import com.treat.customer.base.BaseApiProvider
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.LoginResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.data.model.TreatResponse
import kotlinx.coroutines.flow.Flow

class UserAuthRemoteDSImpl(private val apiService: ApiService) : IUserAuthRemoteDS,
    BaseApiProvider() {
    override suspend fun login(phone: String): Flow<NetworkResource<LoginResponse>> {
        return apiRequest { apiService.login(phone) }
    }

    override suspend fun logout(): Flow<NetworkResource<TreatResponse>> {
        return apiRequest { apiService.logout() }
    }

    override suspend fun getUserProfile(): Flow<NetworkResource<ProfileResponse>> {
        return apiRequest { apiService.getProfile() }
    }


    override suspend fun verifyAccount(
        phone: String,
        otp: String
    ): Flow<NetworkResource<ProfileResponse>> {
        return apiRequest { apiService.verifyAccount(phone, otp) }
    }

    override suspend fun resendVerifyingOtp(phone: String): Flow<NetworkResource<TreatResponse>> {
        return apiRequest { apiService.resendVerifyingOtp(phone) }

    }

    override suspend fun updateLocation(
        latitude: Double,
        longitude: Double
    ): Flow<NetworkResource<ProfileResponse>> {
        return apiRequest { apiService.updateLocation(latitude, longitude) }

    }

    override suspend fun disableAccount(): Flow<NetworkResource<TreatResponse>> {
        return apiRequest { apiService.disableAccount() }

    }

    override suspend fun getGender(): Flow<NetworkResource<GenderResponse>> {
        return apiRequest { apiService.getGenderType() }
    }

    override suspend fun updateUserProfile(
        name: String,
        gender: String, email: String?, birthDate: String?
    ): Flow<NetworkResource<ProfileResponse>> {
        return apiRequest { apiService.updateProfile(name, gender, email, birthDate) }
    }


}
