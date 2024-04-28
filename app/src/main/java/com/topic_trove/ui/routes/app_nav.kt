package com.topic_trove.ui.routes


import PostCard
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.topic_trove.data.model.Post
import com.topic_trove.ui.global_widgets.CommunityTitle
import com.topic_trove.ui.modules.chatscreen.screen.ChatScreen
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreen
import com.topic_trove.ui.modules.communityscreen.screens.createpostScreen
import com.topic_trove.ui.modules.loginscreen.screens.LoginScreen


@Composable
fun NavControll(navController: NavHostController){
    NavHost(navController = navController, startDestination = AppRoutes.communityRoute){
        composable(route = AppRoutes.homeRoute){
            //Sample
            ChatScreen()
        }

        composable(route = "${AppRoutes.createPostRoute}/{communityName}",
            arguments = listOf(
                navArgument("communityName"){
                    type= NavType.StringType
                }
            )){entry->
            val name = entry.arguments?.getString("communityName")
            requireNotNull(name)
            createpostScreen(navController = navController, communityName = name)
        }

        composable(route = AppRoutes.communityRoute
            ){
            CommunityScreen(
                navController = navController
            )
        }

        composable(route = AppRoutes.loginRoute){
            LoginScreen()
        }
    }
}