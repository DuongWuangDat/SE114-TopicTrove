package com.topic_trove.ui.modules.registerscreen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun RegisterRoute(
    registerViewModel: RegisterViewModel = hiltViewModel(),
    onSignUpSubmitted: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        registerViewModel.message.collect { message ->
            if (message.isNotBlank()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    RegisterScreen(
        onSignInSubmitted = { name, phone, email, password ->
            registerViewModel.sendEmail(name, phone, email, password, onSignUpSubmitted)
        },
    )
}
