package com.topic_trove.ui.modules.communityscreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.AppStrings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.File

class CommunityScreenVM : ViewModel() {
    private var _postData =  MutableStateFlow(Post())
    var postData : StateFlow<Post> = _postData.asStateFlow()
    val base_url = AppStrings.BASE_URL
    var isLoading = mutableStateOf(false)
    var snackbarHostState = SnackbarHostState()

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

    fun createPostApi(){
        viewModelScope.launch {
            isLoading.value= true
            val json = if(_postData.value.imageUrl == "")
                """
                        {
                            "author": "661ded639a9ecc4c2525774d",
                            "communityId": "662385ad314b50e0397a3a90",
                            "title": "${_postData.value.title}",
                            "content": [
                                {
                                    "body": "${_postData.value.content}",
                                    "type": "text"
                                }
                            ]
                        }""".trimIndent()
            else
                """
                        {
                            "author": "661ded639a9ecc4c2525774d",
                            "communityId": "662385ad314b50e0397a3a90",
                            "title": "${_postData.value.title}",
                            "content": [
                                {
                                    "body": "${_postData.value.content}",
                                    "type": "text"
                                },
                                {
                                    "body": "${_postData.value.imageUrl}",
                                    "type": "image"
                                }
                            ]
                        }""".trimIndent()
            Fuel.post("$base_url/post/create")
                .header("Content-Type" to "application/json")
                .authentication()
                .bearer("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NjFkZWQ2MzlhOWVjYzRjMjUyNTc3NGQiLCJ0eXBlIjoicmVmcmVzaCIsImlhdCI6MTcxMzYwNTAxNSwiZXhwIjoxNzE2MTk3MDE1fQ.OYasn0W85JmIRWeOiTl69Br3z7l6lZDglRaz94dnbQU")
                .jsonBody(json)
                .responseString (){ result ->
                    result.fold(
                        {d-> println(d)
                            isLoading.value= false
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Create post successfully")
                            }

                        },
                        {err ->
                            isLoading.value=false
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Something went wrong")
                            } }
                    )
                }

        }

    }

    fun uploadImgApi(file : File){
        viewModelScope.launch {
            isLoading.value= true
            // Tải lên hình ảnh
            Fuel.upload("https://topictrovebe.onrender.com/api/v1/upload/image")
                .add(FileDataPart(file, name = "image"))
                .responseString() { result ->
                    result.fold(
                        { d ->
                            val jsonObject = JSONObject(d)
                            val imageUrl = jsonObject.getString("image")
                            println("Image URL: $imageUrl")
                            isLoading.value=false
                            inputImage(imageUrl)
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Upload image successfully")
                            }


                        },
                        { err ->
                            isLoading.value=false
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Something went wrong")
                            }

                        }
                    )
                }

        }

    }

    fun getPostList(){
        viewModelScope.launch {

        }
    }
}

