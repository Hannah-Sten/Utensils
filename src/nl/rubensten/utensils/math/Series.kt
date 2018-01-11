package nl.rubensten.utensils.math

import kotlin.math.abs

/**
 * Approximate an infinite series _Σ`_`{n=[start]}^∞ [term]`(`n`)`_ with the series
 * _Σ`_`{n=[start]}^{[maxIndex]} [term]`(`n`)`_.
 *
 * It will stop the approximation when the ratio of the current sum term _[term]`(`n`)`_ over the current partial sum is
 * has reached the desired [precision].
 */
inline fun approximateInfiniteSeries(
        start: Int = 1, maxIndex: Int = Integer.MAX_VALUE, precision: Double = DEFAULT_EPSILON,
        term: (Int) -> Double
): Double {
    var n = start
    var a_n = term(n)
    var sum = a_n

    while (abs(a_n / sum) > precision && n < maxIndex && sum < Double.POSITIVE_INFINITY) {
        a_n = term(++n)
        sum += a_n
    }

    return sum
}
