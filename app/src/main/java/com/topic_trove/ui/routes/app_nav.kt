package com.topic_trove.ui.routes


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.topic_trove.MainViewModel
import com.topic_trove.data.provider.Provider
import com.topic_trove.ui.core.utils.AppEvent
import com.topic_trove.ui.modules.addcommentscreen.AddCommentRoute
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreenRoute
import com.topic_trove.ui.modules.communityscreen.screens.createpostScreen
import com.topic_trove.ui.modules.confirmemailscreen.ConfirmEmailRoute
import com.topic_trove.ui.modules.homescreen.screen.CreateCommunityScreen
import com.topic_trove.ui.modules.homescreen.screen.HomeForeLoad
import com.topic_trove.ui.modules.loginscreen.LoginRoute
import com.topic_trove.ui.modules.loginscreen.WelcomeScreen
import com.topic_trove.ui.modules.postdetailscreen.PostDetailRoute
import com.topic_trove.ui.modules.profilescreen.screen.EditProfile
import com.topic_trove.ui.modules.profilescreen.screen.ProfileScreen
import com.topic_trove.ui.modules.registerscreen.RegisterRoute
import com.topic_trove.ui.modules.replyscreen.ReplyCommentRoute
import com.topic_trove.ui.modules.searchscreen.SearchScreenRoute
import com.topic_trove.ui.modules.splashscreen.SplashRoute
import com.topic_trove.ui.modules.uploadavatar.UploadAvatarRoute


@Composable
fun NavControl(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val navController = Provider.LocalNavController.current
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            if (it == AppEvent.LogOut) {
                navController.navigate(AppRoutes.welcome)
            }
        }
    }
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
                navController = navController
            )
        }

        composable(route = AppRoutes.loginRoute) {
            LoginRoute(
                onNavUp = navController::navigateUp,
                onSubmitted = {
                    navController.navigate(AppRoutes.homeRoute)
                }
            )
        }

        composable(route = AppRoutes.registerRoute) {
            RegisterRoute(
                onSignUpSubmitted = {
                    navController.navigate(AppRoutes.uploadAvatar)
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
        composable(route = AppRoutes.createCommunity) {
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

        composable(route = AppRoutes.searchRoute) {
            SearchScreenRoute(navController = navController)
        }


        composable(route = AppRoutes.uploadAvatar) {
            UploadAvatarRoute(
                onSubmitted = {
                    navController.navigate(AppRoutes.confirmEmailRoute)
                },
                onNavUp = navController::navigateUp,
            )
        }
    }
}