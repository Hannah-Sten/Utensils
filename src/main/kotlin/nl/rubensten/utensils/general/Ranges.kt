package nl.rubensten.utensils.general

/**
 * Get the length of the [IntRange].
 * For example the range `2..5` has length `4` as it has the numbers `2, 3, 4, 5`.
 */
val IntRange.length
    get() = endInclusive - start + 1