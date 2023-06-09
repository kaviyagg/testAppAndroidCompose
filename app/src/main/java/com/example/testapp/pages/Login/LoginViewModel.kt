package com.example.testapp.pages.Login

import android.os.Build
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.Model.Interfaces.IAppNavigator
import com.example.testapp.Model.Interfaces.IUserRepository
import com.example.testapp.Model.api.APIResult
import com.example.testapp.Model.api.LoginRequest
import com.example.testapp.Model.common.User
import com.example.testapp.config.AppLang
import com.example.testapp.data.dataStore.UserPreference
import com.example.testapp.util.FormBuilder
import com.example.testapp.util.FormField
import com.example.testapp.util.FormValidations
import com.example.testapp.util.ValidationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.TimeZone
import javax.inject.Inject

object LoginForm {
    const val Email = "email"
    const val Password = "password"
}

data class LoginPageState(
    val loginForm: FormBuilder = FormBuilder(
        formFields = mapOf(
            LoginForm.Email to FormField(
                "", validations = listOf(
                    FormValidations.required(), FormValidations.email()
                ), messages = mapOf(
                    ValidationType.REQUIRED to AppLang.BlankField,
                    ValidationType.EMAIL to AppLang.ValidEmail,
                )
            ), LoginForm.Password to FormField(
                "", validations = listOf(
                    FormValidations.required()
                ), messages = mapOf(
//                    ValidationType.REQUIRED to AppLang.BlankField
                )
            )
        )
    ),

    val forgotPasswordForm: FormBuilder = FormBuilder(
        formFields = mapOf(
            LoginForm.Email to FormField("")
        )
    ),
)


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val userPreference: UserPreference,
    private val appNavigator: IAppNavigator,
) : ViewModel() {
    private val tag = "LoginViewModel"
    val lang = AppLang
    private val _loginResult = MutableStateFlow<APIResult<User>>(APIResult.Loading)
    private val _loginPageState = MutableStateFlow(LoginPageState())


    val loginPageState: StateFlow<LoginPageState> = _loginPageState.asStateFlow()




    fun navigateBack() {
        appNavigator.tryNavigateBack()
    }

    fun navigateTo(path: String) {
        appNavigator.tryNavigateTo(path)
    }


    fun setLoginEmailValue(email: String) {
        viewModelScope.launch {
            val emailValue = _loginPageState.value
            emailValue.loginForm.getForm().patch(
                mapOf(
                    LoginForm.Email to email
                )
            )
        }
    }

    fun login() {
        val form = _loginPageState.value.loginForm.getAllValues()


        viewModelScope.launch {

            try {
                val formValue = _loginPageState.value.loginForm.getAllValues()
                val loginRequest = LoginRequest(
                    formValue.getValue(LoginForm.Email) as String,
                    formValue.getValue(LoginForm.Password) as String,
                )
                val res = userRepository.login(loginRequest)
                _loginResult.value = APIResult.Success(res)
            } catch (exception: HttpException) {
                when (exception.code()) {

                }
                _loginResult.value = APIResult.Failure(exception)
            } catch (exception: Exception) {
                _loginResult.value = APIResult.Failure(exception)
            } finally {

            }
        }
    }




    fun submitEmail() {

        val formValue = _loginPageState.value.forgotPasswordForm.getAllValues()
        val email = formValue.getValue(LoginForm.Email) as String

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            return
        }

        viewModelScope.launch {
        }
    }

}