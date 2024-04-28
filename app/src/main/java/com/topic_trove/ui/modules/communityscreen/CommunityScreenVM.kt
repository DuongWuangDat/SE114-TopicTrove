package com.topic_trove.ui.modules.communityscreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.utils.CheckRefreshToken
import com.topic_trove.ui.core.values.AppStrings
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale
import java.util.logging.SimpleFormatter

class CommunityScreenVM : ViewModel() {
    private var _postData =  MutableStateFlow(Post())
    var postData : StateFlow<Post> = _postData.asStateFlow()
    val base_url = AppStrings.BASE_URL
    var isLoading = mutableStateOf(false)
    var snackbarHostState = SnackbarHostState()
    var postList = mutableStateListOf<Post>()
    var isShowDialog = mutableStateOf(false)
    var curPostId = mutableStateOf("")
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
            val accessToken = CheckRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NjFkZWQ2MzlhOWVjYzRjMjUyNTc3NGQiLCJ0eXBlIjoicmVmcmVzaCIsImlhdCI6MTcxMzYwNTAxNSwiZXhwIjoxNzE2MTk3MDE1fQ.OYasn0W85JmIRWeOiTl69Br3z7l6lZDglRaz94dnbQU")
            val json = if(_postData.value.imageUrl == "")
                """
                        {
                            "author": "661ded639a9ecc4c2525774d",
                            "communityId": "662385ad314b50e0397a3a90",
                            "title": "${_postData.value.title.replace("\n","\\n")}",
                            "content": [
                                {
                                    "body": "${_postData.value.content.replace("\n","\\n")}",
                                    "type": "text"
                                }
                            ]
                        }""".trimIndent()
            else
                """
                        {
                            "author": "661ded639a9ecc4c2525774d",
                            "communityId": "662385ad314b50e0397a3a90",
                            "title": "${_postData.value.title.replace("\n","\\n")}",
                            "content": [
                                {
                                    "body": "${_postData.value.content.replace("\n","\\n")}",
                                    "type": "text"
                                },
                                {
                                    "body": "${_postData.value.imageUrl}",
                                    "type": "image"
                                }
                            ]
                        }""".trimIndent()
            Fuel.post("$base_url/post/create")
                .timeout(Int.MAX_VALUE)
                .timeoutRead(Int.MAX_VALUE)
                .header("Content-Type" to "application/json")
                .authentication()
                .bearer(accessToken)
                .jsonBody(json)
                .responseString (){ result ->
                    result.fold(
                        {d-> println(d)

                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Create post successfully")
                            }
                            isLoading.value= false

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
                .timeout(Int.MAX_VALUE)
                .timeoutRead(Int.MAX_VALUE)
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

    fun getPostList(communityId : String, userId: String){
        viewModelScope.launch {
            postList.clear()
            var formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val accessToken = CheckRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NjFkZWQ2MzlhOWVjYzRjMjUyNTc3NGQiLCJ0eXBlIjoicmVmcmVzaCIsImlhdCI6MTcxMzYwNTAxNSwiZXhwIjoxNzE2MTk3MDE1fQ.OYasn0W85JmIRWeOiTl69Br3z7l6lZDglRaz94dnbQU")

            Fuel.get("$base_url/post/findbycommunityid?communityId=$communityId")
                .timeout(Int.MAX_VALUE)
                .timeoutRead(Int.MAX_VALUE)
                .header("Content-Type" to "application/json")
                .authentication()
                .bearer(accessToken)
                .responseString(){ result ->
                    result.fold(
                        {d->
                            val response = JSONObject(d)
                            val arrayPost = response.getJSONArray("data")
                            println(arrayPost)

                            for(i in 0 until arrayPost.length()){

                                val item = arrayPost.getJSONObject(i)
                                val userLikeList = item.getJSONArray("interestUserList")
                                val listUser = List(userLikeList.length()){userLikeList.getString(it)}
                                var isLike = false
                                if(listUser.contains(userId)){
                                    isLike = true
                                }

                                val content = item.getJSONArray("content")
                                val authorName = item.getJSONObject("author").getString("username")
                                val contentText = content.getJSONObject(0).getString("body").replace("\\n","\n")
                                var imageUrl = ""
                                if(content.length() >=2){
                                    imageUrl = content.getJSONObject(1).getString("body")
                                }
                                var post = Post(
                                    id = item.getString("_id"),
                                    authorID = item.getJSONObject("author").getString("_id"),
                                    authorName = item.getJSONObject("author").getString("username"),
                                    avatar = item.getJSONObject("author").getString("avatar"),
                                    communityID = item.getJSONObject("communityId").getString("_id"),
                                    communityName = item.getJSONObject("communityId").getString("communityName"),
                                    content= contentText,
                                    imageUrl = imageUrl,
                                    createdAt = formatter.parse(item.getString("createdAt")),
                                    interestCount = item.getInt("interestCount"),
                                    title = item.getString("title"),
                                    isLike = isLike,
                                    commentCount = item.getInt("commentCount")

                                )
                                postList.add(post)
                            }
                        },
                        {err-> println(err) }
                    )
                }
        }
    }

    fun deletePost(id: String){
        viewModelScope.launch {
            isLoading.value= true
            val accessToken = CheckRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NjFkZWQ2MzlhOWVjYzRjMjUyNTc3NGQiLCJ0eXBlIjoicmVmcmVzaCIsImlhdCI6MTcxMzYwNTAxNSwiZXhwIjoxNzE2MTk3MDE1fQ.OYasn0W85JmIRWeOiTl69Br3z7l6lZDglRaz94dnbQU")
            Fuel.delete("$base_url/post/delete/$id")
                .timeout(Int.MAX_VALUE)
                .timeoutRead(Int.MAX_VALUE)
                .authentication()
                .bearer(accessToken)
                .responseString(){ result ->
                    result.fold(
                        {d->
                            postList.removeIf{x->x.id == id}
                            isLoading.value= false
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Delete successffuly")
                            }

                        },
                        {err->
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Something went wrong")
                            }
                        }
                    )
                }
        }
    }

    fun likePost(id: String, userId: String, isLike: Boolean){
        viewModelScope.launch {
            FuelManager.instance.forceMethods= true
            val accessToken = CheckRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NjFkZWQ2MzlhOWVjYzRjMjUyNTc3NGQiLCJ0eXBlIjoicmVmcmVzaCIsImlhdCI6MTcxMzYwNTAxNSwiZXhwIjoxNzE2MTk3MDE1fQ.OYasn0W85JmIRWeOiTl69Br3z7l6lZDglRaz94dnbQU")
            val interest = if(!isLike) 1 else -1
            val json = """
                {
                    "userId": "$userId",
                    "interest": "$interest"
                }
            """.trimIndent()
            Fuel.patch("$base_url/post/likepost/$id")
                .header("Content-Type" to "application/json")
                .authentication()
                .bearer(accessToken)
                .jsonBody(json)
                .responseString(){_,response,result ->
                    result.fold(
                        {d-> println(d) },
                        {err-> println(response) }
                    )
                }

        }
    }
}

