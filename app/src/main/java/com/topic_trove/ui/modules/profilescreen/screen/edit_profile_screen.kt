package com.topic_trove.ui.modules.profilescreen.screen

import ImagePicker
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.topic_trove.data.model.User
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.modules.profilescreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.profilescreen.ProfileScreenVM
import com.topic_trove.ui.modules.profilescreen.widgets.HeaderBarActionBtn
import java.io.File

@Composable
fun EditProfile(
    user: User = User(
        "662cc6b65b4d055e982936ce",
        "Eren Yeager",
        "quoctruong@mail.com",
        "0987654321",
        "https://www.google.com/url?sa=i&url=https%3A%2F%2Fbuffer.com%2Flibrary%2Ffree-images%2F&psig=AOvVaw3ahVGsupVozBkj6Vj13Mhz&ust=1714487449663000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCLi6ns3R54UDFQAAAAAdAAAAABAE"
    ), navController: NavController
) {
    val scrollState = rememberScrollState()
    val profileVM = viewModel<ProfileScreenVM>()
    val horizontalPadding = 25.dp
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            HeaderBarActionBtn(title = "Edit Profile",
                actionText = "Save",
                state = profileVM.actionState,
                onActionClick = {
                    profileVM.isEditing.value = true
                    val file = getFileFromUri(context, profileVM.imageLocalUri.value)
                    file?.let {
                        // Here you can start the upload
                        profileVM.uploadImgApi(it)
                    }
                },
                onBackButtonPressed = { navController.popBackStack() })
        },

        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(
                    horizontalPadding,
                    paddingValues.calculateTopPadding(),
                    horizontalPadding,
                    paddingValues.calculateBottomPadding()
                )


        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState)

            ) {
                Spacer(modifier = Modifier.height(8.dp))
                ImagePicker(initialImage = user.avatar) { uri ->
                    profileVM.imageLocalUri.value = uri
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextFieldCard(title = "Display name", placeholder = user.username) {
                    profileVM.inputDisplayName(it)
                    profileVM.checkIsEnable()
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextFieldCard(
                    title = "Email",
                    placeholder = user.email
                ) {
                    profileVM.inputEmail(it)
                    profileVM.checkIsEnable()
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextFieldCard(
                    title = "Phone number",
                    placeholder = user.phoneNumber,
                ) {
                    profileVM.inputPhoneNumber(it)
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextFieldCard(
                    title = "Password",
                    placeholder = "xxxxxxxx",
                    isPasswordField = true
                ) {
                    profileVM.inputPassword(it)
                    profileVM.checkIsEnable()
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextFieldCard(
                    title = "Confirm password",
                    placeholder = "xxxxxxxx",
                    isPasswordField = true
                ) {
                    profileVM.inputConfirmPassword(it)
                    profileVM.checkIsEnable()
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (profileVM.isEditing.value) {
        OverlayLoading()
    }
}

fun getFileFromUri(context: Context, uri: Uri): File? {
    val inputStream = context.contentResolver.openInputStream(uri)
    val fileName = getFileName(context, uri)
    val tempFile = File.createTempFile(fileName, null, context.cacheDir)
    tempFile.outputStream().use {
        inputStream?.copyTo(it)
    }
    return tempFile
}

fun getFileName(context: Context, uri: Uri): String {
    var name = ""
    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
    returnCursor?.let { cursor ->
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        name = cursor.getString(nameIndex)
        cursor.close()
    }
    return name
}

@Preview
@Composable
private fun EditProfilePreview() {
    EditProfile(navController = NavController(LocalContext.current))
}