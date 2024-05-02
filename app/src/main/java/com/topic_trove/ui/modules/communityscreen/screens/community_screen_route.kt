package com.topic_trove.ui.modules.communityscreen.screens

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
import com.topic_trove.ui.modules.communityscreen.CommunityScreenVM

@Composable
fun CommunityScreenRoute(
    communityVM: CommunityScreenVM = viewModel<CommunityScreenVM>(),
    navController: NavController,
    idUser: String = "661decfc04225e07fbc92f80",
    communityId: String
){
    LaunchedEffect(key1 = true) {
        communityVM.getCommunityByID(communityId,navController)
        communityVM.getPostList(communityId, idUser, navController)
        communityVM.CheckIsJoined(communityId,navController, idUser)

    }
    val community by communityVM.community.collectAsStateWithLifecycle()

    val isAuthor = community.owner == idUser
     CommunityScreen(communityId = communityId,
         navController = navController,
         community = community,
         idUser = idUser,
         communityVM = communityVM,
         isAuthor = isAuthor)


}