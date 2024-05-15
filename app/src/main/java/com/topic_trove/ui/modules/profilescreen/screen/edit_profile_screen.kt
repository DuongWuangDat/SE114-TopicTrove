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
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.topic_trove.data.model.User
import com.topic_trove.ui.global_widgets.OverlayLoading
import com.topic_trove.ui.modules.profilescreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.profilescreen.ProfileScreenVM
import com.topic_trove.ui.modules.profilescreen.widgets.HeaderBarActionBtn
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun EditProfile(
    profileVM: ProfileScreenVM = hiltViewModel(),
    navController: NavController
) {
//    val user = profileVM.useSession!!
    val user = profileVM.useSession
    val snackBarHostState = profileVM.snackBarHostState
    val scrollState = rememberScrollState()
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
                   profileVM.viewModelScope.launch {
                       if (profileVM.imageLocalUri.value != Uri.EMPTY) {
                           val file = getFileFromUri(context, profileVM.imageLocalUri.value)
                           file?.let {
                               profileVM.uploadImgApi(it)
                           }
                       }
                       profileVM.updateUser(navController)
                   }
                },
                onBackButtonPressed = {
                    navController.popBackStack()
                })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }


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
                    profileVM.checkIsEnable()
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