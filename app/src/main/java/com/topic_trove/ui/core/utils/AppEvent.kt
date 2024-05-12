package com.topic_trove.ui.core.utils

sealed class AppEvent {
    data object LogOut : AppEvent()
}