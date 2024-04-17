package com.topic_trove.ui.core.values


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

class CustomTextStyle {
    companion object{
        fun h1(color : Color) : TextStyle{
            return TextStyle(color = color)
        }
    }
}