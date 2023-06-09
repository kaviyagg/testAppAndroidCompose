package com.example.testapp.Model.api

//data class LoginRequest(
//    val accessToken: String, val account: Account, val expiresAt: String, val refreshToken: String
//)
data class LoginRequest(
    val email: String,
    val password: String
)

