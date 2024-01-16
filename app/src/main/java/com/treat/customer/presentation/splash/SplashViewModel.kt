package com.treat.customer.presentation.splash

import com.treat.customer.base.BaseViewModel
import com.treat.customer.domain.usecases.auth.GetLoginStatusUseCase

class SplashViewModel(private val loginStatus: GetLoginStatusUseCase) : BaseViewModel() {
    fun isLoggedIn(): Boolean {
            return  loginStatus.isLoggedIn()

    }
}