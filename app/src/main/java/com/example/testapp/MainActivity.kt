package com.example.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.testapp.sharedComponent.viewmodel.AuthViewModel
import com.example.testapp.ui.theme.TestAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                        AppNavGraph(navController = navController)
                    AuthState(navController)

                }
        }
    }

    @Composable
    fun AuthState(navController: NavController) {
        val viewModel: AuthViewModel = hiltViewModel()
        val isUserLoggedIn = viewModel.isUserLoggedIn.collectAsState().value

        LaunchedEffect(isUserLoggedIn) {
            if (isUserLoggedIn != null) {
                navController.navigate(Routes.PROFILE) {
                    popUpTo(0) // Clear the back stack
                }
            }
        }

        // Rest of your code for the AuthState composable
    }



}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}