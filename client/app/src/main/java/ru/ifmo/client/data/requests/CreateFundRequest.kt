package ru.ifmo.client.data.requests

import org.json.JSONObject
import ru.ifmo.client.api.ApiRequest
import ru.ifmo.client.data.models.Fund

class CreateFundRequest(
    uid: Int,
    name: String,
    limit: Int
) : ApiRequest<Fund>("fund.create") {

    init {
        if (limit > 0)
            addParam("limit", limit)
        addParam("uid", uid)
        addParam("fund_name", name)
    }

    override fun parse(response: JSONObject): Fund {
        return Fund.parse(response.getJSONObject("response"))
    }
}