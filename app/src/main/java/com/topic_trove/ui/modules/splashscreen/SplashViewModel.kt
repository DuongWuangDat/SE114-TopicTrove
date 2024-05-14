package com.topic_trove.ui.modules.splashscreen

import androidx.lifecycle.ViewModel
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharePreferenceProvider: SharePreferenceProvider,
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
            onCommunity()
        }
    }
}