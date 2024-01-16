package com.treat.customer.domain.usecases.auth

import com.treat.customer.data.model.TreatResponse
import com.treat.customer.domain.repository.IUserAuthRepository

class LogoutUseCase (private val iUserAuthRepository: IUserAuthRepository) {
    fun logout() = iUserAuthRepository.logout()
}