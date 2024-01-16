package com.treat.customer.domain.usecases.auth

import com.treat.customer.data.model.GenderData
import com.treat.customer.domain.repository.IUserAuthRepository

class GetSavedGenderUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    fun getSavedGender(): GenderData? =
        iUserAuthRepository.getSavedGender()
}