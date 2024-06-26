package com.topic_trove.ui.global_widgets



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.topic_trove.R
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.modules.searchscreen.SearchResultViewModel
import com.topic_trove.ui.modules.searchscreen.TopicImage

@Composable
fun ListContractSearchComponentUI(
    onNavBack: ()-> Unit,
    searchResultViewModel: SearchResultViewModel,
    selectTabIndex : Int = 0
){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ){
        TopicImage(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .align(Alignment.CenterVertically)
                .size(24.dp)
                .clickable {
                    onNavBack()
                },
            resDrawable = R.drawable.back_arrow,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(end = 16.dp)
                .background(AppColors.TextFieldColorSearch, shape = RoundedCornerShape(5.dp))


        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                        end = 12.dp
                    )

            ) {
                TopicImage(
                    resDrawable = R.drawable.ic_search,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(22.dp)
                )

                BasicTextField(
                    value = searchResultViewModel.searchValue.value,
                    onValueChange = { newQuery ->
                        searchResultViewModel.searchFunction(selectTabIndex,newQuery)
                    },
                    singleLine = true,
                    textStyle = TextStyle.Default,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 2.dp),
                    cursorBrush = Brush.verticalGradient(
                        0.00f to Color.Transparent,
                        0.10f to Color.Transparent,
                        0.10f to colorResource(R.color.black_500),
                        0.90f to colorResource(R.color.black_500),
                        0.90f to Color.Transparent,
                        1.00f to Color.Transparent
                    )
                )
            }
        }
    }

}

