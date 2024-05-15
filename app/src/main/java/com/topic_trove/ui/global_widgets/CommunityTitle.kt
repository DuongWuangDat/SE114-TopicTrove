package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.topic_trove.data.model.Community
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CommunityTitle(
    community: Community = Community(),
    isJoin: MutableState<Boolean> ,
    isAuthor: Boolean = false,
    modifier: Modifier= Modifier,
    onClick: () -> Unit = {},
    joinFunction: ()-> Unit

){

    Column (modifier = modifier.background(color = Color.White).clickable {
        onClick()
    }){
        CommunityHeader(
            community = community,
            isJoin = isJoin,
            isAuthor = isAuthor,
            joinFunction = {
                joinFunction()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = if(community.description == "") "Community description... Community 1 is a place for you to share your experience" else  community.description ,
            style = CustomTextStyle.communityDescription(),
            modifier = Modifier.padding(PaddingValues(
                start = 10.dp
            ))
        )

    }
}

@Composable
fun CommunityHeader(
    community: Community,
    isJoin: MutableState<Boolean>,
    joinFunction: () -> Unit,
    isAuthor: Boolean
){

    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(45.dp)
        .background(color = Color.White)
        ) {
        var icon = createRef()
        Image(painter = rememberAsyncImagePainter(model = if(community.icon=="") "https://firebasestorage.googleapis.com/v0/b/skillexchange-62da0.appspot.com/o/files%2FArtificial%20Intelligence%20Programming.png?alt=media&token=3accd6fe-5296-4e68-94e4-eefe98660110" else community.icon), contentDescription = null,
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
            Text(text = if (community.communityName=="") "community1" else community.communityName, style = CustomTextStyle.communityTitle())
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "${community.memberCount} member", style = CustomTextStyle.communityCountMember())

        }

        var buttonJoin =createRef()

        if(!isAuthor){
            if(!isJoin.value){
                JoinButton(
                    modifier = Modifier
                        .constrainAs(buttonJoin) {
                            top.linkTo(parent.top, margin = 14.dp)
                            end.linkTo(parent.end, margin = 10.dp)
                        }){
                    isJoin.value = true
                    joinFunction()
                }
            } else{
                JoinedButton(modifier = Modifier.constrainAs(buttonJoin) {
                    top.linkTo(parent.top, margin = 14.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                }) {
                    isJoin.value= false
                    joinFunction()
                }
            }
        }




    }
}



@Composable
fun JoinButton(
    modifier: Modifier,
    onClick: () -> Unit
){
    Button(onClick = {onClick() },
        contentPadding = PaddingValues(7.dp),
        modifier = modifier
            .width(35.dp)
            .height(27.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            AppColors.CommunityJoinButton
        ),) {
        Text(text = "Join", style = CustomTextStyle.joinButtonText(Color.White))
    }
}

@Composable
fun JoinedButton(
    modifier: Modifier,
    onClick : ()->Unit
){
    Button(onClick = { onClick() },
        contentPadding = PaddingValues(7.dp),
        modifier = modifier
            .width(47.dp)
            .height(27.dp),
        border = BorderStroke(width = 1.dp, Color.Black),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            AppColors.White
        ),) {
        Text(text = "Joined", style = CustomTextStyle.joinButtonText(color = Color.Black))
    }
}



