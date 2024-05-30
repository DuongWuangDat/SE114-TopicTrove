package com.topic_trove.ui.modules.postdetailscreen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.model.CommentResponse
import com.topic_trove.data.model.LikeRequest
import com.topic_trove.data.model.Post
import com.topic_trove.data.repositories.PostRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val repository: PostRepository,
    sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel() {

    // save when login
    private val idUserSharePreference =
        sharePreferenceProvider.get<String>(SharePreferenceProvider.USER_ID)
    private val idUser =
        if (idUserSharePreference.isNullOrBlank()) "661ded639a9ecc4c2525774d" else idUserSharePreference

    private val _postDetailUiState = MutableStateFlow(PostDetailUiState())
    val postDetailUiState: StateFlow<PostDetailUiState> = _postDetailUiState.asStateFlow()

    var snackBarHostState = SnackbarHostState()

    fun likePost(
        authorId: String,
        postId: String,
        interest: Int,
    ) {
        viewModelScope.launch {
            repository.likePost(
                id = postId,
                likeRequest = LikeRequest(authorId, interest),
            ).onSuccess {
                _postDetailUiState.update { uiState ->
                    uiState.copy(
                        post = uiState.post.copy(
                            interestCount = uiState.post.interestCount + interest,
                            isLike = interest == 1
                        )
                    )
                }
            }.onFailure {
                snackBarHostState.showSnackbar("Like post fail with message ${it.message}")
            }
        }
    }

    fun getPostById(id: String) {
        viewModelScope.launch {
            repository.getPostById(id).onSuccess { response ->
                val isCommunityOwner = response.communityId?.id == idUser
                val isPostOwner = (response.author?.id == idUser)
                _postDetailUiState.update { uiState ->
                    uiState.copy(
                        owner = isPostOwner,
                        isCommunityOwner = isCommunityOwner,
                        post = Post(
                            id = response.id,
                            authorID = response.author?.id ?: "",
                            authorName = response.author?.username ?: "",
                            communityID = response.communityId?.id ?: "",
                            communityName = response.communityId?.communityName ?: "",
                            content = response.content?.get(0)?.body ?: "",
                            title = response.title ?: "",
                            imageUrl = if(response.content?.size!! >1) response.content?.get(1)?.body ?: "" else "",
                            avatar = response.author?.avatar ?: "",
                            createdAt = response.createdAt ?: Date(),
                            interestCount = response.interestCount ?: 0,
                            isLike = response.interestUserList?.contains(idUser) ?: false,
                            commentCount = response.commentCount ?: 0,
                        ),
                    )
                }
            }.onFailure {
                // TODO handle error
            }
        }
    }

    fun getCommentByPostId(id: String) {
        viewModelScope.launch {
            repository.getCommentByPostId(id).onSuccess { response ->
                _postDetailUiState.update { uiState ->
                    uiState.copy(
                        comments = mapData(response),
                    )
                }
            }.onFailure {
                // TODO handle error
            }
        }
    }

    private fun mapData(list: List<CommentResponse>): List<Comment> {
        return list.map {
            Comment(
                id = it.comment?.id ?: "",
                authorName = it.comment?.author?.username ?: "",
                content = it.comment?.content ?: "",
                createdAt = it.comment?.createdAt ?: Date(),
                avatar = it.comment?.author?.avatar ?: "",
                interestCount = it.comment?.interestCount ?: 0,
                liked = it.comment?.interestUserList?.contains(idUser) ?: false,
                owner = (it.comment?.author?.id == idUser),
                replies = mapData(it.children ?: listOf())
            )
        }
    }

    fun deleteComment(deleteId: String) {
        viewModelScope.launch {
            repository.deleteComment(deleteId).onSuccess {
                getCommentByPostId(postDetailUiState.value.post.id)
                snackBarHostState.showSnackbar("Delete comment successfully")
            }.onFailure {
                snackBarHostState.showSnackbar("Delete comment fail")
            }
        }
    }

    fun likeComment(
        authorId: String,
        commentId: String,
        interest: Int,
    ) {
        viewModelScope.launch {
            repository.likeComment(
                id = commentId,
                likeRequest = LikeRequest(authorId, interest),
            ).onSuccess {
                getCommentByPostId(postDetailUiState.value.post.id)
            }.onFailure {
                snackBarHostState.showSnackbar("Like comment fail with message ${it.message}")
            }
        }
    }
}