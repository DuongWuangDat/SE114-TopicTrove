package com.topic_trove.ui.global_widgets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.theme.*
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.topic_trove.R
@Composable
fun CommentButton() {
    var commentCount by remember { mutableIntStateOf(0) }

    Row(modifier = Modifier
        .clickable {
            commentCount += 1
        }
        .clip(RoundedCornerShape(5.dp)) // Rounded corners for the border
        .border(1.dp, Gray600, RoundedCornerShape(5.dp)) // Border with 1.dp thickness and gray color
        .padding(4.dp) // Padding inside the border
    ) {
        Image(
            painter = painterResource(id = R.drawable.add_btn),
            contentDescription = null
        )
        Text(text = commentCount.toString(), modifier = Modifier.padding(start = 8.dp), onTextLayout = { result -> /* Handle TextLayoutResult here */ })
    }
}

@Preview
@Composable
fun PreviewCommentButton() {
    CommentButton()
}