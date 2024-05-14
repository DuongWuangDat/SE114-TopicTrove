package com.topic_trove

import androidx.lifecycle.ViewModel
import com.topic_trove.ui.core.utils.AppEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableStateFlow<AppEvent>(AppEvent.None)
    val event = _event.asStateFlow()

    fun logOut() {
        _event.update { AppEvent.LogOut }
    }
}