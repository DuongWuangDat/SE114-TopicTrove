package com.topic_trove.ui.modules.registerscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RegisterRoute(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onSignUpSubmitted: () -> Unit,
) {
    RegisterScreen(
        snackBarHostState = registerViewModel.snackBarHostState,
        onSignInSubmitted = { name, phone, email, password ->
            registerViewModel.sendEmail(name, phone, email, password, onSignUpSubmitted)
        },
    )
}
