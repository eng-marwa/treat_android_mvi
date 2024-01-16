package com.treat.customer.domain.usecases.auth

import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.repository.IUserAuthRepository

class GetSavedUserDataUseCase (private val iUserAuthRepository: IUserAuthRepository){
    fun getSavedUserData(): ProfileResponse? =
        iUserAuthRepository.getSavedUserData()
}