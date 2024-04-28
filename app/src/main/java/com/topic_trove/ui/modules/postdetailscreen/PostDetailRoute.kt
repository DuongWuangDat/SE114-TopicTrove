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
    onReply: () -> Unit,
    onNavAddComment: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getPostById(postId)
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
        onReply = onReply,
    )
}
