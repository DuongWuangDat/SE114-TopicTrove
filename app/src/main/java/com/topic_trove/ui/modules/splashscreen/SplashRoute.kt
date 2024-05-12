package com.topic_trove.ui.modules.splashscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onLogin: () -> Unit,
    onCommunity: () -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.refresh(
            onLogin = onLogin,
            onCommunity = onCommunity,
        )
    }
    SplashScreen()
}
