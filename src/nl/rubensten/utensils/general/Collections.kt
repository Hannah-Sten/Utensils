package nl.rubensten.utensils.general

import java.util.*

/**
 * Given a collection, apply an iterator action to every set of 2 elements from the collection.
 *
 * Iterates (`size nCr 2`) times.
 */
fun <T> Collection<T>.forEachPair(iterator: (Pair<T, T>) -> Unit) {
    val queue = ArrayDeque<T>(this)
    while (queue.size >= 2) {
        val first = queue.removeFirst()
        queue.forEach {
            iterator(first to it)
        }
    }
}

/**
 * Given a collection, apply an iterator action to every set of `size` elements from the collection.
 *
 * Iterates (`collectionSize nCr size`) times.
 */
fun <T> Collection<T>.forEachTuple(size: Int, iterator: (Set<T>) -> Unit) {
    require(size >= 2) { "Size must be > 2, got $size" }

    val queue = ArrayDeque<T>(this)
    while (queue.size >= 2) {
        val first = queue.removeFirst()
        if (size == 2) {
            queue.forEach {
                iterator(setOf(first, it))
            }
        }
        else {
            queue.forEachTuple(size - 1) { iterator(it + first) }
        }
    }
}

/**
 * Checks if the pair `(key, value)` is present in the map.
 *
 * Does not work well for null values.
 */
fun <K, V> Map<K, V>.containsBoth(key: K, value: V) = this[key] == value