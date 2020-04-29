package ru.ifmo.client.api

import java.security.MessageDigest

fun String.md5(): String {
    val digest = MessageDigest.getInstance("MD5").digest(toByteArray())
    return digest.joinToString("") { "%02x".format(it) }.padStart(32, '0')
}