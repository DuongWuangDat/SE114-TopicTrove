package com.topic_trove.ui.modules.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R
import com.topic_trove.ui.core.values.AppColors

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    login: () -> Unit,
    register: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.White),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.welcome_bg),
            contentDescription = null,
        )
        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Topic Trove",
                color = AppColors.White,
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 46.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ask and answer any topic with us",
                color = AppColors.White,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight(300),
                    lineHeight = 17.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 45.dp),
            ) {
                Button(
                    onClick = { login() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.White),
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = stringResource(id = R.string.login),
                        style = TextStyle(
                            color = AppColors.CreatePostButton,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 24.sp,
                        )
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Button(
                    onClick = { register() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.CreatePostButton),
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = stringResource(id = R.string.register),
                        style = TextStyle(
                            color = AppColors.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 24.sp,
                        )
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    WelcomeScreen(modifier = Modifier, {}, {})
}