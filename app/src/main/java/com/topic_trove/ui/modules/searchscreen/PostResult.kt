package com.topic_trove.ui.modules.searchscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.PostCard


@Composable
fun PostResult (

) {
    val postList = listOf(Post())
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {

        }
        items(postList) { post ->
            val isCommunityOwner = false

            val isPostOwner = false

            Divider(color = AppColors.DividerColor, thickness = 0.3.dp)
            PostCard(
                data = post,
                isPostOwner = isPostOwner,
                isCommunityOwner = isCommunityOwner,
                onLike = {


                },
                onDelete = {

                },
                onClickable = {


                }
            )
        }
    }
}