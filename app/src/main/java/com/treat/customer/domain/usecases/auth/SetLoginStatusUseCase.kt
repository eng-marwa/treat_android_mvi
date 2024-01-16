package com.treat.customer.domain.usecases.auth

import com.treat.customer.domain.repository.IUserAuthRepository

class SetLoginStatusUseCase(private val iUserAuthRepository: IUserAuthRepository) {
    fun setLoginStatus(b: Boolean) {
        return iUserAuthRepository.setLoginStatus(b)
    }
}