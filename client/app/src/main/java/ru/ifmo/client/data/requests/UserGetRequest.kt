package ru.ifmo.client.data.requests

import org.json.JSONObject
import ru.ifmo.client.api.ApiRequest
import ru.ifmo.client.data.models.User

class UserGetRequest(
    userId: Int
) : ApiRequest<User>("user.get") {

    init {
        addParam("uid", userId)
    }

    override fun parse(response: JSONObject): User {
        return User.parse(response.getJSONObject("response"))
    }
}