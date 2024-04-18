package com.topic_trove.ui.core.values


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    }
}