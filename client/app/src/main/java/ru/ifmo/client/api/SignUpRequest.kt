package ru.ifmo.client.api

import org.json.JSONObject
import ru.ifmo.client.data.models.User

internal class SignUpRequest(
    login: String,
    password: String
) : ApiRequest<User>("auth.signUp") {
    init {
        addParam("login", login)
        addParam("hashed_pwd", password)
    }
    override fun parse(response: JSONObject) = User.parse(response)

}