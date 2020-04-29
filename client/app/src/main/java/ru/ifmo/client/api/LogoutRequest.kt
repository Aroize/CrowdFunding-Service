package ru.ifmo.client.api

import org.json.JSONObject
import ru.ifmo.client.api.ApiRequest

class LogoutRequest(
    uid: Int
) : ApiRequest<Int>("auth.logout") {

    init {
        addParam("uid", uid)
    }

    override fun parse(response: JSONObject): Int {
        return response.optInt("response")
    }
}