package com.topic_trove.ui.modules.searchscreen

import androidx.compose.animation.fadeIn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.topic_trove.data.model.Community
import com.topic_trove.data.model.Post
import com.topic_trove.data.sharepref.SharePreferenceProvider
import com.topic_trove.ui.core.utils.CheckRefreshToken
import com.topic_trove.ui.core.values.AppStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val sharePreferenceProvider: SharePreferenceProvider
) : ViewModel(){
    var postList = mutableStateListOf<Post>()
    var communityList = mutableStateListOf<Community>()
    var accessToken = sharePreferenceProvider.getAccessToken()
    var base_url = AppStrings.BASE_URL
    var snackbarHostState = SnackbarHostState()
    var refreshToken = sharePreferenceProvider.getRefreshToken()
    var isRefreshing = mutableStateOf(false)

    fun getPostList(userId: String, navController: NavController) {
        viewModelScope.launch {
            postList.clear()
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            var isRetry: Boolean = true

            while (isRetry) {
                if (accessToken != null && accessToken != "") {
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
            isRefreshing.value=false
        }
    }

}