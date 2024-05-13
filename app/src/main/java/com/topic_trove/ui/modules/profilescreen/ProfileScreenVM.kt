package com.topic_trove.ui.modules.profilescreen

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FileDataPart
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.topic_trove.data.model.Post
import com.topic_trove.data.model.User
import com.topic_trove.data.sharepref.SharePreferenceProvider
import com.topic_trove.ui.core.utils.CheckRefreshToken
import com.topic_trove.ui.core.utils.baseUrl
import com.topic_trove.ui.core.values.AppStrings
import com.topic_trove.ui.core.values.AppStrings.Companion.BASE_URL
import com.topic_trove.ui.routes.AppRoutes
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class ProfileScreenVM @Inject constructor(
    private val sharePreferenceProvider: SharePreferenceProvider,
) : ViewModel() {
    private val baseUrl = AppStrings.BASE_URL
    val userId = sharePreferenceProvider.getUserId()
    var isLoading = mutableStateOf(false)
    var isEditing = mutableStateOf(false)
    var posts = mutableListOf<Post>()
    var curPostId = mutableStateOf("")
    var isShowDialog = mutableStateOf(false)
    var snackBarHostState = SnackbarHostState()

    private var refreshToken = sharePreferenceProvider.getRefreshToken()
    var accessToken = sharePreferenceProvider.getAccessToken()

    var actionState = mutableStateOf(false)
    var user = mutableStateOf(User())
    private var password = ""
    private var confirmPassword = ""
    var imageLocalUri = mutableStateOf(Uri.EMPTY)
    fun inputDisplayName(it: String) {
        viewModelScope.launch {
            user.value.username = it
        }
    }

    fun inputImage(it: String) {
        viewModelScope.launch {
            user.value.avatar = it
        }
    }

    fun inputEmail(it: String) {
        viewModelScope.launch {
            user.value.email = it
        }
    }

    fun inputPhoneNumber(it: String) {
        viewModelScope.launch {
            user.value.phoneNumber = it
        }
    }

    fun inputPassword(it: String) {
        viewModelScope.launch {
            password = it
        }
    }

    fun inputConfirmPassword(it: String) {
        viewModelScope.launch {
            confirmPassword = it
        }
    }

    fun checkIsEnable() {
        viewModelScope.launch {
            actionState.value =
                (imageLocalUri.value.toString()
                    .isNotEmpty() || user.value.username.isNotEmpty() || user.value.email.isNotEmpty() || user.value.phoneNumber.isNotEmpty()
                        || (password.isNotEmpty() && password == confirmPassword))
        }
    }

    fun updateUser(navController: NavController) {
        viewModelScope.launch {
            isEditing.value = true
            var isRetryChangePassword: Boolean = true

            while (isRetryChangePassword) {
                if (password.isNotEmpty())
                    Fuel.patch("${baseUrl}/user/changepassword")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(
                            """
                    {
                        "email": "${user.value.email}",
                        "password": "$password"
                    }
                """.trimIndent()
                        ).awaitStringResponseResult().let { (_, response, result) ->
                            when (response.statusCode) {
                                200 ->
                                    result.fold({ d ->
                                        println(d)
                                        GlobalScope.launch {
                                            snackBarHostState.showSnackbar("Change password successfully")
                                        }
                                        isRetryChangePassword = false;
                                    }, { err ->
                                        println(err)
                                        GlobalScope.launch {
                                            snackBarHostState.showSnackbar("Error when change password")
                                        }
                                        isRetryChangePassword = false;
                                    })

                                401 -> {
                                    accessToken =
                                        refreshToken?.let { CheckRefreshToken(it, navController) }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetryChangePassword = true
                                }

                                else -> {
                                    println(response)
                                    isRetryChangePassword = false
                                }
                            }
                        }

                if (actionState.value && password.isEmpty())
                    Fuel.patch("${baseUrl}/user/update/${user.value.id}")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(
                            """
                    {
                    ${
                                if (user.value.username.isNotEmpty())
                                    """"username": "${user.value.username}", """ else ""
                            }
                    ${
                                if (imageLocalUri.value.toString().isNotEmpty())
                                    """"avatar": "${user.value.avatar}", """ else ""
                            }
                    ${
                                if (user.value.email.isNotEmpty())
                                    """"email": "${user.value.email}", """ else ""
                            }
                    ${
                                if (user.value.phoneNumber.isNotEmpty())
                                    """"phoneNumber": "${user.value.phoneNumber}" """ else ""
                            }
                    }
                """.trimIndent()
                        ).responseString() { result ->
                            result.fold({ d ->
                                println(d)
                                GlobalScope.launch {
                                    snackBarHostState.showSnackbar("Upload information successfully")
                                }
                            }, { err ->
                                println(err)
                                GlobalScope.launch {
                                    snackBarHostState.showSnackbar("Something went wrong")
                                }
                            })
                        }

                isEditing.value = false
            }
        }
    }

    fun uploadImgApi(file: File) {
        viewModelScope.launch {
            isLoading.value = true
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
                                snackBarHostState.showSnackbar("Upload image successfully")
                            }


                        },
                        { err ->
                            isLoading.value = false
                            GlobalScope.launch {
                                snackBarHostState.showSnackbar("Something went wrong")
                            }

                        }
                    )
                }

        }

    }

    fun logout(navController: NavController) {
        viewModelScope.launch {
//            Fuel.post("$baseUrl/user/logout")
//                .header("Content-Type" to "application/json")
//                .authentication()
//                .bearer(accessToken!!)
//                .responseString() { _, response, result ->
//                    result.fold({ d ->
//                        println(response)
//                    }, { err ->
//                        println(response)
//                    })
//                }
            navController.navigate(AppRoutes.loginRoute)
        }
    }

    fun getPosts(userId: String, navController: NavController) {
        viewModelScope.launch {
            posts.clear()
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            var isRetry: Boolean = true

            while (isRetry) {
                if (accessToken != null && accessToken != "") {
                    Fuel.get("$baseUrl/post/findbyuid?authorId=$userId")
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
                                            val userLikeList =
                                                item.getJSONArray("interestUserList")
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
                                                item.getJSONObject("author")
                                                    .getString("username")
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
                                            posts.add(post)
                                            isRetry = false
                                        }
                                    },
                                    { err ->
                                        isRetry = false
                                        GlobalScope.launch {
                                            snackBarHostState.showSnackbar("Something went wrong")
                                        }
                                    })

                                401 -> {
                                    accessToken =
                                        refreshToken?.let {
                                            CheckRefreshToken(
                                                it,
                                                navController
                                            )
                                        }
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
                        snackBarHostState.showSnackbar("Something went wrong")
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
                if (accessToken != null && accessToken != "") {
                    Fuel.patch("$baseUrl/post/likepost/$id")
                        .header("Content-Type" to "application/json")
                        .authentication()
                        .bearer(accessToken!!)
                        .jsonBody(json)
                        .awaitStringResponseResult().let { (_, response, result) ->
                            when (response.statusCode) {
                                200 -> result.fold(

                                    { d ->
                                        println(d)
                                        posts.find { it.id === id }?.isLike = !isLike
                                        isRetry = false
                                    },
                                    { err ->
                                        isRetry = false
                                        GlobalScope.launch {
                                            snackBarHostState.showSnackbar("Something went wrong")
                                        }
                                    })

                                401 -> {
                                    accessToken =
                                        refreshToken?.let {
                                            CheckRefreshToken(
                                                it,
                                                navController
                                            )
                                        }
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
                        snackBarHostState.showSnackbar("Something went wrong")
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
                if (accessToken != null && accessToken != "") {
                    Fuel.delete("$baseUrl/post/delete/$id")
                        .timeout(Int.MAX_VALUE)
                        .timeoutRead(Int.MAX_VALUE)
                        .authentication()
                        .bearer(accessToken!!)
                        .awaitStringResponseResult().let { (_, response, result) ->
                            when (response.statusCode) {
                                200 ->
                                    result.fold({ d ->
                                        posts.removeIf { x -> x.id == id }
                                        isLoading.value = false
                                        GlobalScope.launch {
                                            snackBarHostState.showSnackbar("Delete successffuly")
                                        }
                                        isRetry = false

                                    },
                                        { err ->
                                            isRetry = false
                                            GlobalScope.launch {
                                                snackBarHostState.showSnackbar("Something went wrong")
                                            }

                                        })

                                401 -> {
                                    accessToken =
                                        refreshToken?.let {
                                            CheckRefreshToken(
                                                it,
                                                navController
                                            )
                                        }
                                    sharePreferenceProvider.saveAccessToken(accessToken!!)
                                    isRetry = true
                                }

                                else -> {
                                    GlobalScope.launch {
                                        snackBarHostState.showSnackbar("Something went wrong")
                                    }
                                    isRetry = false
                                }
                            }

                        }
                } else {
                    isRetry = false
                    GlobalScope.launch {
                        snackBarHostState.showSnackbar("Something went wrong")
                    }
                }
            }

        }
    }
}