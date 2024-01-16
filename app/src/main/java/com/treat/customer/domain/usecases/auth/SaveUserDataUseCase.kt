package com.treat.customer.domain.usecases.auth

import android.util.Log
import com.treat.customer.data.model.ProfileResponse
import com.treat.customer.domain.repository.IUserAuthRepository

class SaveUserDataUseCase(
    private val iUserAuthRepository: IUserAuthRepository,
){
   fun saveUserData(data: ProfileResponse?) {
       Log.d("TAG3", "saveUserData:$data ")
     return  iUserAuthRepository.saveUserData(data)
   }
}
