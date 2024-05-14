package com.topic_trove.ui.modules.example


import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R
import com.topic_trove.ui.modules.searchscreen.TopicImage
import com.topic_trove.ui.modules.searchscreen.TopicText


@Preview(showBackground = true)
@Composable
fun ListItemPostComponent() {
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
            items(1) {
                ItemPostComponent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPostComponent(){
    Box(){
        Column {
            TopicText(
                fontSize =12.sp ,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                text = "#LCK",
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ){
                TopicImage(
                    resDrawable = R.drawable.ic_user,
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .padding(end = 6.dp)
                )
                TopicText(
                    fontSize =10.sp ,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W600,
                    text = "Another",
                    color = colorResource(id = R.color.black_500),
                    modifier = Modifier.padding(end = 6.dp)
                )
                TopicImage(
                    resDrawable = R.drawable.ic_author,
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .padding(end = 6.dp)
                )

                TopicText(
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W600,
                    text = "14/4/2024",
                    color = colorResource(id = R.color.gray_400)
                )
                Spacer(Modifier.weight(1f))

                Box(modifier = Modifier
                    .background(
                        colorResource(id = R.color.white),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .border(
                        1.dp,
                        color = colorResource(id = R.color.black_500),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(8.dp)

                ) {
                    TopicText(
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.W600,
                        text = "Delete",
                        color = colorResource(id = R.color.red_500),
                    )
                }

            }
            TopicText(
                fontSize =14.sp ,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W600,
                text = "Post title",
                color = colorResource(id = R.color.black_500),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .fillMaxWidth()
            )
            TopicText(
                fontSize =12.sp ,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W400,
                text = "Post content: LCK mùa Xuân 2024: Đánh bại T1, GenG lập kỷ lục vô địch lần thứ 4 liên tiếp. Trong trận chung kết tổng LCK mùa Xuân 2024, GenG đánh bại T1 3-2, qua đó trở thành nhà vô địch giải đấu. Theo đó, GenG tạo kỷ lục là đội đầu tiên vô địch 4 lần liên tiếp, đồng thời trở thành hạt giống số 1 của LCK tại MSI 2024.",
                color = colorResource(id = R.color.black_500),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .fillMaxWidth()
            )
            TopicImage(
                resDrawable = R.drawable.bgr_lck,
                modifier = Modifier
                    .size(width = 360.dp, height = 230.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row {
                Box(modifier = Modifier
                    .background(
                        colorResource(id = R.color.white),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .border(
                        1.dp,
                        color = colorResource(id = R.color.black_500),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(8.dp)

                ) {
                    Row {
                        TopicImage(
                            resDrawable = R.drawable.ic_like,
                            modifier = Modifier
                                .size(15.dp)
                        )
                        TopicText(
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.W600,
                            text = "16",
                            color = colorResource(id = R.color.red_500),
                            modifier = Modifier.padding(start = 8.dp)

                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))

                Box(modifier = Modifier
                    .background(
                        colorResource(id = R.color.white),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .border(
                        1.dp,
                        color = colorResource(id = R.color.black_500),
                        shape = RoundedCornerShape(25.dp)
                    )
                    .padding(8.dp)

                ) {
                    Row {
                        TopicImage(
                            resDrawable = R.drawable.ic_comment,
                            modifier = Modifier
                                .size(15.dp)
                        )
                        TopicText(
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.W600,
                            text = "15 Comments",
                            color = colorResource(id = R.color.black_500),
                            modifier = Modifier.padding(start = 8.dp)

                        )
                    }
                }
            }

        }
    }
}