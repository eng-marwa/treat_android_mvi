package com.treat.customer.domain.repository

import android.util.Log
import com.treat.customer.data.datasource.interfaces.IUserAuthRemoteDS
import com.treat.customer.data.datasource.local.prefs.PreferenceHelper
import com.treat.customer.data.datasource.remote.api.NetworkResource
import com.treat.customer.data.model.FQAResponse
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.LoginResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.data.model.TreatResponse
import kotlinx.coroutines.flow.Flow

class UserAuthRepositoryImpl(
    private val iUserAuthRemoteDS: IUserAuthRemoteDS,
    private val preferenceHelper: PreferenceHelper
) : IUserAuthRepository {
    override suspend fun login(phone: String): Flow<NetworkResource<LoginResponse>> {
        return iUserAuthRemoteDS.login(phone)
    }

    override suspend fun logoutRemote(): Flow<NetworkResource<TreatResponse>> {
        return iUserAuthRemoteDS.logout()
    }

    override suspend fun getUserProfile(): Flow<NetworkResource<ProfileResponse>> {
        return iUserAuthRemoteDS.getUserProfile()
    }

    override suspend fun verifyAccount(
        phone: String,
        otp: String
    ): Flow<NetworkResource<ProfileResponse>> {
        return iUserAuthRemoteDS.verifyAccount(phone, otp)
    }

    override suspend fun resendVerifyingOtp(phone: String): Flow<NetworkResource<TreatResponse>> {
        return iUserAuthRemoteDS.resendVerifyingOtp(phone)
    }

    override suspend fun updateLocation(
        latitude: Double,
        longitude: Double
    ): Flow<NetworkResource<ProfileResponse>> {
        return iUserAuthRemoteDS.updateLocation(latitude, longitude)
    }

    override suspend fun disableAccount(): Flow<NetworkResource<TreatResponse>> {
        return iUserAuthRemoteDS.disableAccount()
    }

    override fun saveUserData(data: ProfileResponse?) {
        Log.d("TAG4", "saveUserData: ${data}")
        preferenceHelper.userData = data
        preferenceHelper.userToken = data?.data?.token
    }

    override fun getSavedUserData(): ProfileResponse? {
        return preferenceHelper.userData
    }

    override fun logout() {
        preferenceHelper.loggedIn = false
        preferenceHelper.userData = null
    }

    override fun setLoginStatus(b: Boolean) {
        preferenceHelper.loggedIn = b
    }

    override fun isLoggedIn(): Boolean {
        return preferenceHelper.loggedIn
    }

    override suspend fun getGender(): Flow<NetworkResource<GenderResponse>> {
        return iUserAuthRemoteDS.getGender()
    }

    override suspend fun updateUserProfile(
        name: String,
        gender: String, email: String?, birthDate: String?
    ): Flow<NetworkResource<ProfileResponse>> {
        return iUserAuthRemoteDS.updateUserProfile(name, gender, email, birthDate)
    }

    override fun saveGender(gender: GenderData) {
        preferenceHelper.gender = gender
    }

    override fun getSavedGender(): GenderData? {
        return preferenceHelper.gender
    }


}