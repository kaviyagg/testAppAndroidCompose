package com.example.testapp.pages.Login

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.testapp.config.AppLang
import com.example.testapp.sharedComponent.component.CenteredTitleBottomLayout
import com.example.testapp.sharedComponent.component.TextIconButton

@Composable
fun Login(navigator: NavController) {}
@Composable
fun LoginPage(email: String? = null) {
    val lang = AppLang.LoginPage
    val viewModel: LoginViewModel = hiltViewModel()
    val loginUiState by viewModel.loginPageState.collectAsState()
    val loginForm by loginUiState.loginForm.form.collectAsState()
    val focusManager = LocalFocusManager.current
    if(email != null){
        viewModel.setLoginEmailValue(email)
    }

    CenteredTitleBottomLayout(
        title = AppLang.Login,
        backAction = {
            TextIconButton(AppLang.Back, Icons.Filled.ArrowBack, onClick = {
                viewModel.navigateBack()
                focusManager.clearFocus()
            })
        },
        nextAction = {
            TextIconButton(AppLang.Login, trailingIcon = Icons.Filled.ArrowForward,
                enabled = loginForm.isFormValid, onClick = {
                    viewModel.login()
                    focusManager.clearFocus()
                })
        },
    ) {
        LoginForm
    }
}