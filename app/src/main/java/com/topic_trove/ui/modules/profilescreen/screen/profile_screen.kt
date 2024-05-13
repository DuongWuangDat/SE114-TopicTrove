package com.topic_trove.ui.modules.profilescreen.screen

import HeaderBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.topic_trove.R
import com.topic_trove.data.model.User
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.convertHex
import com.topic_trove.ui.global_widgets.CustomDialog
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.global_widgets.PostCard
import com.topic_trove.ui.modules.profilescreen.ProfileScreenVM
import com.topic_trove.ui.modules.profilescreen.widgets.AlertDialogCustom
import com.topic_trove.ui.routes.AppRoutes

@Composable
fun ProfileScreen(
    navController: NavController,
    profileVM: ProfileScreenVM = hiltViewModel()
) {
//    val user = profileVM.useSession!!
    val user = User(
        id = "6641d0ca43c8abf74b9b768c",
        username = "Eren Yeager",
        email = "eren@gmail.com",
        phoneNumber = "0987654321",
        avatar = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2Fcoding.jpg?alt=media&token=de22115f-4cab-487d-836e-78060d019ddc"
    )
    val openAlertDialog = remember { mutableStateOf(false) }
    val snackBarHostState = profileVM.snackBarHostState
    LaunchedEffect(key1 = true) {
        profileVM.getPosts(userId = user.id, navController = navController)
    }

    when {
        openAlertDialog.value -> {
            AlertDialogCustom(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Logout?",
                dialogText = "Are you sure you want to logout?",
            )
        }
    }

    Scaffold(topBar = {
        HeaderBar(onBackButtonPressed = { navController.popBackStack() })
    }, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }

    ) { innerPadding ->
        (Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                model = user.avatar,
                placeholder = painterResource(R.drawable.avatar_default),
                contentDescription = "Profile Picture",
            )
            Text(
                text = user.username,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
            Button(
                onClick = { navController.navigate(AppRoutes.editProfileRoute) },
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(convertHex("#0072DB")),
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black,
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                contentPadding = PaddingValues(35.dp, 8.dp),

                ) {
                Text(text = "Edit")
            }
            Button(
                onClick = {
                    openAlertDialog.value = true
                    profileVM.logout(navController)
                },
                colors = ButtonColors(
                    containerColor = Color(convertHex("#E90000")),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black,
                ),
            ) {
                Text(text = "Log out")
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(profileVM.posts) { post ->
                    HorizontalDivider(thickness = 0.3.dp, color = AppColors.DividerColor)
                    PostCard(data = post, isPostOwner = true, isCommunityOwner = false, onLike = {
                        profileVM.likePost(
                            post.id, user.id, isLike = post.isLike, navController
                        )
                    }, onDelete = {
                        //profileVM.deletePost(post.id)
                        profileVM.isShowDialog.value = true
                        profileVM.curPostId.value = post.id
                    })
                }
            }

        })
    }
    if (profileVM.isLoading.value) {
        OverlayLoading()
    }
    if (profileVM.isShowDialog.value) {
        CustomDialog(onDismiss = {
            profileVM.isShowDialog.value = false
        }, onConfirm = {
            profileVM.deletePost(profileVM.curPostId.value, navController)
            profileVM.isShowDialog.value = false
        }, title = "Delete this post", text = "Are you sure to delete this post"
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen(
//        navController = NavController(LocalContext.current)
//    )
//}