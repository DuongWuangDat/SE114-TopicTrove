package com.topic_trove.ui.modules.communityscreen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.Assets
import com.topic_trove.ui.core.values.CustomTextStyle

@Composable
fun TopBarCreatePost(
    state : MutableState<Boolean>,
    onCreateClick: ()-> Unit,
    onBack: ()->Unit
){
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(color = Color.White)) {
        Box(modifier = Modifier
            .padding(start = 10.dp, top = 3.dp)
            .width(30.dp)
            .height(30.dp)
            .clickable {
                println("Cancel button")
                onBack()
            }

        ){
            Image(painter = painterResource(id = Assets.CancelImg), contentDescription = null, modifier = Modifier.fillMaxSize())
        }
        Text(text = "Create post", style = CustomTextStyle.createPostHeader(), modifier = Modifier.padding(start = 20.dp, top = 9.dp))
        Spacer(modifier = Modifier.width((screenWidth*0.45).dp))
        Button(onClick = { onCreateClick() },
            enabled = state.value,
            contentPadding = PaddingValues(horizontal = 11.dp, vertical = 7.5.dp),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = AppColors.DisableCreatePostButton,
                containerColor = AppColors.CreatePostButton
            ),
                modifier = Modifier
                    .padding(top= 4.dp)
                    .width(55.dp)
                    .height(27.dp)
            ) {
            Text(text = "Create", style = if(state.value) CustomTextStyle.createTextButton(AppColors.CreateTextButton) else CustomTextStyle.createTextButton(AppColors.DisableTextCreatePost))
        }
    }
}

@Preview(name = "Top bar create post", showBackground = true)
@Composable
fun TopbarPreview(){



}