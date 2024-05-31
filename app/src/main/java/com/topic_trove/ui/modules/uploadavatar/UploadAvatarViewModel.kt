package com.topic_trove.ui.modules.uploadavatar

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.AuthRepository
import com.topic_trove.ui.core.utils.SavedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class UploadAvatarViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val savedUser: SavedUser,
) : ViewModel() {

    var snackBarHostState = SnackbarHostState()
    fun uploadAvatar(image: File, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repository.uploadAvatar(image)
                .onSuccess {
                    savedUser.avatar = it.image ?: ""
                    onSuccess()
                }.onFailure { error ->
                    snackBarHostState.showSnackbar("${error.message}")
                }
        }
    }


}