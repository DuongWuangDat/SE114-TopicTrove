package com.topic_trove.ui.modules.homescreen.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.topic_trove.data.model.User
import com.topic_trove.data.sharepref.SharePreferenceProvider
import com.topic_trove.ui.modules.homescreen.HomeScreenViewModel

@Composable
fun HomeForeLoad(
    communityVM: HomeScreenViewModel = viewModel<HomeScreenViewModel>(),
    navController: NavController,
    user: User,
    communityId: String
){
    val sharePreferenceProvider: SharePreferenceProvider
    LaunchedEffect(key1 = true) {
        communityVM.getPostList(communityId, user.id, navController)
    }
    val community by communityVM.community.collectAsStateWithLifecycle()
    val isAuthor = community.owner == user.id
//    Home_Screen(
//        navController = navController,
//        community = community,
//        user = ,
//        homeScreenVM = communityVM
//    )


}