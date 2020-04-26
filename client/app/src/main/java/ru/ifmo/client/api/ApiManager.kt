package ru.ifmo.client.api

import org.json.JSONObject
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.function.Supplier

object ApiManager {

    private val executor = Executors.newSingleThreadExecutor()

    fun <T> executeSync(request: ApiCommand<T>): T {
        try {
            var response = request.execute()
            response = handleResponse<T>(response, null, null)
            return validateResult(response, request, null)!!
        } catch (e: Throwable) {
            handleResponse<T>(null, e, null)
        }
        throw IllegalStateException("Exception must be thrown")
    }

    fun <T> execute(request: ApiRequest<T>, callback: ApiCallback<T>) {
        CompletableFuture
            .supplyAsync (Supplier { request.execute() }, executor)
            .handle { response, e -> handleResponse(response, e, callback) }
            .thenAccept { result -> validateResult(result, request, callback) }
    }

    private fun <T> handleResponse(response: String?, e: Throwable?, callback: ApiCallback<T>?): String {
        if (response == null && e != null) {
            if (callback != null)
                callback.onError(e)
            else
                throw e
        } else {
            return response as String

        }
        return ""
    }

    private fun <T> validateResult(
        result: String,
        request: ApiCommand<T>,
        callback: ApiCallback<T>?
    ): T? {
        val response = JSONObject(result)
        if (response.has("error_code")) {
            //Server returned error response
            //We should wrap it and send out exception
            val e = ApiException(response.getString("failure_msg"))
            if (callback != null)
                callback.onError(e)
            else
                throw e
        } else {
            val parsed = request.parse(response)
            callback?.onSuccess(parsed)
            return parsed
        }
        return null
    }
}