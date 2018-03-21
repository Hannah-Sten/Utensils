package nl.rubensten.utensils.general

/**
 * Get the length of the [IntRange].
 * For example the range `2..5` has length `4` as it has the numbers `2, 3, 4, 5`.
 */
val IntRange.length
    get() = endInclusive - start + 1

/**
 * Find the smallest number _n_ in the range for which _[comparator]__(n) <= [element]_.
 *
 * The function [comparator] must be increasing.
 *
 * @author Sten Wessel
 */
fun <T : Comparable<T>> IntRange.lowerBinarySearchBy(element: T, comparator: (Int) -> T): Int {
    var lower = this.first
    var upper = this.last

    var estimation: T

    while (lower < upper) {
        val middle = lower + (upper - lower) / 2
        estimation = comparator(middle)

        if (estimation > element) {
            upper = if (upper == middle) upper - 1 else middle
        }
        else {
            lower = if (lower == middle) lower + 1 else middle
        }
    }

    // Check if the correct point is found
    estimation = comparator(lower)
    while (estimation > element) {
        estimation = comparator(--lower)
    }

    return lower
}
