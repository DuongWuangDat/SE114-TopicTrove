package com.topic_trove.ui.core.utils

import androidx.navigation.NavController
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import com.topic_trove.ui.core.values.AppStrings
import com.topic_trove.ui.routes.AppRoutes
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

val baseUrl = AppStrings.BASE_URL
fun CheckRefreshToken(refreshToken: String, navController: NavController): String {
    var accessToken = ""
    runBlocking {

        Fuel.get("$baseUrl/token/refresh-token")
            .timeoutRead(Int.MAX_VALUE)
            .timeout(Int.MAX_VALUE)
            .authentication()
            .bearer(refreshToken)
            .awaitStringResponseResult()
            .let { (request, response, result) ->
                when (response.statusCode) {
                    200 -> {
                        result.fold(
                            { d ->
                                var jsonObj = JSONObject(d)
                                print(jsonObj)
                                accessToken = jsonObj.getString("access_token")

                            },
                            { err ->
                                println("Error: $err")
                            }
                        )
                    }

                    401 -> {
                        println("Navigate to login")
                        navController.navigate(AppRoutes.loginRoute) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }

                    else -> {
                        println("Something went wrong")
                    }
                }
            }

    }
    if (accessToken == "") {
        return ""
    } else {
        return accessToken
    }
}

