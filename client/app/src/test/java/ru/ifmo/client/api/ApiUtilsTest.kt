package ru.ifmo.client.api

import org.junit.Test

import org.junit.Assert.*

class ApiUtilsTest {
    @Test
    fun testMD5() {
        val testCases = arrayListOf(
            "Aroize" to "1c2da765aafa6fe111d6d9b41f1d6f22",
            "MessageDigest.getInstance" to "36dc2474bab41fddd36667d39aa80c6e",
            "ApiUtilsTest" to "2efa4efc27a455c3a86d05314729b4bb",
            "function md5()" to "b1131abe22277e066731346d53b953f8",
            "test with digits 1 4 123" to "15f0c3c2fef87b9a43c81c41a6a72c05"
        )
        testCases.forEach {
            assertEquals(it.second, it.first.md5())
        }
    }
}