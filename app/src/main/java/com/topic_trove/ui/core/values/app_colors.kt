package com.topic_trove.ui.core.values

import androidx.compose.ui.graphics.Color

class AppColors {

    companion object{
        val White = Color(0xFFFFFFFF)
        val CommunityTitleHeader = Color(convertHex("#004CDF"))
        val CommunityJoinButton = Color(convertHex("#0478FF"))
    }
}

fun convertHex(hex: String): Int{
    return android.graphics.Color.parseColor(hex)
}