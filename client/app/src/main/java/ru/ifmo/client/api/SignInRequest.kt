package ru.ifmo.client.api

import org.json.JSONObject

internal class SignInRequest(
    login: String,
    password: String
) : ApiRequest<AccessToken>("auth.signIn") {
    init {
        addParam("login", login)
        addParam("hashed_pwd", password)
    }
    override fun parse(response: JSONObject) = AccessToken.parse(response.getJSONObject("response"))
}