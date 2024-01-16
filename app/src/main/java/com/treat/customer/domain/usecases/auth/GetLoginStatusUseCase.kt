package com.treat.customer.domain.usecases.auth

import com.treat.customer.domain.repository.IUserAuthRepository

class GetLoginStatusUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    fun isLoggedIn(): Boolean {
        return iUserAuthRepository.isLoggedIn()
    }
}