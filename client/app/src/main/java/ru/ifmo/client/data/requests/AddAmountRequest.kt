package ru.ifmo.client.data.requests

import org.json.JSONObject
import ru.ifmo.client.api.ApiRequest

class AddAmountRequest(
    uid: Int,
    amount: Int
) : ApiRequest<Int>("bill.userAddAmount") {
    init {
        addParam("uid", uid)
        addParam("amount", amount)
    }

    override fun parse(response: JSONObject): Int {
        return response.optInt("response")
    }
}