package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.topic_trove.ui.core.values.AppColors

@Composable
fun CommunityTitle(

){

}

@Composable
fun CommunityHeader(


){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .background(color = Color.Transparent)
        ) {
        var icon = createRef()
        AsyncImage(model = "https://firebasestorage.googleapis.com/v0/b/skillexchange-62da0.appspot.com/o/files%2FDucAvatar?alt=media&token=1bfad71b-54f7-4a22-8aad-73cede68dc95", contentDescription = null,
            modifier = Modifier.height(30.dp).width(30.dp)
                .background(color = Color.White, shape = CircleShape))
    }
}


@Preview(name = "Community title", showBackground = true)
@Composable
fun CommunityTitlePreview(){
    CommunityTitle()
}

@Preview(name = "Community header", showBackground = true)
@Composable
fun CommunityHeaderPreview(){
    CommunityHeader()
}