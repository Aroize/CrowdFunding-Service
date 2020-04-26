package ru.ifmo.client.data.models

import org.json.JSONObject

data class User(
    var uid: Int = 0,
    var login: String = "",
    var balance: Int = 0
) {
    companion object {
        fun parse(json: JSONObject) = User(
            uid = json.optInt("id"),
            login = json.optString("login"),
            balance = json.optInt("balance")
        )
    }
}