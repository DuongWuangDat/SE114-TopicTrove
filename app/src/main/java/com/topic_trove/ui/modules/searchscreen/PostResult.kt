package com.topic_trove.ui.modules.searchscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.PostCard
import com.topic_trove.ui.routes.AppRoutes


@Composable
fun PostResult (
    searchResultViewModel: SearchResultViewModel,
    navController: NavController
) {
    val postList = listOf(Post())
    val idUser = searchResultViewModel.idUser
    LaunchedEffect(key1 = true) {
        searchResultViewModel.idUser?.let { searchResultViewModel.getPostList(it, navController ) }
        searchResultViewModel.searchValue.value=""
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {

        }
        items(searchResultViewModel.postList) { post ->
            val isCommunityOwner =
                if (post.comunityAuthor != "") (post.comunityAuthor == idUser) else false
            val isPostOwner = (post.authorID == idUser)

            Divider(color = AppColors.DividerColor, thickness = 0.3.dp)
            PostCard(
                data = post,
                isPostOwner = isPostOwner,
                isCommunityOwner = isCommunityOwner,
                onLike = {
                    searchResultViewModel.idUser?.let { it1 ->
                        searchResultViewModel.likePost(
                            post.id,
                            it1,
                            post.isLike,
                            navController
                        )
                    }

                },
                onDelete = {
                    searchResultViewModel.isShowDialog.value = true
                    searchResultViewModel.curPostId.value = post.id
                },
                onClickable = {
                    navController.navigate(
                        "${AppRoutes.postDetailRoute}/${post.id}"
                    )

                }
            )
        }
    }
}