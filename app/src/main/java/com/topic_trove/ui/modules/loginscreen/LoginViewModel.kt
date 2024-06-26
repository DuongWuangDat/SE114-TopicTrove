package com.topic_trove.ui.modules.loginscreen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.AuthRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel() {

    var snackBarHostState = SnackbarHostState()

    fun login(
        email: String,
        password: String,
        registerSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            authRepository.login(
                email = email,
                password = password,
            ).onSuccess {
                sharePreferenceProvider.saveAccessToken(it.accessToken)
                sharePreferenceProvider.saveUserId(it.data.id)
                sharePreferenceProvider.saveRefreshToken(it.refreshToken)
                sharePreferenceProvider.saveUser(it.data)
                registerSuccess()
                snackBarHostState.showSnackbar("Login successfully")
            }.onFailure {
                snackBarHostState.showSnackbar("Invalid account or password!")
            }
        }
    }
}