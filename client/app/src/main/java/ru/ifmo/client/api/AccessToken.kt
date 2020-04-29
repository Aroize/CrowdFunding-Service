package ru.ifmo.client.api

import org.json.JSONObject

data class AccessToken(
    var tokenValue: String = "",
    var expireTime: Long = 0L,
    var userId: Int = 0
) {
    companion object {
        fun parse(response: JSONObject) = AccessToken (
            tokenValue = response.optString("accessToken"),
            expireTime = response.optLong("expires"),
            userId = response.optInt("userId")
        )
    }
}