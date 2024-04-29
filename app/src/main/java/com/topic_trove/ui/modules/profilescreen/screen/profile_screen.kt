package com.topic_trove.ui.modules.profilescreen.screen

import HeaderBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.topic_trove.R
import com.topic_trove.data.model.User
import com.topic_trove.ui.core.values.convertHex
import com.topic_trove.ui.modules.profilescreen.ProfileScreenVM

@Composable
fun ProfileScreen(
    user: User = User(),
    navController: NavController
) {
    val profileVM = viewModel<ProfileScreenVM>()

    LaunchedEffect(key1 = navController) {
        profileVM.getPosts()
    }
    Scaffold(topBar = {
        HeaderBar(onBackButtonPressed = { navController.popBackStack() })
    }

    ) { innerPadding ->
        (Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                model = user.avatar,
                placeholder = painterResource(R.drawable.suzy),
                contentDescription = "Profile Picture",
            )
            Text(
                text = user.username,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(convertHex("#FF0000")),
                    disabledContainerColor = Color.Yellow,
                    disabledContentColor = Color.Cyan,
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                contentPadding = PaddingValues(35.dp, 8.dp),
                modifier = Modifier

            ) {
                Text(text = "Edit")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonColors(
                    containerColor = Color(convertHex("#E90000")),
                    contentColor = Color.White,
                    disabledContainerColor = Color.Yellow,
                    disabledContentColor = Color.Cyan,
                ),
                modifier = Modifier
            ) {
                Text(text = "Log out")
            }

        })
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        User(
            "1",
            "John Doe",
            "abc@mail.com",
            "1234567890",
            "android.resource://com.topic_trove/drawable/add_btn",
            emptyList()
        ),
        navController = NavController(LocalContext.current)
    )
}