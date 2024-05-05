package com.topic_trove.ui.routes


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.topic_trove.ui.modules.addcommentscreen.AddCommentRoute
import com.topic_trove.ui.modules.chatscreen.screen.ChatScreen
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreenRoute
import com.topic_trove.ui.modules.communityscreen.screens.createpostScreen
import com.topic_trove.ui.modules.confirmemailscreen.ConfirmEmailRoute
import com.topic_trove.ui.modules.loginscreen.LoginRoute
import com.topic_trove.ui.modules.loginscreen.WelcomeScreen
import com.topic_trove.ui.modules.postdetailscreen.PostDetailRoute
import com.topic_trove.ui.modules.registerscreen.RegisterRoute
import com.topic_trove.ui.modules.replyscreen.ReplyCommentRoute


@Composable
fun NavControl(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppRoutes.welcome) {
        composable(route = AppRoutes.homeRoute) {
            //Sample
            ChatScreen()
        }

        composable(route = "${AppRoutes.createPostRoute}/{communityName}/{communityId}",
            arguments = listOf(
                navArgument("communityName") {
                    type = NavType.StringType
                },
                navArgument("communityId") {
                    type = NavType.StringType
                }
            )) { entry ->
            val name = entry.arguments?.getString("communityName")
            val id = entry.arguments?.getString("communityId")
            requireNotNull(name)
            requireNotNull(id)
            createpostScreen(navController = navController, communityName = name, communityId = id)
        }

        composable(
            route = "${AppRoutes.communityRoute}",
            //arguments = listOf(
            //    navArgument("communityId") {
            //        type = NavType.StringType
            //    }
            //)
        ) {
            //val id = entry.arguments?.getString("communityId")
            //requireNotNull(id)
            CommunityScreenRoute(
                navController = navController,
                communityId = "662385ad314b50e0397a3a90"
            )
        }

        composable(route = AppRoutes.loginRoute) {
            LoginRoute(
                onNavUp = navController::navigateUp,
                onSubmitted = {
                    navController.navigate(AppRoutes.communityRoute)
                }
            )
        }

        composable(route = AppRoutes.registerRoute) {
            RegisterRoute(
                onSignUpSubmitted = {
                    navController.navigate(AppRoutes.confirmEmailRoute)
                },
                onNavUp = navController::navigateUp,
            )
        }

        composable(route = AppRoutes.confirmEmailRoute) {
            ConfirmEmailRoute(
                onSubmitted = {
                    navController.navigate(AppRoutes.loginRoute)
                },
                onNavUp = navController::navigateUp,
            )
        }

        composable(route = "${AppRoutes.createCommentRoute}/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            AddCommentRoute(
                postId = postId ?: "",
                onNavUp = navController::navigateUp,
                onSuccess = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = "${AppRoutes.replyCommentRoute}/{postId}/{parentComment}/{comment}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            val parentComment = backStackEntry.arguments?.getString("parentComment")
            val comment = backStackEntry.arguments?.getString("comment")
            ReplyCommentRoute(
                postId = postId ?: "",
                parentComment = parentComment ?: "",
                onNavUp = navController::navigateUp,
                content = comment ?: "",
            )
        }

        composable(route = "${AppRoutes.postDetailRoute}/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            PostDetailRoute(
                postId = postId ?: "",
                onNavUp = navController::navigateUp,
                onNavAddComment = { navController.navigate("${AppRoutes.createCommentRoute}/$postId") },
                onReply = { content, parentComment ->
                    navController.navigate("${AppRoutes.replyCommentRoute}/$postId/$parentComment/$content")
                },
            )
        }

        composable(route = AppRoutes.welcome) {
            WelcomeScreen(
                login = {
                    navController.navigate(AppRoutes.loginRoute)
                },
                register = {
                    navController.navigate(AppRoutes.registerRoute)
                },
            )
        }
    }
}