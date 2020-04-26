package ru.ifmo.client.api

import org.json.JSONObject

interface ApiCommand<T> {

    val method: String

    fun execute(): String

    fun parse(response: JSONObject): T

    fun parse(response: String): T
}