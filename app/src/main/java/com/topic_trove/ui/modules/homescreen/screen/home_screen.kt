package com.topic_trove.ui.modules.homescreen.screen
import com.topic_trove.ui.global_widgets.PostCard
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home_Screen(
    community: Community,
    user: StateFlow<User>,
    navController: NavController,
    homeScreenVM: HomeScreenViewModel
) {
    val snackbarHostState = homeScreenVM.snackbarHostState
    val isJoin by homeScreenVM.isJoined
    val user by user.collectAsState()
    val isExpanded = remember { mutableStateOf(false) }
    println(isJoin)
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
            TopbarHome(
                navController,
                user
            ) { isExpanded.value = !isExpanded.value }
            CommunityTab(
                isVisible = isExpanded.value,
                communities = homeScreenVM.communityList,
                navController = navController
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(homeScreenVM.postList) { post ->
                    val isCommunityOwner =
                        if (community.id != "") (community.owner == user.id) else false
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

        }
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

