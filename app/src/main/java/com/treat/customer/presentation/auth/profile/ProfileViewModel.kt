package com.treat.customer.presentation.auth.profile

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treat.customer.base.BaseException
import com.treat.customer.base.BaseViewModel
import com.treat.customer.data.datasource.remote.api.NetworkStatus
import com.treat.customer.data.model.GenderData
import com.treat.customer.data.model.GenderResponse
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.usecases.auth.GetGenderUseCase
import com.treat.customer.domain.usecases.auth.GetProfileUseCase
import com.treat.customer.domain.usecases.auth.GetSavedGenderUseCase
import com.treat.customer.domain.usecases.auth.SaveGenderUseCase
import com.treat.customer.domain.usecases.auth.UpdateUserProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getGenderUseCase: GetGenderUseCase,
    private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    private val saveGenderUseCase: SaveGenderUseCase,
    private val getSavedGenderUseCase: GetSavedGenderUseCase,
) : ViewModel() {
    val gender =
        MutableStateFlow<BaseViewModel.ViewState<GenderResponse>>(BaseViewModel.ViewState.Empty())
    val profileStatus =
        MutableStateFlow<BaseViewModel.ViewState<ProfileResponse>>(BaseViewModel.ViewState.Empty())
    val fetchProfileStatus =
        MutableStateFlow<BaseViewModel.ViewState<ProfileResponse>>(BaseViewModel.ViewState.Empty())

    fun getGender() {
        viewModelScope.launch {
            getGenderUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                gender.value = state
            }
        }
    }

    fun updateUserProfileFromAuth(name: String, gender: String) {
        if (validateName(name) && validateGender(gender)) {
            viewModelScope.launch {
                updateUserProfileUseCase(name, gender, null, null).collect {
                    val state = when (it.status) {
                        is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                        is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                        else -> BaseViewModel.ViewState.Empty()
                    }
                    profileStatus.value = state
                }
            }

        } else if (!validateName(name)) {
            profileStatus.value =
                BaseViewModel.ViewState.Failed(BaseException(message = "INVALID_NAME_ERROR"))
        } else if (!validateGender(gender)) {
            profileStatus.value =
                BaseViewModel.ViewState.Failed(BaseException(message = "INVALID_GENDER_ERROR"))
        }
    }

    private fun validateName(name: String): Boolean {
        return name.trim().isNotEmpty()
    }

    private fun validateGender(gender: String): Boolean {
        return gender.trim().isNotEmpty()
    }

    fun updateUserProfileFromMore(name: String, gender: String, email: String, birthDate: String?) {
        if (validateName(name) && validateGender(gender) && validateEmail(email)) {
            viewModelScope.launch {
                updateUserProfileUseCase(name, gender, email, birthDate).collect {
                    val state = when (it.status) {
                        is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                        is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                        else -> BaseViewModel.ViewState.Empty()
                    }
                    profileStatus.value = state
                }
            }

        } else if (!validateName(name)) {
            profileStatus.value =
                BaseViewModel.ViewState.Failed(BaseException(message = "INVALID_NAME_ERROR"))
        } else if (!validateGender(gender)) {
            profileStatus.value =
                BaseViewModel.ViewState.Failed(BaseException(message = "INVALID_GENDER_ERROR"))
        } else if (!validateEmail(email)) {
            profileStatus.value =
                BaseViewModel.ViewState.Failed(BaseException(message = "INVALID_EMAIL_ERROR"))

        }
    }

    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect {
                val state = when (it.status) {
                    is NetworkStatus.Success -> BaseViewModel.ViewState.Loaded(data = it.payload?.data!!)
                    is NetworkStatus.Failure -> BaseViewModel.ViewState.Failed(it.error)
                    else -> BaseViewModel.ViewState.Empty()
                }
                fetchProfileStatus.value = state
            }
        }
    }

    fun saveGender(gender: GenderData) {
        saveGenderUseCase.saveGender(gender)
    }

    fun getSavedGender(): GenderData? {
        return getSavedGenderUseCase.getSavedGender()
    }
}