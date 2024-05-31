package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.AppColors.Companion.BorderStrokeColor


@Composable
fun ReplyButton(
    modifier: Modifier = Modifier,
    onReplyFunc: () -> Unit,
) {

    Row(modifier = modifier
        .height(27.dp)
        .clickable {
            onReplyFunc()
        }
        .background(color = Color.Transparent)
        .border(
            1.dp,
            BorderStrokeColor,
            RoundedCornerShape(20.dp)
        )
        .padding(5.dp)
    ) {
        Text(
            text = "Reply",
            color = AppColors.CreatePostButton,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight(500),
                lineHeight = 12.sp 
            )
        )
      //  Spacer(modifier = Modifier.width(10.dp))
    }
}
