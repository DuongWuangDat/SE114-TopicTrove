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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.topic_trove.data.model.Community
import com.topic_trove.ui.core.values.Assets
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.routes.AppRoutes
import coil.compose.AsyncImage
import com.topic_trove.ui.core.values.AppStrings

@Composable
fun Item_Community(
    community: Community,
    navController: NavController,
){
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(25.dp)
        .background(color = Color.White)
        .clickable {
            navController.navigate("${AppRoutes.communityRoute}/${community.id}")
        }
    ) {
        val icon = createRef()
        Box(modifier = Modifier
            .width(20.dp)
            .height(20.dp)
            .background(color = Color.Transparent)
            .clip(CircleShape)
            .constrainAs(icon) {
                top.linkTo(parent.top, margin = 5.dp)
                start.linkTo(parent.start, margin = 5.dp)
            }){
            AsyncImage(if(community.icon == "") AppStrings.MODEL_IMG
            else community.icon,  contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
        }


        val column = createRef()
        Column(modifier = Modifier.fillMaxWidth(fraction = 0.7f).constrainAs(column){
            top.linkTo(parent.top, margin = 5.dp)
            start.linkTo(icon.end, margin = 20.dp)
        }) {

            Text(text = if (community.communityName== "") "community2" else community.communityName,
                style = CustomTextStyle.itemCommunity(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
            Spacer(modifier = Modifier.height(5.dp))
        }

    }
}


@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    val mockNavController = rememberNavController()
    Item_Community(
        community = Community(),
        navController = mockNavController
    )
}