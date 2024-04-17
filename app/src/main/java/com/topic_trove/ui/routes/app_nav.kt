package com.topic_trove.ui.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.topic_trove.ui.modules.chatscreen.screen.ChatScreen


@Composable
fun NavControll(navController: NavHostController){
    NavHost(navController = navController, startDestination = AppRoutes.homeRoute){
        composable(route = AppRoutes.homeRoute){
            //Sample
            ChatScreen()
        }
    }
}