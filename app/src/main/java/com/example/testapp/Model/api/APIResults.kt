package com.example.testapp.Model.api

sealed class APIResult<out T> {
    data class Success<T>(val data: T) : APIResult<T>()
    data class Failure(val exception: Exception) : APIResult<Nothing>()
    object Loading : APIResult<Nothing>()
}
