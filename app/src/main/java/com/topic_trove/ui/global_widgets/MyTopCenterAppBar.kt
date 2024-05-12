package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopCenterAppBar(
    topAppBarText: String,
    onNavUp: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
        title = {
            Text(
                text = topAppBarText,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black
                )
            )
        },
        navigationIcon = {
            if (onNavUp != null) {
                IconButton(onClick = onNavUp) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = stringResource(id = R.string.back),
                        tint = Color.Black
                    )
                }
            }
        },
        // We need to balance the navigation icon, so we add a spacer.
        actions = {
            Spacer(modifier = Modifier.width(68.dp))
        },
    )
}