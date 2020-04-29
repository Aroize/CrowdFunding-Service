package ru.ifmo.client.api

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.lang.Exception
import java.lang.RuntimeException

abstract class ApiRequest<T>(override val method: String) : ApiCommand<T> {

    private val parameters: MutableMap<String, String> = HashMap()

    fun addParam(key: String, value: String) { parameters[key] = value }

    fun addParam(key: String, value: Int) = addParam(key, value.toString())

    fun addParam(params: Map<String, String>) { parameters.putAll(params) }

    override fun execute(): String {
        val client = OkHttpClient()
        val url = createUrl()
        val request = Request.Builder()
            .url(url)
            .build()
        try {
            val response = client.newCall(request).execute().body
            if (response == null)
                throw NoBodyException()
            else
                return response.string()
        } catch (e: Exception) {
            throw RuntimeException("Exception while executing request", e)
        }
    }

    private fun createUrl(): HttpUrl {
        val urlBuilder = HttpUrl.Builder()
        urlBuilder
            .scheme(ApiConfig.SCHEME)
            .host(ApiConfig.HOST)
            .port(ApiConfig.PORT)
            .addPathSegment(method)
        val iterator = parameters.iterator()
        while (iterator.hasNext()) {
            val keyValue = iterator.next()
            urlBuilder.addQueryParameter(keyValue.key, keyValue.value)
        }
        return urlBuilder.build()
    }

    override fun parse(response: String): T {
        return parse(JSONObject(response))
    }

}