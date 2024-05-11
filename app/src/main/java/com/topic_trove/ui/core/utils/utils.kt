package com.topic_trove.ui.core.utils

import androidx.navigation.NavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.await
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.coroutines.awaitStringResponse
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.topic_trove.ui.core.values.AppStrings
import com.topic_trove.ui.routes.AppRoutes
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

val baseUrl = AppStrings.BASE_URL
fun CheckRefreshToken(refreshToken: String, navController: NavController) : String {
    var accessToken= ""
    runBlocking {

        Fuel.get("$baseUrl/token/refresh-token")
            .authentication()
            .bearer(refreshToken)
            .awaitStringResponseResult()
            .third.fold(
                {d-> var jsonObj = JSONObject(d)
                    print(jsonObj)
                    accessToken = jsonObj.getString("access_token")},
                {err->
                    println("Navigate to login")
                    navController.navigate(AppRoutes.loginRoute){
                        popUpTo(navController.graph.startDestinationId){
                            inclusive=true
                        }
                        launchSingleTop=true
                    }
                }
            )
    }
    if(accessToken==""){
        return ""
    }
    else{
        return accessToken
    }
}

