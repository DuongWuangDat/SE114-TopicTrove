package com.topic_trove.ui.modules.searchscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.topic_trove.data.model.Community
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.CommunityTitle
import com.topic_trove.ui.global_widgets.PostCard

@Composable
fun CommunityResult (

) {
    val communityList = listOf(Community())
    var isJoin = remember {
        mutableStateOf(false)
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {

        }
        items(communityList) { community ->

            Divider(color = AppColors.DividerColor, thickness = 0.3.dp)
            CommunityTitle(isJoin = isJoin) {

            }
        }
    }
}