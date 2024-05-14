package com.topic_trove.ui.modules.searchscreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R


@Preview(showBackground = true)
@Composable
fun ListItemSearchComponent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                ListContractSearchComponentUI()
                TabHeader()
            }
            items(5) {
                ItemListSearch()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListContractSearchComponentUI(
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
                .clickable {},
            resDrawable = R.drawable.back_arrow,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(end = 16.dp)
                .background(colorResource(id = R.color.gray_400), shape = RoundedCornerShape(5.dp))


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
                    value = " ",
                    onValueChange = { newQuery ->

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
@Preview(showBackground = true)
@Composable
fun TabHeader(){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)

    ) {

        TopicText(
            fontSize =12.sp ,
            fontFamily = FontFamily.Default,
            text = "Posts",
        )
        Spacer(modifier = Modifier.width(30.dp))

        TopicText(
            fontSize =12.sp ,
            fontFamily = FontFamily.Default,
            text = "Communities",
        )
    }
}
@Preview(showBackground = true)
@Composable
fun ItemListSearch(){
    Box {
        Column(modifier = Modifier.padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                TopicImage(
                    resDrawable = R.drawable.ic_user,
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                )
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    TopicText(
                        fontSize =12.sp ,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W600,
                        text = "community1",
                        color = colorResource(id = R.color.blue_500)
                    )
                    TopicText(
                        fontSize =10.sp ,
                        fontFamily = FontFamily.Default,
                        text = "140 members",
                    )
                }
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(
                            colorResource(id = R.color.blue_500),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(8.dp)
                ) {
                    TopicText(
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W600,
                        text = "Join",
                        color = colorResource(id = R.color.white)
                    )
                }

            }

            TopicText(
                fontSize =12.sp ,
                fontFamily = FontFamily.Default,
                modifier = Modifier.padding(bottom = 10.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = "Community description... Community 1 is a place for you to share your experience",
            )

            Box(modifier = Modifier
                .height(0.5.dp)
                .fillMaxWidth()
                .background(colorResource(id = R.color.gray_400)))

        }

    }
}
