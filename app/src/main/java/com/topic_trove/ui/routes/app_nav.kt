package com.topic_trove.ui.routes


import PostCard
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.topic_trove.data.model.Post
import com.topic_trove.ui.global_widgets.CommunityTitle
import com.topic_trove.ui.modules.chatscreen.screen.ChatScreen
import com.topic_trove.ui.modules.communityscreen.screens.CommunityScreen
import com.topic_trove.ui.modules.communityscreen.screens.createpostScreen


@Composable
fun NavControll(navController: NavHostController){
    NavHost(navController = navController, startDestination = AppRoutes.communityRoute){
        composable(route = AppRoutes.homeRoute){
            //Sample
            ChatScreen()
        }

        composable(route = AppRoutes.createPostRoute){
            createpostScreen(navController = navController)
        }

        composable(route = AppRoutes.communityRoute){
            CommunityScreen(
                navController = navController
            )
        }
    }
}