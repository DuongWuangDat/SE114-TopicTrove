package com.topic_trove.ui.modules.searchscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SearchScreenRoute(
    searchResultViewModel: SearchResultViewModel = hiltViewModel(),
    navController: NavController
){

    LaunchedEffect(key1 = true) {
        searchResultViewModel.searchValue.value=""
    }
    SearchResultScreen(searchResultViewModel = searchResultViewModel, navController)

}
