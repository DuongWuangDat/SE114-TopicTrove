package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.topic_trove.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopLeftAppBar(
    icon: ImageVector = Icons.Filled.ArrowBack,
    topAppBarText: String,
    onNavUp: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        title = {
            Text(
                text = topAppBarText,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.CenterStart),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(700),
                    lineHeight = 20.sp,
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { onNavUp() }) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = R.string.back),
                    tint = Color.Black
                )
            }

        },
        actions = {
            actions()
        },
    )
}

@Preview
@Composable
private fun MyTopLeftAppBarPreview() {
    MyTopLeftAppBar(topAppBarText = "Title", onNavUp = { })
}