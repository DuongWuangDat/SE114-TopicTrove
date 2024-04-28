package com.topic_trove.ui.modules.postdetailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.model.LikePostRequest
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

    fun likePost(
        authorId: String,
        postId: String,
        interest: Int,
    ) {
        viewModelScope.launch {
            repository.likePost(
                id = postId,
                likePostRequest = LikePostRequest(authorId, interest),
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
                // TODO handle error
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
                            imageUrl = "",
                            avatar = response.author?.avatar ?: "",
                            createdAt = response.createdAt ?: Date(),
                            interestCount = response.interestCount ?: 0,
                            isLike = response.interestUserList?.contains(idUser) ?: false,
                            commentCount = response.commentCount ?: 0,
                        ),
                        comments = listOf(),
                    )
                }
            }.onFailure {
                // TODO handle error
            }
        }
    }
}