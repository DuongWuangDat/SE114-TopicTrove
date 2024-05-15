package com.topic_trove.ui.core.values


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class CustomTextStyle {
    companion object{
        fun h1(color : Color) : TextStyle{
            return TextStyle(color = color)
        }

        fun communityTitle() : TextStyle{
            return TextStyle(color = AppColors.CommunityTitleHeader, fontSize = 13.sp, fontWeight = FontWeight(700))
        }

        fun communityCountMember() : TextStyle{
            return TextStyle(color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight(300))
        }

        fun joinButtonText(color: Color) : TextStyle{
            return TextStyle(color = color, fontSize = 10.sp, fontWeight = FontWeight(600))
        }

        fun communityDescription() : TextStyle{
            return TextStyle(color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight(400))
        }

        fun createTextButton(color: Color): TextStyle{
            return TextStyle(color= color, fontWeight = FontWeight(600), fontSize = 10.sp)
        }

        fun titleFieldCreatePost(): TextStyle{
            return TextStyle(color = AppColors.TitleFieldCreatePost, fontWeight = FontWeight(500), fontSize = 15.sp)
        }

        fun hintTextCreatePost(): TextStyle{
            return TextStyle(color = AppColors.TextFieldColor, fontWeight = FontWeight(250), fontSize = 14.sp, fontStyle = FontStyle.Italic)
        }

        fun filedTextCreatePost(): TextStyle{
            return TextStyle(color = Color.Black, fontWeight = FontWeight(400), fontSize = 14.sp)
        }

        fun addImgButtonCommunity(): TextStyle{
            return createTextButton(AppColors.CreateTextButton)
        }

        fun contentPostCard(): TextStyle{
            return TextStyle(color= Color.Black, fontWeight = FontWeight(400), fontSize = 11.sp)
        }

        // ĐQP
        fun createPostHeader() : TextStyle{
            return TextStyle(color = Color.Black, fontWeight = FontWeight(700), fontSize = 13.sp)
        }

        fun createPostTitle() : TextStyle{
            return TextStyle(color = Color.Black, fontWeight = FontWeight(700), fontSize = 15.sp)
        }
        fun createPostDate() : TextStyle{
            return TextStyle(color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight(400), textAlign =  TextAlign.Center)
        }
        fun likeCommentText() : TextStyle{
            return TextStyle(color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight(500), textAlign = TextAlign.Center,)
        }

        fun postNameText() : TextStyle{
            return TextStyle(color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight(700), textAlign = TextAlign.Center,)
        }

        fun homeTitle() : TextStyle{
            return TextStyle(color = AppColors.HomeTitleColor, fontSize = 20.sp, fontWeight = FontWeight(900) )
        }

        fun itemCommunity() : TextStyle{
            return TextStyle(color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight(500))
        }

        fun communityTitleInHome() : TextStyle{
            return TextStyle(color = Color.Black, fontSize = 13.sp, fontWeight = FontWeight(700))
        }

        fun communityCreateTitle() : TextStyle{
            return TextStyle(color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight(700))
        }
        // ĐQP

        fun tabText() : TextStyle{
            return TextStyle(fontWeight = FontWeight(600), fontSize = 12.sp)
        }

        fun textInputField(): TextStyle{
            return TextStyle(color = Color.Black, fontWeight = FontWeight(400), fontSize = 16.sp)
        }
    }
}