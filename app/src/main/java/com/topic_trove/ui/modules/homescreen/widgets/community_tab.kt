package com.topic_trove.ui.modules.homescreen.widgets

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.topic_trove.data.model.Community
import com.topic_trove.ui.core.values.Assets
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.modules.homescreen.HomeScreenViewModel
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CommunityTab(
    isVisible : Boolean,
    communities: List<Community>,
    navController: NavController
) {


    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { -200 }, animationSpec = tween(durationMillis = 300)),
        exit = slideOutHorizontally(targetOffsetX = { -200 }, animationSpec = tween(durationMillis = 300))
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .width(200.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column {
                Text(text = "Your Communities", style = CustomTextStyle.communityTitleInHome())
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    items(communities) { community ->
                        Item_Community(community = community, navController = navController)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    val mockNavController = rememberNavController()
    val community1 = Community()
    val community2 = Community()
    val communities = listOf(community1, community2)
    CommunityTab(
        true,
        communities = communities,
        navController = mockNavController
    )
}