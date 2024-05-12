package com.topic_trove.ui.modules.communityscreen.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle

@Composable
fun CommunityCard(
    name: String
){
    Column {
        Text(text = "Community", style = CustomTextStyle.titleFieldCreatePost())
        Spacer(modifier = Modifier.height(17.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = AppColors.TextFieldColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))){
            Text(text = name, style = CustomTextStyle.filedTextCreatePost(), modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterStart))
        }
    }
}


