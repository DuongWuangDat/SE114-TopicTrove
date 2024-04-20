package com.topic_trove.ui.modules.communityscreen.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.topic_trove.data.model.Community
import com.topic_trove.ui.global_widgets.CommunityTitle
import com.topic_trove.ui.modules.communityscreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.communityscreen.widgets.TopBarCreatePost
import com.topic_trove.ui.modules.communityscreen.widgets.TopbarCommunity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommunityScreen(
    community: Community = Community(),
    onNavigateToCreatePost: ()->Unit
){

    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopbarCommunity(community = community, onNavigateToCreatePost=onNavigateToCreatePost)
            CommunityTitle {

            }
            LazyColumn {

            }
        }
    }
}