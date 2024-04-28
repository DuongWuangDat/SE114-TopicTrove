package com.topic_trove.ui.modules.addcommentscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.model.CreateCommentRequest
import com.topic_trove.data.repositories.PostRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCommentViewModel @Inject constructor(
    private val repository: PostRepository,
    sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel() {

    // save when login
    private val idUser = sharePreferenceProvider.get<String>(SharePreferenceProvider.USER_ID)

    fun createComment(
        postId: String,
        parentComment: String?,
        comment: String,
        success: () -> Unit,
    ) {
        viewModelScope.launch {
            repository.createComment(
                CreateCommentRequest(
                    author = if (idUser.isNullOrBlank()) "661ded639a9ecc4c2525774d" else idUser,
                    post = postId,
                    content = comment,
                    parentComment = parentComment,
                )
            ).onSuccess {
                success()
            }.onFailure {
                // TODO handle error
            }
        }
    }
}