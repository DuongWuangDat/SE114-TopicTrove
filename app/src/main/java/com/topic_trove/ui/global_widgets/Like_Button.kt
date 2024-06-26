package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors.Companion.BorderStrokeColor
import com.topic_trove.ui.core.values.Assets.Companion.LikeIcon
import com.topic_trove.ui.core.values.Assets.Companion.UnlikeIcon
import com.topic_trove.ui.core.values.CustomTextStyle


@Composable
fun LikeButton(
    interestCount: Int = 0,
    isLike: Boolean = false,
    onLikePress: () -> Unit
) {
    var isLiked by remember { mutableStateOf(isLike) }
    var likeCount by remember { mutableStateOf(interestCount) }

    Row(modifier = Modifier
        .clickable {
            onLikePress()
            println(isLiked)
            likeCount = if (isLiked) likeCount - 1 else likeCount + 1
            isLiked = !isLiked
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
        Image(
            painter = painterResource(id = if (isLiked) LikeIcon else UnlikeIcon),
            contentDescription = null,
            modifier = Modifier.size(15.dp)
        )

        Text(
            text = likeCount.toString(),
            modifier = Modifier.padding(start = 8.dp),
            style = CustomTextStyle.likeCommentText()
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}


@Composable
fun LikeDetailPostButton(
    interestCount: Int = 0, isLike: Boolean = false, onLikePress: (Boolean) -> Unit
) {
    Row(modifier = Modifier
        .clickable {
            onLikePress(!isLike)
        }
        .background(color = Color.Transparent)
        .border(
            1.dp, BorderStrokeColor, RoundedCornerShape(20.dp)
        ) // Border with 1.dp thickness and gray color
        .padding(5.dp)) {
        Image(
            painter = painterResource(id = if (isLike) LikeIcon else UnlikeIcon),
            contentDescription = null,
            modifier = Modifier.size(15.dp)
        )

        Text(
            text = interestCount.toString(),
            modifier = Modifier.padding(start = 8.dp),
            style = CustomTextStyle.likeCommentText()
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Preview
@Composable
fun PreviewLikeButton() {
    LikeButton {}
}