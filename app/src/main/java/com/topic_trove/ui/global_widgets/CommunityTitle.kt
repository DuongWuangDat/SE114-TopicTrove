package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommunityTitle(
    name: String = "",
    url: String = "",
    description: String = "",
    memberCount: Int = 0
){

    Column (modifier = Modifier.background(color = Color.White)){
        CommunityHeader(
            url= url,
            memberCount= memberCount,
            name= name
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = if(description == "") "Community description... Community 1 is a place for you to share your experience" else  description ,
            style = CustomTextStyle.communityDescription(),
            modifier = Modifier.padding(PaddingValues(
                start = 10.dp
            ))
        )
    }
}

@Composable
fun CommunityHeader(
    url: String,
    memberCount: Int,
    name: String
){

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .background(color = Color.White)
        ) {
        var icon = createRef()
        Image(painter = rememberAsyncImagePainter(model = if(url=="") "https://firebasestorage.googleapis.com/v0/b/skillexchange-62da0.appspot.com/o/files%2FArtificial%20Intelligence%20Programming.png?alt=media&token=3accd6fe-5296-4e68-94e4-eefe98660110" else url), contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .background(color = Color.Transparent, shape = CircleShape)
                .clip(CircleShape)
                .constrainAs(icon) {
                    top.linkTo(parent.top, margin = 12.5.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
        )

        var column = createRef()

        Column(modifier = Modifier.constrainAs(column){
            top.linkTo(parent.top, margin = 14.dp)
            start.linkTo(icon.end, margin = 10.dp)
        }) {
            Text(text = if (name=="") "community1" else name, style = CustomTextStyle.communityTitle())
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "${memberCount} member", style = CustomTextStyle.communityCountMember())
        }

        var buttonJoin =createRef()

        Button(onClick = { /*TODO*/ },
            contentPadding = PaddingValues(7.dp),
            modifier = Modifier
                .width(35.dp)
                .height(27.dp)
                .constrainAs(buttonJoin) {
                    top.linkTo(parent.top, margin = 14.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                },
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                AppColors.CommunityJoinButton
            ),) {
            Text(text = "Join", style = CustomTextStyle.joinButtonText())
        }
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
    CommunityHeader(name= "hehe", url= "", memberCount = 0)
}