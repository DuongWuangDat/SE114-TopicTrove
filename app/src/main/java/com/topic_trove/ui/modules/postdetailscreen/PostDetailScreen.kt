package com.topic_trove.ui.modules.postdetailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.convertHex
import com.topic_trove.ui.global_widgets.CommentCard
import com.topic_trove.ui.global_widgets.MyTopLeftAppBar
import com.topic_trove.ui.global_widgets.PostCard
import com.topic_trove.ui.routes.AppRoutes

@Composable
fun PostDetailScreen(
    postDetail: PostDetailUiState,
    onNavUp: () -> Unit,
    onNavAddComment: () -> Unit,
    onLike: (Boolean) -> Unit,
    onReply: (String, String) -> Unit,
    onDelete: (String) -> Unit,
    onLikeComment: (Boolean, String) -> Unit,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            MyTopLeftAppBar(topAppBarText = "", onNavUp = { onNavUp() }, actions = {
                Button(
                    onClick = { onNavAddComment() },
                    contentPadding = PaddingValues(7.dp),
                    modifier = Modifier
                        .width(78.dp)
                        .height(30.dp)
                        .padding(end = 14.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(AppColors.CommunityJoinButton),
                ) {
                    Text(
                        text = stringResource(id = R.string.comment), style = TextStyle(
                            color = AppColors.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight(600),
                            lineHeight = 12.sp,
                        )
                    )
                }
            })
        },
        content = { contentPadding ->
            LazyColumn(
                contentPadding = contentPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
            ) {
                item {
                    PostCard(
                        data = postDetail.post,
                        isPostOwner = postDetail.owner,
                        isCommunityOwner = postDetail.isCommunityOwner,
                        onDelete = null,
                        onLike = { onLike(it) },
                        fromRoute = AppRoutes.postDetailRoute,
                    )
                    postDetail.comments.forEach { comment ->
                        Divider(
                            color = Color(convertHex("#E1E1E1")),
                            thickness = 0.3.dp,
                            modifier = Modifier.height(4.dp)
                        )
                        CommentCard(
                            modifier = Modifier.padding(top = 15.dp, start = 15.dp, bottom = 15.dp),
                            data = comment,
                            onDelete = { commentId ->
                                onDelete(commentId)
                            },
                            onLike = { isLiked, id ->
                                onLikeComment(isLiked, id)
                            },
                            onReply = { content, parentComment ->
                                onReply(content, parentComment)
                            },
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PostDetailScreenPreview() {
    PostDetailScreen(
        postDetail = PostDetailUiState(
            post = Post(
                communityName = "T1Bo",
                authorName = "Name1",
                title = "T1",
                content = "11111111",
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2F1000002488.jpg?alt=media&token=f47b647a-c17c-404f-b42d-120b34c14e39"
            ),
        ),
        onNavUp = {},
        onNavAddComment = {},
        onLike = {},
        onReply = { _, _ -> },
        onDelete = {},
        onLikeComment = { _, _ -> }
    )
}