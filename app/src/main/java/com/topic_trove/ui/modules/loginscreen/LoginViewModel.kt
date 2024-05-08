package com.topic_trove.ui.modules.loginscreen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.model.User
import com.topic_trove.data.repositories.RegisterRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: RegisterRepository,
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
                sharePreferenceProvider.save<String>(
                    SharePreferenceProvider.ACCESS_TOKEN,
                    it.accessToken
                )
                sharePreferenceProvider.save<String>(
                    SharePreferenceProvider.USER_ID,
                    it.data.id
                )
                sharePreferenceProvider.saveRefreshToken(it.refreshToken)
                sharePreferenceProvider.save<User>(
                    SharePreferenceProvider.USER,
                    it.data
                )
                registerSuccess()
                snackBarHostState.showSnackbar("Login successfully")
            }.onFailure { error ->
                snackBarHostState.showSnackbar("Login fail with message ${error.message}")
            }
        }
    }
}