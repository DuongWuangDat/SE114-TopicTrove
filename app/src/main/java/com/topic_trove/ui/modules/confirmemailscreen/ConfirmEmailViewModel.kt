package com.topic_trove.ui.modules.confirmemailscreen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.RegisterRepository
import com.topic_trove.ui.core.utils.SavedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmEmailViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val savedUser: SavedUser,
) : ViewModel() {

    var snackBarHostState = SnackbarHostState()

    fun register(
        otpValue: String,
        registerSuccess: () -> Unit,
    ) {
        if (otpValue.lowercase() != savedUser.userCode.lowercase()) {
            viewModelScope.launch {
                snackBarHostState.showSnackbar("Invalid OTP")
            }
            return
        }
        viewModelScope.launch {
            repository.register(
                name = savedUser.username,
                phone = savedUser.phoneNumber,
                email = savedUser.email,
                password = savedUser.password,
                avatar = savedUser.avatar,
            ).onSuccess {
                registerSuccess()
            }.onFailure { error ->
                snackBarHostState.showSnackbar("${error.message}")
            }
        }
    }
}