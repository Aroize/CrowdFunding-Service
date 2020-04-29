package ru.ifmo.client.api

import java.lang.RuntimeException

class NoBodyException @JvmOverloads constructor(message: String = "Response has no body") : RuntimeException(message)