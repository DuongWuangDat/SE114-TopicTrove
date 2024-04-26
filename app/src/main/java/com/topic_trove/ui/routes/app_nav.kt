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
            createpostScreen(onBack = {
                navController.popBackStack()
            })
        }

        composable(route = AppRoutes.communityRoute){
            //CommunityScreen(
            //    onNavigateToCreatePost = {
            //        navController.navigate(AppRoutes.createPostRoute)
            //    }
            //)
            PostCard(data = Post(communityName = "T1Bo", authorName = "Name", title = "T1",
                content = "1qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 111111111qqqqqqqqqqqqqqqqqqqqqqq 11111111", imageUrl = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2F1000002488.jpg?alt=media&token=f47b647a-c17c-404f-b42d-120b34c14e39"), isPostOwner = true, isCommunityOwner = true)
        }
    }
}