package ru.ifmo.client.api

import org.json.JSONObject

interface ApiCommand<T> {
    fun execute(): String

    fun parse(response: JSONObject): T

    fun parse(response: String): T
}