package com.topic_trove.ui.global_widgets

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.theme.*
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import com.topic_trove.R
import com.topic_trove.ui.core.values.AppColors.Companion.BorderStrokeColor
import com.topic_trove.ui.core.values.Assets.Companion.LikeIcon
import com.topic_trove.ui.core.values.Assets.Companion.UnlikeIcon
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
        .background(color = Color.Transparent)
        .border(
            1.dp,
            BorderStrokeColor,
            RoundedCornerShape(20.dp)
        ) // Border with 1.dp thickness and gray color
        .padding(5.dp)
    ) {
        Text(text = "Delete", color = Color.Red ,modifier = Modifier.padding(start = 8.dp), style = CustomTextStyle.LikeCommentText())
        Spacer(modifier = Modifier.width(10.dp))
    }
}