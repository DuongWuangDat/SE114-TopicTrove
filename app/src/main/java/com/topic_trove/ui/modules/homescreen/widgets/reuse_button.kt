package com.topic_trove.ui.modules.homescreen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors

@Composable
fun ReuseButton(
    modifier: Modifier,
    btnText: String,
    textStyle: TextStyle,
    btncolor: Color,
    onUseFunc: ()-> Unit,
) {

    Row(modifier = modifier
        .clickable {
            onUseFunc()
        }
        .clip(RoundedCornerShape(20.dp))
        .background(color = btncolor)
        .border(
            1.dp,
            AppColors.BorderStrokeColor,
            RoundedCornerShape(20.dp)
        ) // Border with 1.dp thickness and gray color
        .padding(5.dp)
    ) {
        Text(text = btnText,modifier = Modifier.padding(start = 8.dp), style = textStyle)
        Spacer(modifier = Modifier.width(10.dp))
    }
}
