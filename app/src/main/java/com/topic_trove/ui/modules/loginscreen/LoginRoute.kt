package com.topic_trove.ui.modules.loginscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    onSubmitted: () -> Unit,
    onNavUp: () -> Unit,
) {
    LoginScreen(
        snackBarHostState = viewModel.snackBarHostState,
        onSignInSubmitted = { email, password ->
            viewModel.login(email, password) {
                onSubmitted()
            }
        },
        onNavUp = onNavUp,
    )
}
