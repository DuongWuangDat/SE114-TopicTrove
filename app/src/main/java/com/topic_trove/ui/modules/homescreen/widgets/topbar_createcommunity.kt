package com.topic_trove.ui.modules.homescreen.widgets

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.topic_trove.ui.core.values.Assets
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.core.values.convertHex
import com.topic_trove.ui.modules.homescreen.HomeScreenViewModel
import com.topic_trove.ui.routes.AppRoutes

@Composable
fun TopbarCreateCommunity(
    navController: NavController,
    homeVM: HomeScreenViewModel
){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(36.dp)
        .background(color = Color.White)
    ) {
        val icon = createRef()
        Box(modifier = Modifier
            .clickable {
                navController.navigate(AppRoutes.homeRoute)
            }
            .width(30.dp)
            .height(30.dp)
            .background(color = Color.Transparent)
            .constrainAs(icon) {
                top.linkTo(parent.top, margin = 5.dp)
                start.linkTo(parent.start, margin = 14.dp)
            }){
            Image(painter =  painterResource(id = Assets.CancelImg), contentDescription = null,
                modifier = Modifier.width(27.dp).height(27.dp)
            )
        }


        val column = createRef()

        Column(modifier = Modifier.constrainAs(column){
            top.linkTo(parent.top, margin = 8.dp)
            start.linkTo(icon.end, margin = 25.dp)
        }) {
            Text(text = "Create a community", style = CustomTextStyle.communityCreateTitle())
            Spacer(modifier = Modifier.height(3.dp))
        }

        val button = createRef()
        ReuseButton(
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 4.5.dp)
                end.linkTo(parent.end, margin = 15.dp)
            },
            btnText = "Create",
            textStyle = TextStyle(color = Color(convertHex("#686868")), fontSize = 12.sp, fontWeight = FontWeight(600)),
            btncolor = Color(convertHex("#DFDFDF")),
            onUseFunc = {
                homeVM.createCommunity(navController)
            }
        )

    }
}
