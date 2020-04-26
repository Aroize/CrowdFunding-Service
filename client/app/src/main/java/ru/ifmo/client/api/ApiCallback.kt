package ru.ifmo.client.api

interface ApiCallback<T> {
    fun onSuccess(result: T)

    fun onError(e: Throwable)
}