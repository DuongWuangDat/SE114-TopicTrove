package com.topic_trove.ui.routes


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.topic_trove.ui.modules.addcommentscreen.AddCommentRoute
import com.topic_trove.ui.modules.chatscreen.screen.ChatScreen
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreen
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreenRoute
import com.topic_trove.ui.modules.communityscreen.screens.createpostScreen
import com.topic_trove.ui.modules.confirmemailscreen.ConfirmEmailRoute
import com.topic_trove.ui.modules.homescreen.screen.CreateCommunityScreen
import com.topic_trove.ui.modules.loginscreen.screens.LoginScreen
import com.topic_trove.ui.modules.postdetailscreen.PostDetailRoute
import com.topic_trove.ui.modules.registerscreen.RegisterRoute
import com.topic_trove.ui.modules.replyscreen.ReplyCommentRoute


@Composable
fun NavControl(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "${AppRoutes.communityRoute}") {
        composable(route = AppRoutes.homeRoute) {
            //Sample
            ChatScreen()
        }

        composable(route = "${AppRoutes.createPostRoute}/{communityName}/{communityId}",
            arguments = listOf(
                navArgument("communityName") {
                    type = NavType.StringType
                },
                navArgument("communityId"){
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
            CommunityScreenRoute(navController = navController, communityId = "662385ad314b50e0397a3a90")
        }

        composable(route = AppRoutes.loginRoute) {
            LoginScreen()
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
                    // TODO navigation to login
                    navController.navigate(AppRoutes.communityRoute)
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

        //ĐQP
        composable(route=AppRoutes.createCommunity){
            CreateCommunityScreen(navController = navController)
        }
    }
}