package com.topic_trove.ui.modules.registerscreen

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
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val savedUser: SavedUser,
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

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
                    _message.update { error.message ?: "" }
                }
        }
    }
}