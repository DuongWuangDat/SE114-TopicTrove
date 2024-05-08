package com.topic_trove.data.sharepref

import android.content.SharedPreferences
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.topic_trove.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharePreferenceProvider @Inject constructor(
    val sharedPreferences: SharedPreferences,
    val moshi: Moshi,
) {
    companion object {
        const val NAME_SHARE_PREFERENCE = "topic_trove_share_preference"
        const val USER_ID = "user_id"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER = "user"
    }

    // Save refresh token
    fun saveRefreshToken(refreshToken: String){
        sharedPreferences.edit().putString(REFRESH_TOKEN, refreshToken).apply()
    }

    // Save user id
    fun saveUserId(userId: String){
        sharedPreferences.edit().putString(USER_ID, userId).apply()
    }

    // Save access token
    fun saveAccessToken(accessToken: String){
        sharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply()
    }

    //Get refresh token
    fun getRefreshToken(): String?{
        return sharedPreferences.getString(REFRESH_TOKEN, null)
    }

    //Get user id
    fun getUserId(): String?{
        return sharedPreferences.getString(USER_ID, null)
    }

    //Get access token
    fun getAccessToken(): String?{
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }
    @ToJson
    inline fun <reified T> save(key: String, any: Any) {
        val editor = sharedPreferences.edit()
        when (any) {
            is String -> editor.putString(key, any)
            is Float -> editor.putFloat(key, any)
            is Int -> editor.putInt(key, any)
            is Long -> editor.putLong(key, any)
            is Boolean -> editor.putBoolean(key, any)
            else -> {
                val adapter = moshi.adapter(any.javaClass)
                editor.putString(key, adapter.toJson(any))
            }
        }
        editor.apply()
    }




    @FromJson
    inline fun <reified T> get(key: String): T? {
        when (T::class) {
            Float::class -> return sharedPreferences.getFloat(key, 0f) as T
            Int::class -> return sharedPreferences.getInt(key, 0) as T
            Long::class -> return sharedPreferences.getLong(key, 0) as T
            String::class -> return sharedPreferences.getString(key, "") as T
            Boolean::class -> return sharedPreferences.getBoolean(key, false) as T
            else -> {
                val any = sharedPreferences.getString(key, "")
                val adapter = moshi.adapter(T::class.java)
                if (!any.isNullOrEmpty()) {
                    return adapter.fromJson(any)
                }
            }
        }
        return null
    }
}
