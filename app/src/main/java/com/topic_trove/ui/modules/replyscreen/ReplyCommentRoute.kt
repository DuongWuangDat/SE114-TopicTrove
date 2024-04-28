package com.topic_trove.ui.modules.replyscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.topic_trove.ui.modules.addcommentscreen.AddCommentViewModel
import com.topic_trove.ui.modules.postdetailscreen.PostDetailUiState

@Composable
fun ReplyCommentRoute(
    viewmodel: AddCommentViewModel = hiltViewModel(),
    postDetail: PostDetailUiState,
    onNavUp: () -> Unit,
) {

    ReplyScreen(
        comment = { comment ->
//            viewmodel.createComment(
//                authorId = postDetail.post.authorID,
//                postId = postDetail.post.id,
//                parentComment = null,
//                comment = comment,
//            ) {
//                onNavUp()
//            }
            onNavUp
        },
        onNavUp = onNavUp,
    )
}
