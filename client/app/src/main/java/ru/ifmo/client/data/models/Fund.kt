package ru.ifmo.client.data.models

import org.json.JSONObject

data class Fund(
    var id: Int = 0,
    var ownerId: Int = 0,
    var name: String = "",
    var raised: Int = 0,
    var raiseLimit: Int = -1
) {
    companion object {
        fun parse(jsonObject: JSONObject) = Fund(
            id = jsonObject.optInt("id"),
            ownerId = jsonObject.optInt("ownerId"),
            name = jsonObject.optString("name"),
            raised = jsonObject.optInt("raised"),
            raiseLimit = jsonObject.optInt("limit", -1)
        )
    }
}