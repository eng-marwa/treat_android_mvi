package com.treat.customer.domain.usecases.location

import com.treat.customer.domain.repository.IUserAuthRepository

class SaveUserLocationUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    fun saveUserLocation(lat:Double,lon:Double){
        iUserAuthRepository.saveUserLocation(lat,lon)
    }
}