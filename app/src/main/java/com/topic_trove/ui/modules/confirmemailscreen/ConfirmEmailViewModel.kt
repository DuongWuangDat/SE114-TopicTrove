package com.topic_trove.ui.modules.confirmemailscreen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.AuthRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import com.topic_trove.ui.core.utils.SavedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmEmailViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val savedUser: SavedUser,
    private val sharePreferenceProvider: SharePreferenceProvider
) : ViewModel() {

    var snackBarHostState = SnackbarHostState()

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
                println(it.accessToken)
                sharePreferenceProvider.saveAccessToken(it.accessToken)
                sharePreferenceProvider.saveRefreshToken(it.refreshToken)
                sharePreferenceProvider.saveUserId(it.data.id)
                sharePreferenceProvider.saveUser(it.data)
                registerSuccess()
                snackBarHostState.showSnackbar("Register successfully")
            }.onFailure { error ->
                snackBarHostState.showSnackbar("Register fail with message ${error.message}")
            }
        }
    }
}