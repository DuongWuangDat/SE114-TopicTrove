import android.text.BoringLayout
import android.text.Layout
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import com.topic_trove.ui.global_widgets.CommentButton
import com.topic_trove.ui.global_widgets.LikeButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.topic_trove.R
import com.topic_trove.data.model.Post
import com.topic_trove.ui.core.values.Assets
import com.topic_trove.ui.core.values.CustomTextStyle.Companion.createPostHeader
import com.topic_trove.ui.core.values.CustomTextStyle.Companion.titleFieldCreatePost
import com.topic_trove.ui.core.values.CustomTextStyle.Companion.createPostTitle
import com.topic_trove.ui.core.values.CustomTextStyle.Companion.createPostDate
import java.util.Date
import java.text.SimpleDateFormat
import java.time.format.TextStyle
import java.util.Locale
import com.topic_trove.ui.core.values.Assets.Companion.AuthorIcon
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.global_widgets.DeleteButton

@Composable
fun PostCard(data : Post,
             isPostOwner: Boolean,
             isCommunityOwner: Boolean,
             onDelete:()->Unit={},
             onLike: ()->Unit={},
             onClickable: ()->Unit = {},
             isLike: Boolean = false,
             commentCount : Int = 0,
             ) {
    val date = remember { mutableStateOf(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(data.createdAt)) }
    val headerPost = createPostHeader()
    val datePost = createPostDate()
    val titlePost = createPostTitle()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    Column(modifier = Modifier
        .clickable {
            onClickable()
        }
        .background(color = Color.White)
        .padding(top = 7.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)) {
        Row  {
            Text(text = "#"+data.communityName, style = headerPost)
            if(isPostOwner || isCommunityOwner) {
                Spacer(modifier = Modifier.weight(1f))
                DeleteButton(modifier = Modifier) {
                    onDelete()
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            Box(modifier = Modifier
                .width(20.dp)
                .height(20.dp)
                .background(color = Color.Transparent, shape = CircleShape)
                .clip(CircleShape)){
                AsyncImage(if(data.avatar =="") "https://firebasestorage.googleapis.com/v0/b/skillexchange-62da0.appspot.com/o/files%2FArtificial%20Intelligence%20Programming.png?alt=media&token=3accd6fe-5296-4e68-94e4-eefe98660110" else data.avatar,"avatar" ,modifier = Modifier.size(20.dp))
            }
            // Author
            if(isPostOwner){
                Spacer(modifier = Modifier.width(5.dp))
                Image(painter = painterResource(id = AuthorIcon), contentDescription = "Author",
                    modifier = Modifier.size(10.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Row {Modifier.padding(5.dp)
                // Name
                Text(text = data.authorName, style = CustomTextStyle.PostNameText())
                Spacer(modifier = Modifier.width(10.dp))
                // Date
                Text(text = date.value, style = datePost, modifier = Modifier.align(Alignment.Bottom))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(text = data.title, maxLines = 1, style = titlePost ,overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = data.content, style = CustomTextStyle.contentPostCard())
        Spacer(modifier = Modifier.height(8.dp))
        if(data.imageUrl !=""){
            Spacer(modifier = Modifier.height(5.dp))
            AsyncImage(model = data.imageUrl, contentDescription = null, modifier = Modifier.clip(
                RoundedCornerShape(10.dp)
            ))
            Spacer(modifier = Modifier.height(20.dp))
        }

        Row {
            LikeButton(
                interestCount = data.interestCount,
                isLike = isLike
            ){
                onLike()
            }
            Spacer(modifier = Modifier.width(16.dp))
            CommentButton(count = commentCount)
        }
    }
}

@Preview
@Composable
fun PreviewPost() {
    val data = Post(communityName = "T1Bo", authorName = "Name", title = "T1",
        content = "1qqqqqqqqqqqqqqqqqqqqqqq 11111111", imageUrl = "https://firebasestorage.googleapis.com/v0/b/topictrove-a1b0c.appspot.com/o/files%2F1000002488.jpg?alt=media&token=f47b647a-c17c-404f-b42d-120b34c14e39")
    PostCard(data,true,false)
}