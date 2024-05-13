package com.topic_trove.ui.routes


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.topic_trove.data.model.User
import com.topic_trove.ui.modules.addcommentscreen.AddCommentRoute
import com.topic_trove.ui.modules.chatscreen.screen.ChatScreen
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreenRoute
import com.topic_trove.ui.modules.communityscreen.screens.createpostScreen
import com.topic_trove.ui.modules.profilescreen.screen.ProfileScreen
import com.topic_trove.ui.modules.profilescreen.screen.EditProfile
import com.topic_trove.ui.modules.confirmemailscreen.ConfirmEmailRoute
import com.topic_trove.ui.modules.homescreen.screen.CreateCommunityScreen
import com.topic_trove.ui.modules.homescreen.screen.HomeForeLoad
import com.topic_trove.ui.modules.loginscreen.LoginRoute
import com.topic_trove.ui.modules.loginscreen.WelcomeScreen
import com.topic_trove.ui.modules.postdetailscreen.PostDetailRoute
import com.topic_trove.ui.modules.registerscreen.RegisterRoute
import com.topic_trove.ui.modules.replyscreen.ReplyCommentRoute
import com.topic_trove.ui.modules.splashscreen.SplashRoute


@Composable
fun NavControl(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppRoutes.splash) {
        composable(route = AppRoutes.homeRoute) {
            //Sample
            HomeForeLoad(navController = navController)
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
            route = "${AppRoutes.communityRoute}/{communityId}",
            arguments = listOf(
                navArgument("communityId") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val id = entry.arguments?.getString("communityId")
            requireNotNull(id)
            CommunityScreenRoute(
                navController = navController,
                communityId = id
            )
        }

        composable(route = AppRoutes.profileRoute) {
            ProfileScreen(
                user = User(
                    "662cc6b65b4d055e982936ce",
                    "Eren Yeager",
                    "quoctruong@mail.com",
                    "0987654321",
                    "https://www.google.com/url?sa=i&url=https%3A%2F%2Fbuffer.com%2Flibrary%2Ffree-images%2F&psig=AOvVaw3ahVGsupVozBkj6Vj13Mhz&ust=1714487449663000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCLi6ns3R54UDFQAAAAAdAAAAABAE"
                ),
                navController = navController
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


        //ĐQP
        composable(route=AppRoutes.createCommunity) {
            CreateCommunityScreen(navController = navController)
        }
        //ĐQP
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

        composable(route = AppRoutes.splash) {
            SplashRoute(
                onLogin = { navController.navigate(AppRoutes.welcome) },
                onCommunity = { navController.navigate(AppRoutes.homeRoute) }
            )

        }

        composable(route = AppRoutes.editProfileRoute) {

            EditProfile(

                navController = navController
            )
        }
    }
}