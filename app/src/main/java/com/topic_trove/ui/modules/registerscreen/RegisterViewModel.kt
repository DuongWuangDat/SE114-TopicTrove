package com.topic_trove.ui.modules.registerscreen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.RegisterRepository
import com.topic_trove.ui.core.utils.SavedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val savedUser: SavedUser,
) : ViewModel() {

    var snackBarHostState = SnackbarHostState()

    fun sendEmail(
        name: String,
        phone: String,
        email: String,
        password: String,
        sendEmailSuccess: () -> Unit,
    ) {
        savedUser.username = name
        savedUser.email = email
        savedUser.password = password
        savedUser.phoneNumber = phone
        viewModelScope.launch {
            repository.sendEmail(email)
                .onSuccess { response ->
                    savedUser.userCode = response.userCode
                    sendEmailSuccess()
                }
                .onFailure { error ->
                    snackBarHostState.showSnackbar("Send email fail with message ${error.message}")
                }
        }
    }
}