package com.topic_trove.ui.modules.profilescreen.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBarActionBtn(
    title: String = "",
    actionText: String,
    state: MutableState<Boolean>,
    onActionClick: () -> Unit,
    onBackButtonPressed: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onBackButtonPressed() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .width(27.dp)
                        .height(27.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ),
        actions = {
            Button(
                onClick = { onActionClick() },
                enabled = state.value,
                contentPadding = PaddingValues(horizontal = 11.dp, vertical = 7.5.dp),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = AppColors.DisableCreatePostButton,
                    containerColor = AppColors.CreatePostButton
                ),
                modifier = Modifier
                    .padding(10.dp, 0.dp)
                    .width(55.dp)
                    .height(27.dp)
            ) {
                Text(
                    text = actionText,
                    style = if (state.value)
                        CustomTextStyle.createTextButton(
                            AppColors.CreateTextButton
                        ) else
                        CustomTextStyle.createTextButton(AppColors.DisableTextCreatePost)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHeaderBar() {
    HeaderBarActionBtn(
        title = "Edit Profile",
        actionText = "Save",
        state = mutableStateOf(true),
        onActionClick = {},
    ) {}
}