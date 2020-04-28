package ru.ifmo.client.api

import org.json.JSONObject
import ru.ifmo.client.data.models.User
import java.util.concurrent.CompletableFuture
import java.util.function.Function

object ApiManager {

    private const val TAG = "ApiManager.Tag"

    private val mainExecutor = UIThreadExecutor()

    var cachedInfo: Pair<String, String> = Pair("", "")
        private set

    var accessToken = AccessToken()
        private set

    /**
     * If you use sync method - it is your obligation to control access token expiration
     */
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

    fun <T> execute(request: ApiCommand<T>, callback: ApiCallback<T>) {
        if (verifyRequest(request)) {
            Log.d(TAG, "request is verified")
            CompletableFuture
                .supplyAsync { request.execute() }
                .thenApplyAsync (Function<String, String>{ response -> response }, mainExecutor)
                .whenComplete { response, e ->
                    Log.d(TAG, "request completed with response=$response exception=$e")
                    val handled = handleResponse(response, e, callback)
                    if (handled.isNotEmpty())
                        validateResult(handled, request, callback)
                }
        }
        else {
            Log.d(TAG, "request verification failed")
            val callbackForExecution = object : ApiCallback<AccessToken> {
                override fun onError(e: Throwable) = callback.onError(e)

                override fun onSuccess(result: AccessToken) = execute(request, callback)
            }
            signIn(cachedInfo.first, cachedInfo.second, callbackForExecution)
        }
    }

    @Throws(UnsupportedApiMethod::class)
    private fun <T> verifyRequest(request: ApiCommand<T>): Boolean {
        if (request.method !in ApiConfig.supportedMethods)
            throw UnsupportedApiMethod("${request.method} is not supported")
        if (request.method in ApiConfig.requiresToken && request is ApiRequest<T>) {
            //Check whether access token is available and if it is expired sign in again
            if (accessToken.expireTime <= System.currentTimeMillis())
                return false
            request.addParam("token", accessToken.tokenValue)
        }
        return true
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
        Log.d(TAG, "response validation ---> $result")
        val response = JSONObject(result)
        if (response.has("error_code")) {
            //Server returned error response
            //We should wrap it and send out exception
            Log.d(TAG, "response has error code")
            val e = ApiException(response.getString("failure_msg"))
            if (callback != null)
                callback.onError(e)
            else
                throw e
        } else {
            val parsed = request.parse(response)
            Log.d(TAG, "response is parsed = $response")
            callback?.onSuccess(parsed)
            return parsed
        }
        return null
    }

    /**
     * @param password Must be MD5 Hashed password
     * @param login User's login
     */
    fun signIn(login: String, password: String, callback: ApiCallback<AccessToken>) {
        val request = SignInRequest(login, password)
        val localCallback = object : ApiCallback<AccessToken> {
            override fun onError(e: Throwable)  = callback.onError(e)

            override fun onSuccess(result: AccessToken) {
                accessToken = result
                //Save user info for next token expiration re-login
                cachedInfo = login to password
                callback.onSuccess(accessToken)
            }
        }
        execute(request, localCallback)
    }

    fun isSignIn() = accessToken.tokenValue.isNotEmpty()

    /**
     * @param password Must be MD5 Hashed password
     * @param login User's login
     */
    fun signUp(login: String, password: String, callback: ApiCallback<User>) {
        val request = SignUpRequest(login, password)
        execute(request, callback)
    }
}