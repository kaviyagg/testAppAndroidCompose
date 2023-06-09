package com.example.testapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.ActivityNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testapp.config.AppLang.Login
import com.example.testapp.pages.History.History
import com.example.testapp.pages.Login.Login
import com.example.testapp.pages.Login.LoginPage
import com.example.testapp.pages.PairedDevice.PairedDevice
import com.example.testapp.pages.Profile.Profile
import com.example.testapp.pages.ScanDevices.ScanDevice


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routes.LOGIN
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(startDestination) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            LoginPage(email)
        }

        composable(Routes.PROFILE) {
            Profile(navController)
        }

        composable(Routes.HISTORY) {
            History(navController)
        }

        composable(Routes.SCANDEVICE) {
            ScanDevice(navController)
        }
        composable(Routes.PAIREDDEVICE) {
            PairedDevice(navController)
        }
    }
}