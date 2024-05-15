package com.topic_trove.ui.modules.communityscreen.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.topic_trove.data.model.Community
import com.topic_trove.ui.core.values.Assets
import com.topic_trove.ui.core.values.CustomTextStyle

@Composable
fun TopbarCommunity(
    isAuthor: Boolean,
    isJoin : Boolean,
    community: Community,
    onNavigateToCreatePost: ()->Unit,
    onNavigateBack: ()->Unit
){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(36.dp)
        .background(color = Color.White)
    ) {
        val icon = createRef()
        Box(modifier = Modifier
            .clickable {
                onNavigateBack()
            }
            .width(30.dp)
            .height(30.dp)
            .background(color = Color.Transparent)
            .constrainAs(icon) {
                top.linkTo(parent.top, margin = 5.dp)
                start.linkTo(parent.start, margin = 14.dp)
            }){
            Image(painter =  painterResource(id = Assets.BackArrowImg), contentDescription = null,
                modifier = Modifier.width(27.dp).height(27.dp)
                )
        }


        val column = createRef()

        Column(modifier = Modifier.constrainAs(column){
            top.linkTo(parent.top, margin = 5.dp)
            start.linkTo(icon.end, margin = 20.dp)
        }) {
            Text(text = if (community.communityName== "") "community1" else community.communityName, style = CustomTextStyle.communityTitle())
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = "${community.memberCount} member", style = CustomTextStyle.communityCountMember())
        }

        val icon2 = createRef()
        if(isJoin || isAuthor){
            Box(modifier = Modifier
                .clickable {
                    println("Create post")
                    onNavigateToCreatePost()
                }
                .constrainAs(icon2) {
                    top.linkTo(parent.top, margin = 4.5.dp)
                    end.linkTo(parent.end, margin = 14.dp)
                }
                .background(color = Color.Transparent))
            {
                Image(painter =  painterResource(id = Assets.CreatePostImg), contentDescription = null,
                    modifier = Modifier.width(27.dp).height(27.dp)
                )
            }
        }


    }
}