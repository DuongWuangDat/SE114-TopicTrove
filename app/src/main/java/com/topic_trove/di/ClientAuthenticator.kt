package com.topic_trove.di


import com.topic_trove.data.repositories.AuthRepository
import com.topic_trove.data.sharepref.SharePreferenceProvider
import dagger.Lazy
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class ClientAuthenticator @Inject constructor(
    private val authRepositoryWrapper: Lazy<AuthRepository>,
    private val sharePreferenceProvider: SharePreferenceProvider,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            response.request.run {
                val isRefreshTokenRequest =
                    url.toString().endsWith("/api/v1/token/refresh-token") && method == GET
                if (isRefreshTokenRequest) return null
            }
            val authRepository = authRepositoryWrapper.get()
            authRepository.refresh()
                .onSuccess {
                    sharePreferenceProvider.save<String>(
                        SharePreferenceProvider.ACCESS_TOKEN,
                        it.accessToken
                    )
                    return response.request.newBuilder()
                        .header("Authorization", "Bearer ${it.accessToken}")
                        .build()
                }
            return null
        }
    }

    companion object {
        const val GET = "GET"
    }
}