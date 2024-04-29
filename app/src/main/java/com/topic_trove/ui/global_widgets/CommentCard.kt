package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.topic_trove.R
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.Assets.Companion.AuthorIcon
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.core.values.CustomTextStyle.Companion.createPostDate
import com.topic_trove.ui.modules.postdetailscreen.Comment
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CommentCard(
    modifier: Modifier = Modifier,
    divider: Boolean = false,
    data: Comment,
    onDelete: (String) -> Unit,
    onLike: (Boolean, String) -> Unit,
    onReply: (String, String) -> Unit,
    spacer: @Composable (() -> Unit)? = {},
) {
    val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(data.createdAt)
    val datePost = createPostDate()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            if (divider) {
                Divider(
                    color = AppColors.DividerColor,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(0.3.dp),
                    thickness = 0.3.dp
                )
            }
            Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                if (spacer != null) {
                    spacer()
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .background(color = Color.Transparent, shape = CircleShape)
                            .clip(CircleShape)
                    ) {
                        AsyncImage(
                            if (data.avatar == "") "https://firebasestorage.googleapis.com/v0/b/skillexchange-62da0.appspot.com/o/files%2FArtificial%20Intelligence%20Programming.png?alt=media&token=3accd6fe-5296-4e68-94e4-eefe98660110" else data.avatar,
                            contentDescription = stringResource(R.string.avatar),
                            modifier = Modifier.size(20.dp),
                            contentScale = ContentScale.Crop,
                        )
                    }
                    // Author
                    if (data.owner) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Image(
                            painter = painterResource(id = AuthorIcon),
                            contentDescription = stringResource(R.string.author),
                            modifier = Modifier.size(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    // Name
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = data.authorName,
                        style = CustomTextStyle.PostNameText()
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    // Date
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = date,
                        style = datePost,
                    )
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                            .weight(1f)
                    )
                    if (data.owner) {
                        DeleteButton(modifier = Modifier) {
                            onDelete(data.id)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = data.content, style = CustomTextStyle.contentPostCard())
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    LikeButton(
                        interestCount = data.interestCount,
                        isLike = data.liked,
                    ) { onLike(it, data.id) }
                    Spacer(modifier = Modifier.width(16.dp))
                    ReplyButton {
                        onReply(data.content, data.id)
                    }
                }
            }
        }
        data.replies.forEachIndexed { index, reply ->
            Row(
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                if (divider) {
                    Divider(
                        color = AppColors.DividerColor,
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(0.3.dp),
                        thickness = 0.3.dp
                    )
                }
                CommentCard(
                    modifier = Modifier.padding(
                        top = if (index == 0) 15.dp else 0.dp,
                        start = 15.dp,
                    ),
                    divider = true,
                    data = reply,
                    spacer = if (index == 0) {
                        null
                    } else {
                        { Spacer(modifier = Modifier.height(15.dp)) }
                    },
                    onReply = onReply,
                    onDelete = {
                        onDelete(reply.id)
                    },
                    onLike = { isLiked, id ->
                        onLike(isLiked, id)
                    },
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCommentCard() {
    val data = Comment(
        authorName = "Name2",
        content = "11111111",
        avatar = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2F1000002488.jpg?alt=media&token=f47b647a-c17c-404f-b42d-120b34c14e39",
        replies = listOf(
            Comment(
                authorName = "Name3",
                content = "11111111",
                avatar = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2F1000002488.jpg?alt=media&token=f47b647a-c17c-404f-b42d-120b34c14e39",
                replies = listOf(),
            )
        ),
    )
    CommentCard(
        data = data,
        onDelete = { },
        onLike = { _, _ -> },
        onReply = { _, _ -> },
    )
}