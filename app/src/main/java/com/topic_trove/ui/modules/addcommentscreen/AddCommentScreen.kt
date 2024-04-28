package com.topic_trove.ui.modules.addcommentscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.topic_trove.R
import com.topic_trove.ui.core.values.AppColors
import com.topic_trove.ui.global_widgets.MyTextField
import com.topic_trove.ui.global_widgets.MyTopLeftAppBar
import com.topic_trove.ui.modules.registerscreen.states.TextFieldState

@Composable
fun AddCommentScreen(
    comment: (String) -> Unit,
    onNavUp: () -> Unit,
) {
    val commentState = remember { TextFieldState() }
    Scaffold(
        topBar = {
            MyTopLeftAppBar(
                icon = Icons.Filled.Close,
                topAppBarText = stringResource(R.string.add_comment),
                onNavUp = {
                    onNavUp()
                },
                actions = {
                    Button(
                        onClick = { comment(commentState.text) },
                        contentPadding = PaddingValues(7.dp),
                        modifier = Modifier
                            .width(55.dp)
                            .height(30.dp)
                            .padding(end = 14.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(AppColors.CommunityJoinButton),
                        enabled = commentState.text.isNotEmpty()
                    ) {
                        Text(
                            text = stringResource(R.string.post),
                            style = TextStyle(
                                color = AppColors.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight(600),
                                lineHeight = 12.sp,
                            )
                        )
                    }
                })
        },
        content = { contentPadding ->
            val focusRequester = remember { FocusRequester() }
            LazyColumn(
                contentPadding = contentPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppColors.White)
                    .padding(horizontal = 30.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    // Name
                    Text(
                        text = stringResource(R.string.comment), style = TextStyle(
                            color = AppColors.TitleFieldCreatePost,
                            fontSize = 14.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 24.sp,
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MyTextField(
                        modifier = Modifier.heightIn(min = 200.dp),
                        textState = commentState,
                        onImeAction = { focusRequester.requestFocus() }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun ReplyScreenPreview() {
    AddCommentScreen(comment = {}, onNavUp = {})
}