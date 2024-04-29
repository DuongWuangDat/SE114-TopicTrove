package com.topic_trove.ui.modules.postdetailscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PostDetailRoute(
    viewModel: PostDetailViewModel = hiltViewModel(),
    postId: String,
    onNavUp: () -> Unit,
    onReply: (String, String) -> Unit,
    onNavAddComment: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getPostById(postId)
        viewModel.getCommentByPostId(postId)
    }
    val postDetailUiState: PostDetailUiState by viewModel.postDetailUiState.collectAsStateWithLifecycle()
    PostDetailScreen(
        postDetail = postDetailUiState,
        onNavUp = onNavUp,
        onNavAddComment = onNavAddComment,
        onLike = {
            viewModel.likePost(
                authorId = postDetailUiState.post.authorID,
                postId = postDetailUiState.post.id,
                interest = if (it) 1 else -1,
            )
        },
        onReply = { content, parentComment ->
            onReply(content, parentComment)
        },
        onDelete = { id ->
            viewModel.deleteComment(id)
        },
        onLikeComment = { isLiked, id ->
            viewModel.likeComment(
                authorId = postDetailUiState.post.authorID,
                interest = if (isLiked) 1 else -1,
                commentId = id,
            )
        }
    )
}
