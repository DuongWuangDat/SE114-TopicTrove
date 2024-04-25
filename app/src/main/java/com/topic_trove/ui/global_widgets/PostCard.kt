import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import com.topic_trove.ui.global_widgets.CommentButton
import com.topic_trove.ui.global_widgets.LikeButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.R

@Composable
fun Post(hashtag: String, avatar: Int, name: String, date: String, title: String, content: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = hashtag, color = Color.Black, onTextLayout = { result -> /* Handle TextLayoutResult here */ })
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = avatar), contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = name, onTextLayout = { result -> /* Handle TextLayoutResult here */ })
                Text(text = date, onTextLayout = { result -> /* Handle TextLayoutResult here */ })
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis, onTextLayout = { result -> /* Handle TextLayoutResult here */ })
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = content, onTextLayout = { result -> /* Handle TextLayoutResult here */ })
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            LikeButton()
            Spacer(modifier = Modifier.width(16.dp))
            CommentButton()
        }
    }
}

@Preview
@Composable
fun PreviewPost() {
    Post(
        hashtag = "#JetpackCompose",
        avatar = R.drawable.add_btn,
        name = "John Doe",
        date = "2022-01-01",
        title = "This is a post title",
        content = "This is the content of the post."
    )
}