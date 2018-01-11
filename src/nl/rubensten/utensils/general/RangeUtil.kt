package nl.rubensten.utensils.general


/**
 * Find the smallest number _n_ in the range for which _[comparator]__(n) <= [element]_.
 *
 * The function [comparator] must be increasing.
 *
 * @author Sten Wessel
 */
fun <T : Comparable<T>> LongRange.lowerBinarySearchBy(element: T, comparator: (Long) -> T): Long {
    var lower = this.start
    var upper = this.endInclusive

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
