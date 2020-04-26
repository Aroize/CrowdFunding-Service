package ru.ifmo.client.api

import java.lang.RuntimeException

class ApiException @JvmOverloads constructor(message: String? = "Error from server") : RuntimeException(message) {
}