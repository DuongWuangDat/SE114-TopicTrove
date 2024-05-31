package com.topic_trove.ui.modules.splashscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashRoute(
    viewModel: SplashViewModel = hiltViewModel(),
    onLogin: () -> Unit,
    onCommunity: () -> Unit,
) {

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        viewModel.refresh(
            onLogin = onLogin,
            onCommunity = onCommunity,
        )
    }
    SplashScreen()
}
