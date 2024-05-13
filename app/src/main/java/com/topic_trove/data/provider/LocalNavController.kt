package com.topic_trove.data.provider

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

object Provider{
    val LocalNavController = compositionLocalOf <NavHostController>{
        error("No LocalNavController provided")
    }
}
