package com.topic_trove.ui.routes


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.topic_trove.ui.modules.chatscreen.screen.ChatScreen
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreen
import com.topic_trove.ui.modules.communityscreen.screens.createpostScreen
import com.topic_trove.ui.modules.confirmemailscreen.ConfirmEmailRoute
import com.topic_trove.ui.modules.registerscreen.RegisterRoute


@Composable
fun NavControl(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppRoutes.registerRoute) {
        composable(route = AppRoutes.homeRoute) {
            //Sample
            ChatScreen()
        }

        composable(route = "${AppRoutes.createPostRoute}/{communityName}",
            arguments = listOf(
                navArgument("communityName") {
                    type = NavType.StringType
                }
            )) { entry ->
            val name = entry.arguments?.getString("communityName")
            requireNotNull(name)
            createpostScreen(navController = navController, communityName = name)
        }

        composable(
            route = AppRoutes.communityRoute
        ) {
            CommunityScreen(
                navController = navController
            )
        }

        composable(route = AppRoutes.registerRoute) {
            RegisterRoute(
                onSignUpSubmitted = {
                    navController.navigate(AppRoutes.confirmEmailRoute)
                },
            )
        }

        composable(route = AppRoutes.confirmEmailRoute) {
            ConfirmEmailRoute(
                onSubmitted = {
                    navController.navigate(AppRoutes.communityRoute)
                },
                onNavUp = navController::navigateUp,
            )
        }
    }
}