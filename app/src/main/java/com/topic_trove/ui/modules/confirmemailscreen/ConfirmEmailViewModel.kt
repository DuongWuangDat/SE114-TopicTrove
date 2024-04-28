package com.topic_trove.ui.modules.confirmemailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.RegisterRepository
import com.topic_trove.ui.core.utils.SavedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmEmailViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val savedUser: SavedUser,
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    fun register(
        otpValue: String,
        registerSuccess: () -> Unit,
    ) {
        if (otpValue != savedUser.userCode) {
            return
        }
        viewModelScope.launch {
            repository.register(
                name = savedUser.username,
                phone = savedUser.phoneNumber,
                email = savedUser.email,
                password = savedUser.password,
            ).onSuccess {
                registerSuccess()
            }.onFailure { error ->
                _message.update { error.message ?: "" }
            }
        }
    }
}