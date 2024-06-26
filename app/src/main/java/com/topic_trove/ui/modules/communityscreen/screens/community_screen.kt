package com.topic_trove.ui.modules.communityscreen.screens

import com.topic_trove.ui.global_widgets.PostCard
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.topic_trove.data.model.Community
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.CommunityTitle
import com.topic_trove.ui.global_widgets.CustomDialog
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.modules.communityscreen.CommunityScreenVM
import com.topic_trove.ui.modules.communityscreen.widgets.TopbarCommunity
import com.topic_trove.ui.routes.AppRoutes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityScreen(
    isAuthor: Boolean,
    community : Community,
    communityId : String,
    idUser: String = "661ded639a9ecc4c2525774d",
    navController: NavController,
    communityVM: CommunityScreenVM
) {
    val snackbarHostState = communityVM.snackbarHostState
    val isJoin by communityVM.isJoined
    var pullToRefreshState = rememberPullToRefreshState()
    var scope = rememberCoroutineScope()
    var lazyListState = rememberLazyListState()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopbarCommunity(
                isAuthor= isAuthor,
                isJoin = communityVM.isJoined.value,
                community = community,
                onNavigateToCreatePost = {
                navController.navigate("${AppRoutes.createPostRoute}/${community.communityName}/${community.id}")
            },
                onNavigateBack = {
                navController.popBackStack()
            })
            Box(modifier = Modifier.zIndex(1f)){
                CommunityTitle (community= community,
                    isAuthor = isAuthor,
                    isJoin = communityVM.isJoined
                ) {
                    communityVM.JoinCommunity(communityId,navController,idUser)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)){
                LazyColumn (modifier = Modifier.fillMaxSize(), state = lazyListState){
                    items(communityVM.postList) { post ->
                        val isCommunityOwner =
                            if (community.id != "") (community.owner == idUser) else false
                        val isPostOwner = (post.authorID == idUser)

                        Divider(color = AppColors.DividerColor, thickness = 0.3.dp)
                        PostCard(
                            data = post,
                            isPostOwner = isPostOwner,
                            isCommunityOwner = isCommunityOwner,
                            onLike = {
                                communityVM.likePost(
                                    post.id,
                                    idUser,
                                    isLike = post.isLike,
                                    navController
                                )
                            },
                            onDelete = {
                                //communityVM.deletePost(post.id)
                                communityVM.isShowDialog.value = true
                                communityVM.curPostId.value = post.id
                            },
                            onClickable = {
                                navController.navigate(
                                    "${AppRoutes.postDetailRoute}/${post.id}"
                                )
                            }
                        )
                    }
                }

                if(pullToRefreshState.isRefreshing){
                    LaunchedEffect(key1 = true) {
                        scope.launch {
                            communityVM.isRefreshing.value=true
                            communityVM.getPostList(communityId,idUser, navController)
                        }

                    }
                }
                LaunchedEffect(key1 = communityVM.isRefreshing.value) {
                    if(communityVM.isRefreshing.value){
                        pullToRefreshState.startRefresh()
                    }else{
                        pullToRefreshState.endRefresh()
                    }
                }
                PullToRefreshContainer(state = pullToRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )


            }


        }
        if (communityVM.isLoading.value) {
            OverlayLoading()
        }
        if (communityVM.isShowDialog.value) {
            CustomDialog(
                onDismiss = {
                    communityVM.isShowDialog.value = false
                },
                onConfirm = {
                    communityVM.deletePost(communityVM.curPostId.value, navController)
                    communityVM.isShowDialog.value = false
                },
                title = "Delete this post",
                text = "Are you sure to delete this post"
            )
        }

    }
}