import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.convertHex
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.topic_trove.R

@Composable
fun ImagePicker(
    initialImage: String,
    onImageSelected: (Uri) -> Unit
) {
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                onImageSelected(it)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        imageUri.value?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(model = uri.toString()),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
        } ?: run {
            AsyncImage(
                model = initialImage,
                placeholder = painterResource(id = R.drawable.avatar_default),
                contentDescription = "Initial Profile Picture",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
        }

        Button(
            onClick = { launcher.launch("image/*") },
            colors = ButtonColors(
                containerColor = Color(convertHex("#0072DB")),
                contentColor = Color(convertHex("#ffffff")),
                disabledContentColor = Color(convertHex("#000000")),
                disabledContainerColor = Color(convertHex("#FFD700")),
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Choose Image")
        }
    }
}