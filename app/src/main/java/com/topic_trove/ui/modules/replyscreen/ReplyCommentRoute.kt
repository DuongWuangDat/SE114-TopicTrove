package com.topic_trove.ui.modules.replyscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.topic_trove.ui.modules.addcommentscreen.AddCommentViewModel

@Composable
fun ReplyCommentRoute(
    viewmodel: AddCommentViewModel = hiltViewModel(),
    postId: String,
    parentComment: String,
    content: String,
    onNavUp: () -> Unit,
) {
    ReplyScreen(
        content = content,
        comment = { comment ->
            viewmodel.createComment(
                postId = postId,
                parentComment = parentComment,
                comment = comment,
            ) {
                onNavUp()
            }
        },
        onNavUp = onNavUp,
    )
}
