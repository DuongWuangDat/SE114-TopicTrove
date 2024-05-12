package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.topic_trove.ui.core.values.AppColors.Companion.BorderStrokeColor
import com.topic_trove.ui.core.values.CustomTextStyle


@Composable
fun DeleteButton(
    modifier: Modifier,
    onDeleteFunc: ()-> Unit,
) {

    Row(modifier = modifier
        .clickable {
            onDeleteFunc()
        }
        .clip(RoundedCornerShape(20.dp))
        .background(color = Color.Transparent)
        .border(
            1.dp,
            BorderStrokeColor,
            RoundedCornerShape(20.dp)
        ) // Border with 1.dp thickness and gray color
        .padding(5.dp)
    ) {
        Text(text = "Delete", color = Color.Red ,modifier = Modifier.padding(start = 8.dp), style = CustomTextStyle.likeCommentText())
        Spacer(modifier = Modifier.width(10.dp))
    }
}
