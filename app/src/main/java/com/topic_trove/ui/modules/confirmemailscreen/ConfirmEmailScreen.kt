package com.topic_trove.ui.modules.confirmemailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.topic_trove.R
import com.topic_trove.ui.core.utils.supportWideScreen
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.MyTopCenterAppBar

@Composable
fun ConfirmEmailScreen(
    modifier: Modifier = Modifier,
    onSubmitted: (otpValue: String) -> Unit,
    onNavUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            MyTopCenterAppBar(
                topAppBarText = stringResource(R.string.enter_6_character_code),
                onNavUp = onNavUp,
            )
        }
    ) { contentPadding ->
        ConstraintLayout(
            modifier = modifier
                .background(color = AppColors.White)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            var otpValue by remember {
                mutableStateOf("")
            }
            val onSubmit = {
                if (otpValue.length == 6) {
                    onSubmitted(otpValue)
                }
            }
            val (content, button) = createRefs()
            LazyColumn(
                modifier = Modifier
                    .supportWideScreen()
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = contentPadding,
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.enter_the_code),
                        style = TextStyle(
                            color = AppColors.TitleFieldCreatePost,
                            fontSize = 12.sp,
                            fontWeight = FontWeight(300),
                            lineHeight = 17.sp,
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    BasicTextField(
                        value = otpValue,
                        onValueChange = {
                            if (it.length <= 6) {
                                otpValue = it
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        decorationBox = {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                repeat(6) { index ->
                                    val char = when {
                                        index >= otpValue.length -> ""
                                        else -> otpValue[index].toString()
                                    }
                                    val isFocused = index == otpValue.length
                                    Box(
                                        modifier = Modifier
                                            .width(50.dp)
                                            .height(80.dp)
                                            .border(
                                                width = if (isFocused) 2.dp else 1.dp,
                                                color = AppColors.BorderStrokeColor,
                                                shape = RoundedCornerShape(20.dp),
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            style = TextStyle(
                                                fontSize = 34.sp,
                                                fontWeight = FontWeight(300),
                                                color = AppColors.Black,
                                            ),
                                            text = char,
                                            textAlign = TextAlign.Center,
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(6.dp))
                                }

                            }
                        }
                    )
                }
            }
            Button(
                onClick = { onSubmit() },
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 40.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.CreatePostButton),
                enabled = otpValue.length == 6,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = stringResource(id = R.string.next),
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

@Preview(showBackground = true, heightDp = 640)
@Composable
fun ConfirmEmailScreenPreview() {
    ConfirmEmailScreen(
        onSubmitted = {},
        onNavUp = {}
    )
}