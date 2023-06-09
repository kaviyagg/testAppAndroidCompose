package com.example.testapp.data.UserRepository

import com.example.testapp.Model.api.LoginRequest
import com.example.testapp.Model.common.User
import com.example.testapp.data.api.IUserAPI
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userAPI: IUserAPI
) {

    suspend fun login(loginRequest: LoginRequest): User {
        return  userAPI.login(loginRequest)
    }

}