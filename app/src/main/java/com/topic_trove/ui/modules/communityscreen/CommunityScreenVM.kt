package com.topic_trove.ui.modules.communityscreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.topic_trove.data.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CommunityScreenVM : ViewModel() {
    private var _postData =  MutableStateFlow(Post())
    var postData : StateFlow<Post> = _postData.asStateFlow()

    var isLoading = mutableStateOf(false)
    var isEnable = mutableStateOf(false)
        private set
    fun inputContent(it: String){
        viewModelScope.launch {
            _postData.value.content = it
        }

    }

    fun inputImage(it: String){
        viewModelScope.launch {
            _postData.value.imageUrl = it
        }
    }

    fun inputTitle(it: String){
        viewModelScope.launch {
            _postData.value.title=it
        }
    }

    fun checkIsEnable() {
        isEnable.value = postData.value.content.isNotBlank() && postData.value.title.isNotBlank()
    }
}

