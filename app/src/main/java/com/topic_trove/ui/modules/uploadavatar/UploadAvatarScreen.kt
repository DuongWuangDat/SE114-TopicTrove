package com.topic_trove.ui.modules.uploadavatar

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.topic_trove.R
import com.topic_trove.ui.core.utils.supportWideScreen
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.MyTopCenterAppBar

@Composable
fun UploadAvatarScreen(
    modifier: Modifier = Modifier,
    onSubmitted: (url: Uri) -> Unit,
    onNavUp: () -> Unit,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            MyTopCenterAppBar(
                topAppBarText = stringResource(R.string.register),
                onNavUp = onNavUp,
            )
        }) { contentPadding ->
        ConstraintLayout(
            modifier = modifier
                .background(color = AppColors.White)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            var imageUri by remember {
                mutableStateOf<Uri?>(null)
            }
            val pickMedia =
                rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    val uri = result.data?.data ?: return@rememberLauncherForActivityResult
                    imageUri = uri
                }

            val onSubmit = {
                if (imageUri != null) {
                    onSubmitted.invoke(imageUri!!)
                }
            }
            val (content, button) = createRefs()
            LazyColumn(
                modifier = Modifier
                    .supportWideScreen()
                    .padding(bottom = 72.dp)
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = contentPadding,
            ) {
                item {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = stringResource(id = R.string.avatar),
                        placeholder = painterResource(R.drawable.avatar_placeholder),
                        error = painterResource(R.drawable.avatar_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .wrapContentSize()
                            .heightIn(min = 128.dp, max = 245.dp)
                            .widthIn(min = 128.dp, max = 200.dp),
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                                val mimeTypes = arrayOf("image/png", "image/jpg", "image/jpeg")
                                type = "*/*"
                                putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                                addCategory(Intent.CATEGORY_OPENABLE)
                                addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            }
                            pickMedia.launch(intent)
                        },
                        modifier = Modifier
                            .wrapContentSize(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AppColors.CreatePostButton),
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                            text = if (imageUri != null) {
                                stringResource(R.string.upload_another)
                            } else {
                                stringResource(R.string.upload_your_avatar)
                            },
                            style = TextStyle(
                                color = AppColors.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight(500),
                                lineHeight = 24.sp,
                            )
                        )
                    }
                }
            }
            Button(
                onClick = { onSubmit() },
                modifier = Modifier
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 40.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.CreatePostButton),
                enabled = imageUri != null,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp),
                    text = stringResource(id = R.string.next),
                    style = TextStyle(
                        color = AppColors.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        lineHeight = 24.sp,
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 640)
@Composable
fun ConfirmEmailScreenPreview() {
    UploadAvatarScreen(onSubmitted = {}, onNavUp = {})
}