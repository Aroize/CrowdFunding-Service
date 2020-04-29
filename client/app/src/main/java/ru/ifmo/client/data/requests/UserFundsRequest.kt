package ru.ifmo.client.data.requests

import org.json.JSONObject
import ru.ifmo.client.api.ApiRequest
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.forEach

class UserFundsRequest(
    uid: Int
) : ApiRequest<List<Fund>>("user.funds"){
    init {
        addParam("uid", uid)
    }

    override fun parse(response: JSONObject): List<Fund> {
        val jsonArray = response.getJSONArray("response")
        val result = ArrayList<Fund>(jsonArray.length())
        jsonArray.forEach {
            it as JSONObject
            result.add(Fund.parse(it))
        }
        return result
    }
}