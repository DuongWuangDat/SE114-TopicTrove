package com.topic_trove.ui.modules.addcommentscreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddCommentRoute(
    viewmodel: AddCommentViewModel = hiltViewModel(),
    postId: String,
    onNavUp: () -> Unit,
    onSuccess: () -> Unit,
) {

    AddCommentScreen(
        comment = { comment ->
            viewmodel.createComment(
                postId = postId,
                parentComment = null,
                comment = comment,
            ) {
                onSuccess()
            }
        },
        onNavUp = onNavUp,
    )
}
