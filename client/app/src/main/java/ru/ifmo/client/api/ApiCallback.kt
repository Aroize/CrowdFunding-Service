package ru.ifmo.client.api

import java.lang.Exception

interface ApiCallback<T> {
    fun onSuccess(result: T)

    fun onError(e: Throwable)
}