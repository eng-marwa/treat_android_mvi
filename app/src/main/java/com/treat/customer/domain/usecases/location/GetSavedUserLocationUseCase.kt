package com.treat.customer.domain.usecases.location

import com.treat.customer.domain.repository.IUserAuthRepository

class GetSavedUserLocationUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    fun getSavedLocation():String?{
        return iUserAuthRepository.getSavedLocation()
    }
}