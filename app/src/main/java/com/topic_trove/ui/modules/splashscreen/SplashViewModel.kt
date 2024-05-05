package com.topic_trove.ui.modules.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.RegisterRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: RegisterRepository,
    private val sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel() {

    fun refresh(
        onLogin: () -> Unit,
        onCommunity: () -> Unit,
    ) {
        viewModelScope.launch {
            authRepository.refresh()
                .onSuccess {
                    sharePreferenceProvider.save<String>(
                        SharePreferenceProvider.ACCESS_TOKEN,
                        it.accessToken
                    )
                    onCommunity()
                }
                .onFailure { onLogin() }
        }
    }
}