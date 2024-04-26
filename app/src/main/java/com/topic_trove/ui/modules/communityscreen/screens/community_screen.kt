package com.topic_trove.ui.modules.communityscreen.screens

import PostCard
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.topic_trove.data.model.Community
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.AppColors
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
    var PostList : List<Post> = listOf(Post(communityName = "T1Bo", authorName = "Name", title = "T1",
        content = "1qqqqqqqqqqqqqqqqqqqqqqq 11111111", imageUrl = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2F1000002488.jpg?alt=media&token=f47b647a-c17c-404f-b42d-120b34c14e39"),
        Post(communityName = "T1Bo", authorName = "Name", title = "T1",
            content = "1qqqqqqqqqqqqqqqqqqqqqqq 11111111", imageUrl = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2F1000002488.jpg?alt=media&token=f47b647a-c17c-404f-b42d-120b34c14e39"))
    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopbarCommunity(community = community, onNavigateToCreatePost=onNavigateToCreatePost)
            CommunityTitle {

            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(PostList) { post ->
                    Divider(color = AppColors.DividerColor, thickness = 0.3.dp)
                    PostCard(data = post, isPostOwner = true, isCommunityOwner = true, onDelete = {
                        println("Hello")
                    })
                }
            }
        }
    }
}