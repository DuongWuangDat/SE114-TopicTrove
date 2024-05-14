package com.topic_trove.ui.modules.homescreen.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.modules.communityscreen.widgets.CommunityCard
import com.topic_trove.ui.modules.homescreen.widgets.ImageBlock
import com.topic_trove.ui.modules.homescreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.homescreen.HomeScreenViewModel
import com.topic_trove.ui.modules.homescreen.widgets.TopbarCreateCommunity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateCommunityScreen(
    navController: NavController,
) {
    val homeVM : HomeScreenViewModel = hiltViewModel()
    val snackbarHostState = homeVM.snackbarHostState
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
            TopbarCreateCommunity(navController = navController, homeVM = homeVM)
            collumnContent(
                homeVM = homeVM,
                snackbarHostState = snackbarHostState
            )

        }
        if (homeVM.isLoading.value) {
            OverlayLoading()
        }
    }
}

@Composable
fun collumnContent(
    homeVM: HomeScreenViewModel,
    snackbarHostState: SnackbarHostState
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(horizontal = 31.5.dp, vertical = 10.dp)
            .verticalScroll(
                scrollState
            )
    ) {
        Spacer(modifier = Modifier.height(16.dp))
            ImageBlock(
                isLoading = homeVM.isLoading,
                snackbarHostState = snackbarHostState,
                uploadImage = { file ->
                    homeVM.uploadImgApi(file)
                },
                inputImage = {
                    homeVM.inputImage(it)
                }
            )
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldCard(title = "Name", singleLine = true) {
            homeVM.inputCommunityName(it)
            homeVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Description") {
            homeVM.inputCommunityDescription(it)
            homeVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Rules") {
            homeVM.inputCommunityRules(it)
            homeVM.checkIsEnable()
        }


    }
}

@Composable
@Preview
fun Preview(){
    val mockNavController = rememberNavController()
    CreateCommunityScreen(navController = mockNavController)
}
