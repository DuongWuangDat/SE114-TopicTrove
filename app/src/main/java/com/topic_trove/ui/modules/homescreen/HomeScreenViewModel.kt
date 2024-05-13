package com.topic_trove.ui.modules.homescreen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.topic_trove.data.model.Community
import com.topic_trove.data.model.Post
import com.topic_trove.data.model.User
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
class HomeScreenViewModel @Inject constructor(
    val sharePreferenceProvider: SharePreferenceProvider
) : ViewModel() {
    private var _postData = MutableStateFlow(Post())
    var postData: StateFlow<Post> = _postData.asStateFlow()
    private var _userData = MutableStateFlow(User("", "", "", "", ""))
    var userData: StateFlow<User> = _userData.asStateFlow()
    val base_url = AppStrings.BASE_URL
    var isLoading = mutableStateOf(false)
    var snackbarHostState = SnackbarHostState()
    var postList = mutableStateListOf<Post>()
    var isShowDialog = mutableStateOf(false)
    var curPostId = mutableStateOf("")
    private var _communityData = MutableStateFlow(Community())
    var community: StateFlow<Community> = _communityData.asStateFlow()
    private var _joinedCommunityData = MutableStateFlow(Community())
    var joinedCommunity: StateFlow<Community> = _joinedCommunityData.asStateFlow()
    var communityList = mutableStateListOf<Community>()
    var isJoined = mutableStateOf(false)
    var IdUser = sharePreferenceProvider.getUserId()
    var refreshToken =
        sharePreferenceProvider.getRefreshToken()
    var accessToken = sharePreferenceProvider.getAccessToken()
    var isEnable = mutableStateOf(false)
        private set

    fun inputCommunityName(it: String) {
        viewModelScope.launch {
            _communityData.value.communityName = it
        }
    }

    fun inputCommunityDescription(it: String) {
        viewModelScope.launch {
            _communityData.value.description = it
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

    fun checkIsEnable() {
        isEnable.value = community.value.description.isNotBlank() && community.value.communityName.isNotBlank()
    }


    fun inputImage(it: String) {
        viewModelScope.launch {
            _communityData.value.icon = it
        }
    }

    fun getPostList(userId: String, navController: NavController) {
        viewModelScope.launch {
            postList.clear()
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            var isRetry: Boolean = true

            while (isRetry) {
                if (accessToken != null || accessToken == "") {
                    Fuel.get("$base_url/post/findall")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .awaitStringResponseResult()
                        .let { (_, response, result) ->
                            when (response.statusCode) {
                                200 -> result.fold(
                                    { d ->
                                        val response = JSONObject(d)
                                        val arrayPost = response.getJSONArray("data")
                                        println(arrayPost)

                                        for (i in 0 until arrayPost.length()) {

                                            val item = arrayPost.getJSONObject(i)
                                            val userLikeList = item.getJSONArray("interestUserList")
                                            val listUser =
                                                List(userLikeList.length()) {
                                                    userLikeList.getString(
                                                        it
                                                    )
                                                }
                                            var isLike = false
                                            if (listUser.contains(userId)) {
                                                isLike = true
                                            }

                                            val content = item.getJSONArray("content")
                                            val authorName =
                                                item.getJSONObject("author").getString("username")
                                            val contentText =
                                                content.getJSONObject(0).getString("body")
                                                    .replace("\\n", "\n")
                                            var imageUrl = ""
                                            if (content.length() >= 2) {
                                                imageUrl =
                                                    content.getJSONObject(1).getString("body")
                                            }
                                            val post = Post(
                                                id = item.getString("_id"),
                                                authorID = item.getJSONObject("author")
                                                    .getString("_id"),
                                                authorName = item.getJSONObject("author")
                                                    .getString("username"),
                                                avatar = item.getJSONObject("author")
                                                    .getString("avatar"),
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
                                        postList.shuffle()
                                    },
                                    { err ->
                                        isRetry = false
                                        GlobalScope.launch {
                                            snackbarHostState.showSnackbar("Something went wrong")
                                        }
                                    })

                                401 -> {
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it, navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    println(accessToken)
                                    isRetry = true
                                }

                                else -> {
                                    println(response)
                                    isRetry = false
                                }
                            }

                        }
                } else {
                    isRetry = false
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
            var isRetry: Boolean = true
            while (isRetry) {
                if (accessToken != null || accessToken == "") {
                    Fuel.delete("$base_url/post/delete/$id")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .authentication()
                        .bearer(accessToken!!)
                        .awaitStringResponseResult().let { (_, response, result) ->
                            when (response.statusCode) {
                                200 ->
                                    result.fold({ d ->
                                        postList.removeIf { x -> x.id == id }
                                        isLoading.value = false
                                        GlobalScope.launch {
                                            snackbarHostState.showSnackbar("Delete successffuly")
                                        }
                                        isRetry = false

                                    },
                                        { err ->
                                            isRetry = false
                                            GlobalScope.launch {
                                                snackbarHostState.showSnackbar("Something went wrong")
                                            }

                                        })

                                401 -> {
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it, navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetry = true
                                }

                                else -> {
                                    GlobalScope.launch {
                                        snackbarHostState.showSnackbar("Something went wrong")
                                    }
                                    isRetry = false
                                }
                            }

                        }
                } else {
                    isRetry = false
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
            var isRetry: Boolean = true
            val interest = if (!isLike) 1 else -1
            val json = """
                {
                    "userId": "$userId",
                    "interest": "$interest"
                }
            """.trimIndent()
            while (isRetry) {
                if (accessToken != null || accessToken == "") {
                    Fuel.patch("$base_url/post/likepost/$id")
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(json)
                        .awaitStringResponseResult().let { (_, response, result) ->
                            when (response.statusCode) {
                                200 -> result.fold(

                                    { d ->
                                        println(d)
                                        postList.find { it.id === id }?.isLike = !isLike
                                        isRetry = false
                                    },
                                    { err ->
                                        isRetry = false
                                        GlobalScope.launch {
                                            snackbarHostState.showSnackbar("Something went wrong")
                                        }
                                    })

                                401 -> {
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it, navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetry = true
                                }

                                else -> {
                                    println(response)
                                    isRetry = false
                                }
                            }

                        }
                } else {
                    isRetry = false
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }


        }
    }

    fun getUserById(navController: NavController) {
        viewModelScope.launch {
            if (accessToken != null || accessToken == ""){
                Fuel.get("$base_url/user/findbyid/$IdUser")
                    .timeout(Int.MAX_VALUE)
                    .timeoutRead(Int.MAX_VALUE)
                    .header("Content-Type" to "application/json")
                    .authentication()
                    .bearer(accessToken!!)
                    .awaitStringResponseResult()
                    .let { (_, response, result) ->
                        when (response.statusCode) {
                            200 -> result.fold({
                                    d->
                                val data = JSONObject(d)
                                val response = data.getJSONObject("data")
                                val avatar = response.getString("avatar")
                                println(avatar)
                                val id = response.getString("_id")
                                val name =response.getString("username")
                                val email = response.getString("email")
                                val phone = response.getString("phoneNumber")
                                val user = User(
                                    username = name,
                                    email= email,
                                    phoneNumber=phone,
                                    avatar=avatar,
                                    id = id
                                )
                                _userData.value = user
                            },{
                                    err -> GlobalScope.launch {
                                snackbarHostState.showSnackbar("Something went wrong")
                            }
                            })
                            401 -> {
                                accessToken =
                                    refreshToken?.let { CheckRefreshToken(it, navController) }
                                sharePreferenceProvider.saveAccessToken(accessToken!!)
                                println(accessToken)
                            }

                            else -> {
                                println(response)
                            }
                        }
                    }
            }

        }
    }

    fun getAllJoinedCommunity(navController: NavController){
        viewModelScope.launch {
            communityList.clear()
            var isRetry: Boolean = true
            while (isRetry) {
                if (accessToken != null || accessToken == "") {
                    Fuel.get("$base_url/community/findjoinedcommunity")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .awaitStringResponseResult()
                        .let { (_, response, result) ->
                            when(response.statusCode){
                                200 -> result.fold(
                                    {
                                            d->  val response = JSONObject(d)
                                        val arrayCommunity = response.getJSONArray("data")
                                        println(arrayCommunity)

                                        for(i in 0 until arrayCommunity.length()){
                                            val item = arrayCommunity.getJSONObject(i)
                                            val community = Community(
                                                id = item.getString("_id"),
                                                owner = item.getString("owner"),
                                                icon = item.getString("icon"),
                                                description = item.getString("description"),
                                                rules = item.getString("rules"),
                                                communityName = item.getString("communityName"),
                                                memberCount = item.getInt("memberCount")
                                            )
                                            communityList.add(community)

                                        }
                                        isRetry = false
                                    },
                                    {
                                        err ->
                                        isRetry = false
                                        GlobalScope.launch {
                                            snackbarHostState.showSnackbar("Something went wrong")
                                        }
                                    }
                                )
                                401 -> {
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it, navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    println(accessToken)
                                    isRetry = true
                                }

                                else -> {
                                    println(response)
                                    isRetry = false
                                }
                            }

                        }
                        } else {
                            isRetry = false
                            GlobalScope.launch {
                                snackbarHostState.showSnackbar("Something went wrong")
                            }
                        }
            }
        }
    }

    fun createCommunity(navController: NavController) {
        viewModelScope.launch {
            isLoading.value = true
            var isRetry: Boolean = true
            val json =
                """
                {
                    "owner": "$IdUser",
                    "icon": "${_communityData.value.icon}",
                    "description": "${_communityData.value.description}",
                    "rules": "${_communityData.value.rules}",
                    "communityName": "${_communityData.value.communityName}"
                }
                """.trimIndent()

            while (isRetry) {
                if (accessToken != null || accessToken != "") {
                    Fuel.post("$base_url/community/create")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(json)
                        .awaitStringResponseResult()
                        .let { (_, response, result) ->
                            when (response.statusCode) {
                                200 -> result.fold(
                                    {
                                            d -> println(d)
                                        GlobalScope.launch {
                                            snackbarHostState.showSnackbar("Create community successfully")
                                        }
                                        isLoading.value = false
                                        isRetry = false
                                    },
                                    {
                                        err ->
                                        isLoading.value = false
                                        isRetry = false
                                        GlobalScope.launch {
                                            snackbarHostState.showSnackbar("Something went wrong")
                                        }
                                    }
                                )
                                401 -> {
                                    accessToken = refreshToken?.let { CheckRefreshToken(it, navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    println(accessToken)
                                    isRetry = true
                                }
                                else -> {
                                    println(response)
                                    isLoading.value = false
                                    GlobalScope.launch {
                                        snackbarHostState.showSnackbar("Something went wrong")
                                    }
                                    isRetry = false
                                }
                            }
                        }
                } else {
                    isLoading.value = false
                    isRetry = false
                    GlobalScope.launch {
                        snackbarHostState.showSnackbar("Something went wrong")
                    }
                }
            }
        }
    }

}

