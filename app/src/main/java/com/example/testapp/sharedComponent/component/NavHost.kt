package com.example.testapp.sharedComponent.component

//import android.app.Activity
//import androidx.compose.animation.AnimatedVisibilityScope
//import androidx.compose.animation.ExperimentalAnimationApi
//import androidx.compose.animation.core.tween
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideInHorizontally
//import androidx.compose.animation.slideOutHorizontally
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.navigation.NamedNavArgument
//import androidx.navigation.NavBackStackEntry
//import androidx.navigation.NavDeepLink
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.receiveAsFlow
//
//@OptIn(ExperimentalAnimationApi::class)
//@Composable
//fun NavHost(
//    navController: NavHostController,
//    startDestination: Destination,
//    modifier: Modifier = Modifier,
//    route: String? = null,
//    builder: NavGraphBuilder.() -> Unit
//) {
//    AnimatedNavHost(
//        navController = navController,
//        startDestination = startDestination.fullRoute,
//        modifier = modifier,
//        route = route,
//        builder = builder
//    )
//}
//
//@OptIn(ExperimentalAnimationApi::class)
//fun NavGraphBuilder.composable(
//    destination: Destination,
//    arguments: List<NamedNavArgument> = emptyList(),
//    deepLinks: List<NavDeepLink> = emptyList(),
//    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
//) {
//    composable(
//        route = destination.fullRoute,
//        arguments = arguments,
//        deepLinks = deepLinks,
//        content = content,
//        enterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { fullWidth -> 50 },
//                animationSpec = tween(durationMillis = 300)
//            ) + fadeIn(animationSpec = tween(durationMillis = 300))
//
//        },
//        exitTransition = {
//            slideOutHorizontally(
//                targetOffsetX = { fullWidth -> 50 },
//                animationSpec = tween(durationMillis = 300)
//            ) + fadeOut(animationSpec = tween(durationMillis = 300))
//
//        },
//        popEnterTransition = {
//            slideInHorizontally(
//                initialOffsetX = { fullWidth -> -50 },
//                animationSpec = tween(durationMillis = 300)
//            ) + fadeIn(animationSpec = tween(durationMillis = 300))
//        },
//        popExitTransition = {
//            slideOutHorizontally(
//                targetOffsetX = { fullWidth -> -50 },
//                animationSpec = tween(durationMillis = 300)
//            ) + fadeOut(animationSpec = tween(durationMillis = 300))
//        }
//    )
//}
//
//
//@Composable
//fun NavigationEffects(
//    navigationChannel: Channel<NavigationIntent>, navHostController: NavHostController
//) {
//    val activity = (LocalContext.current as? Activity)
//    LaunchedEffect(activity, navHostController, navigationChannel) {
//        navigationChannel.receiveAsFlow().collect { intent ->
//            if (activity?.isFinishing == true) {
//                return@collect
//            }
//            when (intent) {
//                is NavigationIntent.NavigateBack -> {
//                    if (intent.route != null) {
//                        navHostController.popBackStack(intent.route, intent.inclusive)
//                    } else {
//                        navHostController.popBackStack()
//                    }
//                }
//
//                is NavigationIntent.NavigateTo -> {
//                    navHostController.navigate(intent.route) {
//                        launchSingleTop = intent.isSingleTop
//                        intent.popUpToRoute?.let { popUpToRoute ->
//                            if (popUpToRoute == "CLEAR") {
//                                popUpTo(0) { inclusive = intent.inclusive }
//                            } else {
//                                popUpTo(popUpToRoute) { inclusive = intent.inclusive }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}