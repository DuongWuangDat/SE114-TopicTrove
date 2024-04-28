package com.topic_trove.ui.modules.communityscreen.screens

import PostCard
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.topic_trove.data.model.Community
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.CommunityTitle
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.modules.communityscreen.CommunityScreenVM
import com.topic_trove.ui.modules.communityscreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.communityscreen.widgets.TopBarCreatePost
import com.topic_trove.ui.modules.communityscreen.widgets.TopbarCommunity
import com.topic_trove.ui.routes.AppRoutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityScreen(
    community: Community = Community(),
    idUser: String = "661ded639a9ecc4c2525774d",
    navController: NavController
){
    val communityVM = viewModel<CommunityScreenVM>()
    val snackbarHostState = communityVM.snackbarHostState
    LaunchedEffect(key1 = navController) {
        communityVM.getPostList("662385ad314b50e0397a3a90", idUser)
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopbarCommunity(community = community, onNavigateToCreatePost={
                navController.navigate("${AppRoutes.createPostRoute}/${community.communityName}")
            })
            CommunityTitle {

            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(communityVM.postList) { post ->
                    var isCommunityOwner = if(community.id!="") (community.id == idUser) else false
                    var isPostOwner = (post.authorID == idUser)

                    Divider(color = AppColors.DividerColor, thickness = 0.3.dp)
                    PostCard(data = post, isPostOwner = isPostOwner, isCommunityOwner = isCommunityOwner,
                        onLike = {
                            communityVM.likePost(post.id, idUser, isLike = post.isLike)
                        },
                        onDelete = {
                        communityVM.deletePost(post.id)
                    })
                }
            }

        }
        if(communityVM.isLoading.value){
            OverlayLoading()
        }
    }
}