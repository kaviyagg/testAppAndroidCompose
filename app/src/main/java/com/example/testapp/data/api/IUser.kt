package com.example.testapp.data.api



import com.example.testapp.Model.api.LoginRequest
import com.example.testapp.Model.common.User
import retrofit2.http.Body
import retrofit2.http.POST


interface IUserAPI {
    @POST("v3/account/login")
    suspend fun login(@Body loginRequest: LoginRequest): User

}