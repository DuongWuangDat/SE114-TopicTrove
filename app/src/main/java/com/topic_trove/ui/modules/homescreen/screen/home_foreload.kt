package com.topic_trove.ui.modules.homescreen.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.topic_trove.ui.modules.homescreen.HomeScreenViewModel

@Composable
fun HomeForeLoad(
    navController: NavController,
){
    // Lấy ViewModel đã được inject
    val homeVM: HomeScreenViewModel = hiltViewModel()

    // Lấy User



    LaunchedEffect(key1 = true) {
       // homeVM.getPostList(communityId, user.id, navController)
        homeVM.getUserById(navController)
        homeVM.IdUser?.let { homeVM.getAllJoinedCommunity(navController) }
        homeVM.IdUser?.let { homeVM.getPostList(it,navController) }
    }
    val community by homeVM.community.collectAsStateWithLifecycle()
    Home_Screen(
        navController = navController,
        community = community,
        user = homeVM.userData,
        homeScreenVM = homeVM
    )


}