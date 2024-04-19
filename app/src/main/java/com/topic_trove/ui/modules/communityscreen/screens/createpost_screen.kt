package com.topic_trove.ui.modules.communityscreen.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.topic_trove.ui.core.values.CustomTextStyle
import com.topic_trove.ui.modules.communityscreen.CommunityScreenVM
import com.topic_trove.ui.modules.communityscreen.widgets.AddImageRow
import com.topic_trove.ui.modules.communityscreen.widgets.CommunityCard
import com.topic_trove.ui.modules.communityscreen.widgets.ImageBlock
import com.topic_trove.ui.modules.communityscreen.widgets.TextFieldCard
import com.topic_trove.ui.modules.communityscreen.widgets.TopBarCreatePost
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun createpostScreen(
    onBack: ()->Unit
){
    val communityVM = viewModel<CommunityScreenVM>()
    val post by communityVM.postData.collectAsState()

    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            TopBarCreatePost(
                state = communityVM.isEnable,
                onBack = onBack,
                onCreateClick = {
                    println(post)
                }
            )
            collumnContent(
                communityVM = communityVM
            )
        }
    }
}

@Composable
fun collumnContent(
    communityVM: CommunityScreenVM
){
    val scrollState = rememberScrollState()
    Column(modifier = Modifier
        .padding(horizontal = 31.5.dp, vertical = 10.dp)
        .verticalScroll(
            scrollState
        )
        ) {
        Spacer(modifier = Modifier.height(15.dp))
        CommunityCard(name = "Community1")
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Title"){
            communityVM.inputTitle(it)
            communityVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldCard(title = "Content"){
            communityVM.inputContent(it)
            communityVM.checkIsEnable()
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImageBlock()

    }
}
@Preview(name = "Top bar create post", showBackground = true)
@Composable
fun TopbarPreview(){



}