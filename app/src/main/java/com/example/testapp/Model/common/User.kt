package com.example.testapp.Model.common

import com.example.testapp.Model.api.Account

data class User(
    val accessToken: String,
    val account: Account,
    val expiresAt: String,
    val refreshToken: String
)
