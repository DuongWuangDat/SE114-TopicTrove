package com.topic_trove.ui.modules.homescreen.screen
import com.topic_trove.ui.global_widgets.PostCard
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.topic_trove.data.model.Community
import com.topic_trove.data.model.User
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.CustomDialog
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.modules.homescreen.HomeScreenViewModel
import com.topic_trove.ui.modules.homescreen.widgets.CommunityTab
import com.topic_trove.ui.modules.homescreen.widgets.TopbarHome
import com.topic_trove.ui.routes.AppRoutes
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home_Screen(
    user: StateFlow<User>,
    navController: NavController,
    homeScreenVM: HomeScreenViewModel
) {
    val snackbarHostState = homeScreenVM.snackbarHostState
    val communityList = homeScreenVM.communityList
    val ownedCommunity = homeScreenVM.ownedCommunity
    val isJoin by homeScreenVM.isJoined
    val user by user.collectAsState()
    val isExpanded = remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {

                Box(modifier = Modifier.zIndex(1f)){
                    Spacer(modifier = Modifier.height(8.dp))
                    TopbarHome(
                        navController,
                        user
                    ) { isExpanded.value = !isExpanded.value }

                    Spacer(modifier = Modifier.height(10.dp))
                }
                Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)){
                    LazyColumn (modifier = Modifier.fillMaxSize()){
                        items(homeScreenVM.postList) { post ->
                            val isCommunityOwner =
                                if (post.communityID != "" ) (ownedCommunity.contains(post.communityID)) else false
                            val isPostOwner = (post.authorID == user.id)

                            Divider(thickness = 0.3.dp, color = AppColors.DividerColor)
                            PostCard(
                                data = post,
                                isPostOwner = isPostOwner,
                                isCommunityOwner = isCommunityOwner,
                                onLike = {
                                    homeScreenVM.likePost(
                                        post.id,
                                        user.id,
                                        isLike = post.isLike,
                                        navController
                                    )
                                },
                                onDelete = {
                                    //homescreenVM.deletePost(post.id)
                                    homeScreenVM.isShowDialog.value = true
                                    homeScreenVM.curPostId.value = post.id
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
                                homeScreenVM.isRefreshing.value=true
                                homeScreenVM.getPostList(user.id, navController)
                            }

                        }
                    }

                    LaunchedEffect(key1 = homeScreenVM.isRefreshing.value) {
                        if(homeScreenVM.isRefreshing.value){
                            pullToRefreshState.startRefresh()
                        }else{
                            pullToRefreshState.endRefresh()
                        }
                    }

                    PullToRefreshContainer(state = pullToRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter))
                }

            }
            if (isExpanded.value) {
                Surface(color = Color.Black.copy(alpha = 0.5f)) {
                    Modifier.clickable { isExpanded.value = false }
                }
                CommunityTab(
                    isVisible = isExpanded.value,
                    communities = homeScreenVM.communityList,
                    navController = navController
                )
                if (homeScreenVM.isLoading.value) {
                    OverlayLoading()
                }
                if (homeScreenVM.isShowDialog.value) {
                    CustomDialog(
                        onDismiss = {
                            homeScreenVM.isShowDialog.value = false
                        },
                        onConfirm = {
                            homeScreenVM.deletePost(homeScreenVM.curPostId.value, navController)
                            homeScreenVM.isShowDialog.value = false
                        },
                        title = "Delete this post",
                        text = "Are you sure to delete this post"
                    )
                }
            }

        }
    }
}

