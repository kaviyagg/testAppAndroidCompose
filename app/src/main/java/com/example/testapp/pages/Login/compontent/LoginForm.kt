package com.example.testapp.pages.Login.compontent

import android.text.InputType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.config.AppLang
import com.example.testapp.pages.Login.LoginViewModel
import com.example.testapp.sharedComponent.component.Input
import com.example.testapp.ui.theme.SectionPadding

@Composable
fun LoginForm() {
    val lang = AppLang.LoginPage
    val viewModel: LoginViewModel = hiltViewModel()
    val loginUiState by viewModel.loginPageState.collectAsState()
    val loginForm by loginUiState.loginForm.form.collectAsState()
    val forgotForm by loginUiState.forgotPasswordForm.form.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = SectionPadding, horizontalAlignment = Alignment.Start
    ) {
        Input(
            type = com.example.testapp.sharedComponent.component.InputType.EMAIL,
            name = com.example.testapp.pages.Login.LoginForm.Email,
            label = AppLang.Email,
            form = loginForm,
            imeAction = ImeAction.Next
        )
        Spacer(modifier = Modifier.height(height = 20.dp))
        Input(
            type = com.example.testapp.sharedComponent.component.InputType.PASSWORD,
            name = com.example.testapp.pages.Login.LoginForm.Password,
            label ="Password",
            form = loginForm,
            imeAction = ImeAction.Done,
            onDone = {
                if(loginForm.isFormValid){
                    viewModel.login()
                }
            }
        )



    }
}
