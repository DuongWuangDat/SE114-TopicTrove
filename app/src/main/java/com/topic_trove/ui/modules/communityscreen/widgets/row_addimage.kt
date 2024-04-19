package com.topic_trove.ui.modules.communityscreen.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle

@Composable
fun AddImageRow(
    onClick: ()->Unit
){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    val screenHeight = configuration.screenHeightDp.dp
    Row{
        Text(text = "Image", style = CustomTextStyle.titleFieldCreatePost())
        Spacer(modifier = Modifier.width((screenWidth*0.55).dp))
        Button(onClick = { onClick() }, colors = ButtonDefaults.buttonColors(
            AppColors.AddImgPostButton
        ), shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 1.dp, vertical = 7.dp),
            modifier = Modifier
                .width((0.2* screenWidth).dp)
                .height(27.dp)
        ) {
            Text(text = "Add Image", style = CustomTextStyle.addImgButtonCommunity())
        }
    }
}