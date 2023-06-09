package com.example.testapp.Model.Interfaces

import com.example.testapp.Model.api.LoginRequest
import com.example.testapp.Model.common.User
import retrofit2.Response

interface IUserRepository {
    suspend fun login(loginRequest: LoginRequest): User

}