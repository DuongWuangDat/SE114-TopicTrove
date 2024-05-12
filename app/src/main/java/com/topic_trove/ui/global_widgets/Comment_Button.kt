package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors.Companion.BorderStrokeColor
import com.topic_trove.ui.core.values.Assets.Companion.CommentIcon
import com.topic_trove.ui.core.values.CustomTextStyle

@Composable
fun CommentButton(
    commentCount: Int = 15
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.Transparent)
            .border(
                1.dp,
                BorderStrokeColor,
                RoundedCornerShape(20.dp)
            ) // Border with 1.dp thickness and gray color
            .padding(5.dp) // Padding inside the border
    ) {
        Image(
            painter = painterResource(id = CommentIcon),
            contentDescription = null,
            modifier = Modifier.size(15.dp)
        )
        Row {
            Text(
                text = commentCount.toString(),
                modifier = Modifier.padding(start = 8.dp),
                style = CustomTextStyle.likeCommentText()
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = if (commentCount > 1) "Comments" else "Comment",
                style = CustomTextStyle.likeCommentText()
            )

        }
    }
}

@Preview
@Composable
fun PreviewCommentButton() {
    CommentButton()
}