package com.topic_trove.ui.modules.profilescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.topic_trove.data.model.Post

class ProfileScreenVM : ViewModel() {
    var isLoading = mutableStateOf(false)
    var posts = mutableListOf<Post>()

    fun getPosts() {
        isLoading.value = true
        // Fetch user data
        isLoading.value = false
    }
}