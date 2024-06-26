package com.topic_trove.ui.modules.confirmemailscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ConfirmEmailRoute(
    confirmEmailViewModel: ConfirmEmailViewModel = hiltViewModel(),
    onSubmitted: () -> Unit,
    onNavUp: () -> Unit,
) {

    ConfirmEmailScreen(
        onSubmitted = { otpValue ->
            confirmEmailViewModel.register(
                otpValue, onSubmitted
            )
        },
        onNavUp = onNavUp,
        snackBarHostState = confirmEmailViewModel.snackBarHostState,
    )
}
