package ru.ifmo.client

import org.json.JSONArray

fun JSONArray.forEach(func: (Any) -> Unit) {
    for (i in 0 until length()) {
        func(get(i))
    }
}

/**
 * Splits iterable to two collections
 * <p>
 * @return pair of two collections: first collection satisfies predicate, second doesn't satisfies
 */
fun <T> Iterable<T>.split(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val first = arrayListOf<T>()
    val second = arrayListOf<T>()
    forEach {
        if (predicate(it))
            first.add(it)
        else
            second.add(it)
    }
    return first to second
}