package com.example.testapp.sharedComponent.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.Model.Interfaces.IUserRepository
import com.example.testapp.Model.api.APIResult
import com.example.testapp.Model.api.LoginRequest
import com.example.testapp.Model.common.User
import com.example.testapp.data.UserRepository.UserRepository
import com.example.testapp.data.dataStore.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val Data: Boolean = false,
    val email: String = "",
    val password: String = ""
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val userPreference: UserPreference,
) : ViewModel() {

    private val tag = "AuthViewModel"
    var isUserFetched = userPreference.isUserFetched
    var isUserLoggedIn = userPreference.isUserLoggedIn
    private val _loginResult = MutableStateFlow<APIResult<User>>(APIResult.Loading)
    private val _userName = MutableStateFlow(LoginUiState())

    val userName: StateFlow<LoginUiState> = _userName.asStateFlow()
    val loginResult: StateFlow<APIResult<User>> = _loginResult.asStateFlow()

    init {
        viewModelScope.launch {
            val user = userPreference.getUser()
        }
    }

    suspend fun getUserToken(): String? {
        return userPreference.getUserToken()
    }

    fun login(loginRequest: LoginRequest) {
        _userName.update {
            it.copy(isLoading = true)
        }
        _userName.value = _userName.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val res = userRepository.login(loginRequest)
                _loginResult.value = APIResult.Success(res)
                _userName.update {
                    it.copy(isAuthenticated = true)
                }
                saveUser(res)
            } catch (exception: Exception) {
                _loginResult.value = APIResult.Failure(exception)
            } finally {
                _userName.value = _userName.value.copy(isLoading = false)
            }
        }
        Log.e("Email", userName.value.email)
        Log.e("Password", userName.value.password)
    }



    fun logoutUser() {
        viewModelScope.launch {
            userPreference.clearUser()
        }
    }

    private fun saveUser(user: User) {
        viewModelScope.launch {
            userPreference.saveUser(user)
        }
    }

}