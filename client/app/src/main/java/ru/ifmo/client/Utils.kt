package ru.ifmo.client

import org.json.JSONArray

fun JSONArray.forEach(func: (Any) -> Unit) {
    for (i in 0 until length()) {
        func(get(i))
    }
}