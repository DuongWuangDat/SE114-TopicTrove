package com.topic_trove.ui.modules.homescreen.widgets

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.modules.homescreen.widgets.AddImageRow
import java.io.File
import java.io.FileInputStream


@Composable
fun ImageBlock(
    isLoading: MutableState<Boolean>,
    snackbarHostState: SnackbarHostState,
    inputImage: (String) -> Unit,
    uploadImage: (File) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp.value
    val screenHeight = configuration.screenHeightDp.dp
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    Column {
        AddImageRow {
            launcher.launch("image/*")
        }
        Spacer(modifier = Modifier.height(16.dp))
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap = ImageDecoder.decodeBitmap(source)
            }

            bitmap?.let {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Gray, CircleShape)
                            .background(Color.LightGray)
                            .align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    imageUri?.let {
                        val parcelFileDescriptor =
                            context.contentResolver.openFileDescriptor(it, "r")
                        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
                        val extension = MimeTypeMap.getSingleton()
                            .getExtensionFromMimeType(context.contentResolver.getType(it))
                        var fileName = ""
                        context.contentResolver.query(it, null, null, null, null)?.use { cursor ->
                            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            cursor.moveToFirst()
                            fileName = cursor.getString(nameIndex)
                        }
                        val file = File(context.cacheDir, fileName)

                        FileInputStream(fileDescriptor).use { inputStream ->
                            file.outputStream().use { fileOut ->
                                inputStream.copyTo(fileOut)
                            }
                        }
                        uploadImage(file)
                    }
                }, colors = ButtonDefaults.buttonColors(
                    AppColors.AddImgPostButton
                ), shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(horizontal = 1.dp, vertical = 7.dp),
                modifier = Modifier
                    .width((0.3 * screenWidth).dp)
                    .height(27.dp)
                    .align(Alignment.CenterHorizontally)

            ) {
                Text(text = "Upload Icon", style = CustomTextStyle.addImgButtonCommunity())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageBlockPreview() {
    val isLoading = remember { mutableStateOf(false) }
    val snackbarHostState = SnackbarHostState()
    val inputImage: (String) -> Unit = {}
    val uploadImage: (File) -> Unit = {}

    ImageBlock(isLoading, snackbarHostState, inputImage, uploadImage)
}