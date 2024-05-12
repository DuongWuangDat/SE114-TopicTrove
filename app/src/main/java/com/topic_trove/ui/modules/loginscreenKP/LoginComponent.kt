package com.topic_trove.ui.modules.loginscreenKP

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R
import com.topic_trove.ui.modules.searchscreen.TopicImage
import com.topic_trove.ui.modules.searchscreen.TopicText

@Preview(showBackground = true)
@Composable
fun LoginComponent(){
    Box(modifier = Modifier.fillMaxSize()) {
        TopicImage(
            resDrawable = R.drawable.welcome_bg,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 46.dp) ){
            TopicText(
                fontSize =32.sp ,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                text = "Topic Trove",
                color = colorResource(id = R.color.white),
                modifier = Modifier.fillMaxWidth()
            )
            TopicText(
                fontSize =12.sp ,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W300,
                textAlign = TextAlign.Center,
                text = "Ask and answer any topic with us",
                color = colorResource(id = R.color.white),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(80.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                Box(
                    modifier = Modifier
                        .background(
                            colorResource(id = R.color.white),
                            shape = RoundedCornerShape(12.dp)
                        )

                ) {
                    TopicText(
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W600,
                        text = "Log in",
                        color = colorResource(id = R.color.blue_500),
                        modifier = Modifier.padding(
                            start = 55.dp,
                            end = 55.dp,
                            top = 13.dp,
                            bottom = 13.dp
                        )

                    )

                }
                Spacer(modifier = Modifier.width(22.dp))

                Box(
                    modifier = Modifier
                        .background(
                            colorResource(id = R.color.blue_500),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    TopicText(
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W600,
                        text = "Register",
                        color = colorResource(id = R.color.white),
                        modifier = Modifier.padding(
                            start = 55.dp,
                            end = 55.dp,
                            top = 13.dp,
                            bottom = 13.dp
                        )

                    )
                }
            }
        }

    }
}