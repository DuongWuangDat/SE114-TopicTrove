package com.topic_trove.ui.modules.communityscreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.topic_trove.data.model.Community
import com.topic_trove.data.model.Post
import com.topic_trove.data.sharepref.SharePreferenceProvider
import com.topic_trove.ui.core.utils.CheckRefreshToken
import com.topic_trove.ui.core.values.AppStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CommunityScreenVM @Inject constructor(
    private val sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel() {
    private var _postData = MutableStateFlow(Post())
    var postData: StateFlow<Post> = _postData.asStateFlow()
    val base_url = AppStrings.BASE_URL
    var isLoading = mutableStateOf(false)
    var snackbarHostState = SnackbarHostState()
    var postList = mutableStateListOf<Post>()
    var isShowDialog = mutableStateOf(false)
    var curPostId = mutableStateOf("")
    private var _communityData = MutableStateFlow(Community())
    var community: StateFlow<Community> = _communityData.asStateFlow()
    var isJoined = mutableStateOf(false)
    var IdUser = sharePreferenceProvider.getUserId()
    var refreshToken =
        sharePreferenceProvider.getRefreshToken()
    var accessToken = sharePreferenceProvider.getAccessToken()
    var isEnable = mutableStateOf(false)
        private set


    fun inputContent(it: String) {
        viewModelScope.launch {
            _postData.value.content = it
        }

    }

    fun inputImage(it: String) {
        viewModelScope.launch {
            _postData.value.imageUrl = it
        }
    }

    fun inputTitle(it: String) {
        viewModelScope.launch {
            _postData.value.title = it
        }
    }

    fun checkIsEnable() {
        isEnable.value = postData.value.content.isNotBlank() && postData.value.title.isNotBlank()
    }

    fun createPostApi(navController: NavController, communityId: String, userId: String) {
        viewModelScope.launch {
            isLoading.value = true
            var isRetry :Boolean = true
            //val accessToken = CheckRefreshToken(
            //    refreshToken,
            //    navController
            //)
            val json = if (_postData.value.imageUrl == "")
                """
                        {
                            "author": "$userId",
                            "communityId": "$communityId",
                            "title": "${_postData.value.title.replace("\n", "\\n")}",
                            "content": [
                                {
                                    "body": "${_postData.value.content.replace("\n", "\\n")}",
                                    "type": "text"
                                }
                            ]
                        }""".trimIndent()
            else
                """
                        {
                            "author": "$userId",
                            "communityId": "$communityId",
                            "title": "${_postData.value.title.replace("\n", "\\n")}",
                            "content": [
                                {
                                    "body": "${_postData.value.content.replace("\n", "\\n")}",
                                    "type": "text"
                                },
                                {
                                    "body": "${_postData.value.imageUrl}",
                                    "type": "image"
                                }
                            ]
                        }""".trimIndent()
                while(isRetry){
                    if (accessToken != null && accessToken != "") {
                        Fuel.post("$base_url/post/create")
                            .timeout(Int.MAX_VALUE)
                            .timeoutRead(Int.MAX_VALUE)
                            .header("Content-Type" to "application/json")
                            .authentication()
                            .bearer(accessToken!!)
                            .jsonBody(json)
                            .awaitStringResponseResult()
                            .let {(_,response, result) ->
                                when(response.statusCode){
                                    200->result.fold(
                                        { d ->
                                            println(d)

                                            GlobalScope.launch {
                                                snackbarHostState.showSnackbar("Create post successfully")
                                            }
                                            isLoading.value = false
                                            isRetry = false
                                        },
                                        { err ->
                                                isLoading.value = false
                                                isRetry = false
                                                GlobalScope.launch {
                                                    snackbarHostState.showSnackbar("Something went wrong")
                                                }
                                        })
                                    401->{
                                        accessToken =
                                            refreshToken?.let { CheckRefreshToken(it,navController) }
                                        sharePreferenceProvider.saveAccessToken(accessToken!!)
                                        println(accessToken)
                                        isRetry=true
                                    }
                                    else->{
                                        println(response)
                                        isRetry=false
                                    }
                                }

                            }
                    }
                    else{
                        isLoading.value = false
                        isRetry=false
                        GlobalScope.launch {
                            snackbarHostState.showSnackbar("Something went wrong")
                        }
                    }

                }

        }

    }

    fun uploadImgApi(file: File) {
        viewModelScope.launch {

            isLoading.value = true
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
                            isLoading.value = false
                            inputImage(imageUrl)
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Upload image successfully")
                            }


                        },
                        { err ->
                            isLoading.value = false
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Something went wrong")
                            }

                        }
                    )
                }

        }

    }

    fun getPostList(communityId: String, userId: String, navController: NavController) {
        viewModelScope.launch {
            postList.clear()
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            var isRetry : Boolean = true

            while(isRetry){
                if (accessToken != null && accessToken != "") {
                    Fuel.get("$base_url/post/findbycommunityid?communityId=$communityId")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .awaitStringResponseResult()
                        .let { (_,response,result) ->
                            when(response.statusCode){
                                200-> result.fold(
                                    { d ->
                                        val response = JSONObject(d)
                                        val arrayPost = response.getJSONArray("data")
                                        println(arrayPost)

                                        for (i in 0 until arrayPost.length()) {

                                            val item = arrayPost.getJSONObject(i)
                                            val userLikeList = item.getJSONArray("interestUserList")
                                            val listUser =
                                                List(userLikeList.length()) { userLikeList.getString(it) }
                                            var isLike = false
                                            if (listUser.contains(userId)) {
                                                isLike = true
                                            }

                                            val content = item.getJSONArray("content")
                                            val authorName = item.getJSONObject("author").getString("username")
                                            val contentText =
                                                content.getJSONObject(0).getString("body").replace("\\n", "\n")
                                            var imageUrl = ""
                                            if (content.length() >= 2) {
                                                imageUrl = content.getJSONObject(1).getString("body")
                                            }
                                            val post = Post(
                                                id = item.getString("_id"),
                                                authorID = item.getJSONObject("author").getString("_id"),
                                                authorName = item.getJSONObject("author").getString("username"),
                                                avatar = item.getJSONObject("author").getString("avatar"),
                                                communityID = item.getJSONObject("communityId")
                                                    .getString("_id"),
                                                communityName = item.getJSONObject("communityId")
                                                    .getString("communityName"),
                                                content = contentText,
                                                imageUrl = imageUrl,
                                                createdAt = formatter.parse(item.getString("createdAt")),
                                                interestCount = item.getInt("interestCount"),
                                                title = item.getString("title"),
                                                isLike = isLike,
                                                commentCount = item.getInt("commentCount")

                                            )
                                            postList.add(post)

                                        }
                                        isRetry = false
                                    },
                                    { err ->
                                            isRetry = false
                                            GlobalScope.launch {
                                                snackbarHostState.showSnackbar("Something went wrong")
                                            }
                                    })
                                401-> {
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it,navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    println(accessToken)
                                    isRetry=true
                                }
                                else->{
                                    println(response)
                                    isRetry=false
                                }
                            }

                        }
                }
                else{
                    isRetry=false
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }

        }
    }

    fun deletePost(id: String, navController: NavController) {
        viewModelScope.launch {
            isLoading.value = true
            println(accessToken)
            var isRetry : Boolean = true
            while (isRetry){
                if (accessToken != null && accessToken != "") {
                    Fuel.delete("$base_url/post/delete/$id")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .authentication()
                        .bearer(accessToken!!)
                        .awaitStringResponseResult().let { (_,response,result) ->
                            when(response.statusCode){
                                200->
                                    result.fold({ d ->
                                        postList.removeIf { x -> x.id == id }
                                        isLoading.value = false
                                        GlobalScope.launch {
                                            snackbarHostState.showSnackbar("Delete successffuly")
                                        }
                                        isRetry=false

                                    },
                                    { err ->
                                            isRetry =false
                                            GlobalScope.launch {
                                                snackbarHostState.showSnackbar("Something went wrong")
                                            }

                                    })
                                401-> {
                                    accessToken=
                                        refreshToken?.let { CheckRefreshToken(it,navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetry=true
                                }

                                else -> {
                                    GlobalScope.launch {
                                        snackbarHostState.showSnackbar("Something went wrong")
                                    }
                                    isRetry=false
                                }
                            }

                        }
                }
                else{
                    isRetry=false
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }

        }
    }

    fun likePost(id: String, userId: String, isLike: Boolean, navController: NavController) {
        viewModelScope.launch {

            FuelManager.instance.forceMethods = true
            var isRetry : Boolean = true
            val interest = if (!isLike) 1 else -1
            val json = """
                {
                    "userId": "$userId",
                    "interest": "$interest"
                }
            """.trimIndent()
            while(isRetry){
                if (accessToken != null && accessToken != "") {
                    Fuel.patch("$base_url/post/likepost/$id")
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(json)
                        .awaitStringResponseResult().let { (_, response, result) ->
                            when (response.statusCode){
                                200->  result.fold(

                                    { d ->
                                        println(d)
                                        postList.find { it.id === id }?.isLike = !isLike
                                        isRetry=false
                                    },
                                    { err ->
                                            isRetry=false
                                            GlobalScope.launch {
                                                snackbarHostState.showSnackbar("Something went wrong")
                                            }
                                    })
                                401-> {
                                    accessToken=
                                        refreshToken?.let { CheckRefreshToken(it,navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetry=true
                                }
                                else->{
                                    println(response)
                                    isRetry=false
                                }
                            }

                        }
                }
                else{
                    isRetry=false
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }


        }
    }

    fun getCommunityByID(communityId: String, navController: NavController) {
        viewModelScope.launch {
            var isRetry : Boolean = true
            while(isRetry){
                if (accessToken != null && accessToken != "") {
                    Fuel.get("$base_url/community/findbyid/$communityId")
                        .authentication()
                        .bearer(accessToken!!)
                        .awaitStringResponseResult().let { (_, response, result) ->
                            println("Statuscode:"+response.statusCode)
                            when(response.statusCode){
                                200->result.fold(
                                    { d ->
                                        val response = JSONObject(d)
                                        val data = response.getJSONObject("data")
                                        _communityData.update { it ->
                                            it.copy(
                                                id = data.getString("_id"),
                                                owner = data.getJSONObject("owner").getString("_id"),
                                                icon = data.getString("icon"),
                                                description = data.getString("description"),
                                                rules = data.getString("rules"),
                                                communityName = data.getString("communityName"),
                                                memberCount = data.getInt("memberCount"),
                                            )
                                        }
                                        isRetry=false
                                        println(community)
                                    },
                                    { err ->
                                            isRetry= false
                                            GlobalScope.launch {
                                                snackbarHostState.showSnackbar("Something went wrong")
                                            }
                                    })
                                401->{
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it,navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    println(accessToken)
                                    println(sharePreferenceProvider.getAccessToken())
                                    isRetry=true
                                }
                                else->{
                                    println(response)
                                    result.fold({d->}, {err-> println(err) })
                                    isRetry=false
                                }
                            }

                        }
                }
                else{
                    println(accessToken)
                    isRetry=false
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }

        }
    }

    fun CheckIsJoined(communityId: String, navController: NavController, userId: String) {
        viewModelScope.launch {
            var isRetry : Boolean =true
            var json = """
                {
                    "userId": "$userId"
                }
            """.trimIndent()
            while(isRetry){
                if (accessToken != null && accessToken != "") {
                    Fuel.post("$base_url/community/checkisjoin/$communityId")
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(json)
                        .awaitStringResponseResult().let { (_,response,result) ->
                            when(response.statusCode){
                                200->result.fold(
                                    { d ->
                                        var data = JSONObject(d)
                                        isJoined.value = data.getBoolean("result")
                                        println(isJoined.value)
                                        isRetry=false
                                    },
                                    { err ->
                                            isRetry= false
                                            GlobalScope.launch {
                                                snackbarHostState.showSnackbar("Something went wrong")
                                            }
                                        })
                                401->{
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it,navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetry=true
                                }
                                else->{
                                    println(response)
                                    isRetry=false
                                }
                            }

                        }
                }
                else{
                    isRetry= false
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }

        }
    }

    fun JoinCommunity(communityId: String, navController: NavController, userId: String) {
        viewModelScope.launch {
            println(isJoined)
            var isRetry: Boolean = true
            val code = if (isJoined.value) 1 else -1
            val json = """
                {
                    "userId": "$userId",
                    "communityId":"$communityId",
                    "code": $code
                }
            """.trimIndent()
            while(isRetry){
                if (accessToken != null && accessToken != "") {
                    Fuel.post("$base_url/user/joincommunity")
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(json)
                        .awaitStringResponseResult().let { (_, response, result) ->
                            when(response.statusCode){
                                200->result.fold(
                                    { d ->
                                        _communityData.update { it ->
                                            it.copy(
                                                memberCount = if (isJoined.value) it.memberCount + 1 else it.memberCount - 1
                                            )
                                        }
                                        isRetry=false
                                    },
                                    { err ->
                                        if(response.statusCode==401){
                                            accessToken= refreshToken?.let { CheckRefreshToken(it,navController) }
                                            sharePreferenceProvider.saveAccessToken(accessToken!!)
                                            isRetry=true
                                        }
                                        else{
                                            isRetry=false
                                            GlobalScope.launch(){
                                                snackbarHostState.showSnackbar("Something went wrong")
                                            }
                                        }
                                    })
                                401->{
                                    accessToken=
                                        refreshToken?.let { CheckRefreshToken(it,navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetry=true
                                }
                                else->{
                                    println(response)
                                    isRetry=false
                                }
                            }


                        }
                }
                else{
                    isRetry=false
                    GlobalScope.launch(){
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }


        }
    }
}

