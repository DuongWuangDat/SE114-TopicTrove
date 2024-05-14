package com.topic_trove.ui.modules.searchscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.R
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.topic_trove.ui.global_widgets.CustomDialog
import com.topic_trove.ui.global_widgets.TabHeader
import com.topic_trove.ui.global_widgets.ListContractSearchComponentUI
import com.topic_trove.ui.global_widgets.OverlayLoading


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchResultScreen(
    searchResultViewModel: SearchResultViewModel,
    navController: NavController
) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    val snackbarHostState = searchResultViewModel.snackbarHostState
    Scaffold (
       snackbarHost =  {
           SnackbarHost(hostState = snackbarHostState)
       }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ListContractSearchComponentUI(searchResultViewModel, selectTabIndex = selectedTabIndex)
            TabHeader(selectedTabIndex = selectedTabIndex) { index ->
                selectedTabIndex = index
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (selectedTabIndex) {
                0 -> PostResult(searchResultViewModel, navController)
                1 -> CommunityResult(searchResultViewModel,navController)
            }
        }

        if(searchResultViewModel.isLoading.value){
            OverlayLoading()
        }
        if(searchResultViewModel.isShowDialog.value){
            CustomDialog(
                onDismiss = {
                    searchResultViewModel.isShowDialog.value = false
                },
                onConfirm = {
                    searchResultViewModel.deletePost(searchResultViewModel.curPostId.value, navController)
                    searchResultViewModel.isShowDialog.value = false
                },
                title = "Delete this post",
                text = "Are you sure to delete this post"
            )
        }
    }
}


