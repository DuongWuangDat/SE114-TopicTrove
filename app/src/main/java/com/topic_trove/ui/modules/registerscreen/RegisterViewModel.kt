package com.topic_trove.ui.modules.registerscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.RegisterRepository
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
) : ViewModel() {

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    fun register(
        name: String,
        phone: String,
        email: String,
        password: String,
        registerSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            repository.register(name, phone, email, password)
                .onSuccess {
                    registerSuccess()
                }
                .onFailure { error ->
                    _message.update { error.message ?: "" }
                }
        }
    }
}