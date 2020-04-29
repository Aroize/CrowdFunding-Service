package ru.ifmo.client.data.requests

import org.json.JSONObject
import ru.ifmo.client.api.ApiRequest

class DonateRequest(
    uid: Int,
    amount: Int,
    fundId: Int
) : ApiRequest<Int>("bill.donate") {
    init {
        addParam("uid", uid)
        addParam("amount", amount)
        addParam("fund_id", fundId)
    }

    override fun parse(response: JSONObject): Int {
        return response.optInt("response")
    }
}