package com.treat.customer.domain.usecases.auth

import com.treat.customer.data.model.GenderData
import com.treat.customer.domain.repository.IUserAuthRepository

class SaveGenderUseCase (private val iUserAuthRepository: IUserAuthRepository) {
     fun saveGender(genderData: GenderData) =
        iUserAuthRepository.saveGender(genderData)
}