package com.topic_trove.ui.modules.communityscreen.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.AppStrings
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.modules.communityscreen.CommunityScreenVM
import com.topic_trove.ui.modules.communityscreen.widgets.AddImageRow
import com.topic_trove.ui.modules.communityscreen.widgets.CommunityCard
import com.topic_trove.ui.modules.communityscreen.widgets.ImageBlock
import com.topic_trove.ui.modules.communityscreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.communityscreen.widgets.TopBarCreatePost
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun createpostScreen(
    navController: NavController
){

    val communityVM = viewModel<CommunityScreenVM>()
    val post by communityVM.postData.collectAsState()
    val snackbarHostState = communityVM.snackbarHostState
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
            TopBarCreatePost(
                state = communityVM.isEnable,
                onBack = {
                         navController.popBackStack()
                },
                onCreateClick = {
                    communityVM.createPostApi()
                }
            )
            collumnContent(
                communityVM = communityVM,
                snackbarHostState= snackbarHostState
            )

        }
        if(communityVM.isLoading.value){
            Box(
                Modifier
                    .fillMaxSize()
                    .background(AppColors.BackgroundIndicatorr)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = AppColors.CreatePostButton)
                }

            }
        }
    }
}

@Composable
fun collumnContent(
    communityVM: CommunityScreenVM,
    snackbarHostState: SnackbarHostState
){
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .padding(horizontal = 31.5.dp, vertical = 10.dp)
        .verticalScroll(
            scrollState
        )
        ) {
        Spacer(modifier = Modifier.height(15.dp))
        CommunityCard(name = "Community1")
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Title"){
            communityVM.inputTitle(it)
            communityVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Content"){
            communityVM.inputContent(it)
            communityVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImageBlock(
            isLoading = communityVM.isLoading,
            snackbarHostState = snackbarHostState,
            uploadImage = {file ->
                communityVM.uploadImgApi(file)
            },
            inputImage = {
                communityVM.inputImage(it)
            }
        )

    }
}
@Preview(name = "Top bar create post", showBackground = true)
@Composable
fun TopbarPreview(){



}