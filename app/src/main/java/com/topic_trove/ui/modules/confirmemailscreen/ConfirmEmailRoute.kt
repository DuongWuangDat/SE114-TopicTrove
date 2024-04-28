package com.topic_trove.ui.modules.confirmemailscreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ConfirmEmailRoute(
    confirmEmailViewModel: ConfirmEmailViewModel = hiltViewModel(),
    onSubmitted: () -> Unit,
    onNavUp: () -> Unit,
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        confirmEmailViewModel.message.collect { message ->
            if (message.isNotBlank()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    ConfirmEmailScreen(
        onSubmitted = { otpValue ->
            confirmEmailViewModel.register(
                otpValue, onSubmitted
            )
        },
        onNavUp = onNavUp,
    )
}
