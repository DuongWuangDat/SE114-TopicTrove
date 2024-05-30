package com.topic_trove.ui.modules.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.topic_trove.data.repositories.AuthRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharePreferenceProvider: SharePreferenceProvider,
    private val repository: AuthRepository
) : ViewModel() {

    fun refresh(
        onLogin: () -> Unit,
        onCommunity: () -> Unit,
    ) {
        val refreshToken =
            sharePreferenceProvider.get<String>(SharePreferenceProvider.REFRESH_TOKEN)
        if (refreshToken.isNullOrEmpty()) {
            onLogin()
        } else {
            runBlocking{
                repository.refresh()
                    .onSuccess {
                        sharePreferenceProvider.save<String>(
                            SharePreferenceProvider.ACCESS_TOKEN,
                            it.accessToken
                        )
                        onCommunity()
                    }.onFailure {
                        println("Error" + it.message)
                        onLogin()
                    }
            }
           // onCommunity()

        }
    }
}