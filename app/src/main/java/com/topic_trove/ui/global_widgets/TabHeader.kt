package com.topic_trove.ui.global_widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.core.values.CustomTextStyle

@Composable
fun TabHeader(
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {}
){
    val keyboardController = LocalSoftwareKeyboardController.current
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 66.dp),
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Color.Transparent
            )
        },
        divider = {},
        containerColor = Color.Transparent
    ) {
        Tab(
            selected = selectedTabIndex == 0,
            onClick = {
                onTabSelected(0)
                keyboardController?.hide()
                      },

            ) {
            Text(
                text = "Posts",
                color = if (selectedTabIndex == 0) AppColors.TabColor else Color.Gray,
                style = CustomTextStyle.tabText(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
        Tab(
            selected = selectedTabIndex == 1,
            onClick = {
                onTabSelected(1)
                keyboardController?.hide()
            }

        ) {
            Text(
                text = "Communities",
                color = if (selectedTabIndex == 1) AppColors.TabColor else Color.Gray,
                style = CustomTextStyle.tabText(),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TabHeaderPreview() {
    TabHeader()
}