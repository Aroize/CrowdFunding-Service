package ru.ifmo.client.data.requests

import org.json.JSONObject
import ru.ifmo.client.api.ApiRequest
import ru.ifmo.client.data.models.Fund
import ru.ifmo.client.forEach

class FundsGetRequest(
    count: Int,
    offset: Int
) : ApiRequest<List<Fund>>("fund.get") {
    init {
        addParam("count", count)
        addParam("offset", offset)
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