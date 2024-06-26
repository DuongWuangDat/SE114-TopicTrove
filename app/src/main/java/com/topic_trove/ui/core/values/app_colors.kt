package com.topic_trove.ui.core.values

import androidx.compose.ui.graphics.Color

class AppColors {

    companion object {
        val White = Color(0xFFFFFFFF)
        val Black = Color(convertHex("#000000"))
        val BorderStroke = Color(convertHex("#C3C7E5"))
        val CommunityTitleHeader = Color(convertHex("#004CDF"))
        val CommunityJoinButton = Color(convertHex("#0478FF"))
        val CreatePostButton = Color(convertHex("#0072DB"))
        val CreateTextButton = Color.White
        val TitleFieldCreatePost = Color(convertHex("#2F394E"))
        val TextFieldColor = Color(convertHex("#C3C7E5"))
        val AddImgPostButton = CreatePostButton
        val AddImgTextButton = CreateTextButton
        val DisableCreatePostButton = Color(convertHex("#DFDFDF"))
        val DisableTextCreatePost = Color(convertHex("#686868"))
        val BackgroundIndicatorr = Color.Black.copy(alpha = 0.23f)
        val BorderStrokeColor = Color(convertHex("#A1A1A1"))
        val DividerColor = Color(convertHex("#555555"))
        val HomeTitleColor = Color.Red
        val TabColor = Color(convertHex("#0072DB"))
        val TextFieldColorSearch = Color(convertHex("#BCBCBC")).copy(alpha = 0.29f)
    }
}

fun convertHex(hex: String): Int {
    return android.graphics.Color.parseColor(hex)
}