package com.topic_trove.ui.modules.homescreen.widgets

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.topic_trove.data.model.User
import com.topic_trove.ui.core.values.Assets
import com.topic_trove.ui.core.values.CustomTextStyle

@SuppressLint("SuspiciousIndentation")
@Composable
fun TopbarHome(
    navController: NavController,
    user: User,
    onNavigateToCommunity: () -> Unit
){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(36.dp)
        .background(color = Color.White)
    ) {
        val icon = createRef()
        Box(modifier = Modifier
            .clickable {
                onNavigateToCommunity()
            }
            .width(30.dp)
            .height(30.dp)
            .background(color = Color.Transparent)
            .constrainAs(icon) {
                top.linkTo(parent.top, margin = 5.dp)
                start.linkTo(parent.start, margin = 14.dp)
            }){
            Image(painter =  painterResource(id = Assets.CommunityIcon), contentDescription = null,
                modifier = Modifier.width(27.dp).height(27.dp)
            )
        }


        val column = createRef()

        Column(modifier = Modifier.constrainAs(column){
            top.linkTo(parent.top, margin = 5.dp)
            start.linkTo(icon.end, margin = 20.dp)
        }) {
            Text(text = "TopicTrove", style = CustomTextStyle.homeTitle())
            Spacer(modifier = Modifier.height(3.dp))
        }

        val icon2 = createRef()
            Box(modifier = Modifier
                .clickable {
                    navController.navigate("")
                }
                .constrainAs(icon2) {
                    top.linkTo(parent.top, margin = 4.5.dp)
                    end.linkTo(parent.end, margin = 50.dp)
                }
                .background(color = Color.Transparent))
            {
                Image(painter =  painterResource(id = Assets.SearchIcon), contentDescription = null,
                    modifier = Modifier.width(27.dp).height(27.dp)
                )
            }

        val icon3 = createRef()
        Box(modifier = Modifier
            .clickable {
                navController.navigate("")
            }
            .constrainAs(icon3) {
                top.linkTo(parent.top, margin = 4.5.dp)
                end.linkTo(parent.end, margin = 14.dp)
            }
            .background(color = Color.Transparent))
        {
            Image(painter =  painterResource(id = if(user.avatar=="" || user.avatar == null) Assets.AuthorIcon else user.avatar as Int), contentDescription = null,
                modifier = Modifier.width(27.dp).height(27.dp)
            )
        }


    }
}
@Preview(showBackground = true)
@Composable
fun TopbarHomePreview() {
    val mockNavController = rememberNavController()
    val user = User(
        username = "",
        email = "",
        phoneNumber = "",
        avatar = "",
        id ="",
    )
    TopbarHome(
        mockNavController,
        user
    ) {}
}