package com.topic_trove.ui.modules.loginscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R
import com.topic_trove.ui.core.utils.supportWideScreen
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.MyTextField
import com.topic_trove.ui.global_widgets.MyTopCenterAppBar
import com.topic_trove.ui.global_widgets.Password
import com.topic_trove.ui.modules.registerscreen.states.EmailState
import com.topic_trove.ui.modules.registerscreen.states.EmailStateSaver
import com.topic_trove.ui.modules.registerscreen.states.PasswordState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    onSignInSubmitted: (email: String, password: String) -> Unit,
    onNavUp: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            MyTopCenterAppBar(
                topAppBarText = stringResource(R.string.login),
                onNavUp = onNavUp,
            )
        },
        content = { contentPadding ->

            val emailState by rememberSaveable(stateSaver = EmailStateSaver) {
                mutableStateOf(EmailState())
            }
            val passwordState = remember { PasswordState() }
            val onSubmit = {
                if (emailState.isValid && passwordState.isValid) {
                    onSignInSubmitted(
                        emailState.text, passwordState.text
                    )
                }
            }
            val focusRequester = remember { FocusRequester() }

            LazyColumn(
                modifier = modifier
                    .background(color = AppColors.White)
                    .padding(horizontal = 24.dp)
                    .fillMaxSize()
                    .supportWideScreen(),
                contentPadding = contentPadding,
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Email
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.email), style = TextStyle(
                            color = AppColors.TitleFieldCreatePost,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 24.sp,
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MyTextField(textState = emailState,
                        onImeAction = { focusRequester.requestFocus() })

                    // Password
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.password), style = TextStyle(
                            color = AppColors.TitleFieldCreatePost,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 24.sp,
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Password(passwordState = passwordState,
                        modifier = Modifier.focusRequester(focusRequester),
                        onImeAction = { })

                    Button(
                        onClick = { onSubmit() },
                        modifier = Modifier
                            .background(color = AppColors.White)
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 40.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.CreatePostButton),
                        enabled = emailState.isValid && passwordState.isValid
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 12.dp),
                            text = stringResource(id = R.string.login),
                            style = TextStyle(
                                color = AppColors.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight(500),
                                lineHeight = 24.sp,
                            )
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, heightDp = 640)
@Composable
private fun RegisterScreenPreview() {
    LoginScreen(
        onSignInSubmitted = { _, _ -> },
        onNavUp = {}
    )
}