package nl.rubensten.utensils.general

/**
 * Checks if the pair `(key, value)` is present in the map.
 *
 * Does not work well for null values.
 */
fun <K, V> Map<K, V>.containsBoth(key: K, value: V) = this[key] == value