package com.topic_trove.ui.modules.communityscreen.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.modules.communityscreen.CommunityScreenVM
import com.topic_trove.ui.modules.communityscreen.widgets.CommunityCard
import com.topic_trove.ui.modules.communityscreen.widgets.ImageBlock
import com.topic_trove.ui.modules.communityscreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.communityscreen.widgets.TopBarCreatePost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun createpostScreen(
    navController: NavController,
    communityName: String,
    communityId: String
) {

    val communityVM :CommunityScreenVM = hiltViewModel()
    val userId = communityVM.IdUser
    val snackbarHostState = communityVM.snackbarHostState
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
            TopBarCreatePost(
                state = communityVM.isEnable,
                onBack = {
                    navController.popBackStack()
                },
                onCreateClick = {
                    if (userId != null) {
                        communityVM.createPostApi(navController, communityId, userId)
                    }

                }
            )
            CollumnContent(
                communityVM = communityVM,
                communityName = communityName
            )

        }
        if (communityVM.isLoading.value) {
            OverlayLoading()
        }
    }
}

@Composable
fun CollumnContent(
    communityVM: CommunityScreenVM,
    communityName: String,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 31.5.dp, vertical = 10.dp)
            .verticalScroll(
                scrollState
            )
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        CommunityCard(name = communityName)
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Title") {
            communityVM.inputTitle(it)
            communityVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Content") {
            communityVM.inputContent(it)
            communityVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImageBlock(
            uploadImage = { file ->
                communityVM.uploadImgApi(file)
            }
        )

    }
}

@Preview(name = "Top bar create post", showBackground = true)
@Composable
fun TopbarPreview() {


}