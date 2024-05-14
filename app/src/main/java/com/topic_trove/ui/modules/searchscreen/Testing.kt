package com.topic_trove.ui.modules.searchscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.R
import androidx.compose.runtime.*
import com.topic_trove.ui.global_widgets.TabHeader
import com.topic_trove.ui.global_widgets.ListContractSearchComponentUI


@Preview(showBackground = true)
@Composable
fun ListItemSearchComponent0() {

    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(16.dp)
    ) {
        ListContractSearchComponentUI()
        TabHeader(selectedTabIndex = selectedTabIndex) { index ->
            selectedTabIndex = index
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedTabIndex) {
            0 -> PostResult()
            1 -> CommunityResult()
        }
    }
}


