package nl.rubensten.utensils.math

import kotlin.math.abs

/**
 * @author Sten Wessel
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

inline fun approximateContinuedFraction(
        a: (Int) -> Double, b: (Int) -> Double,
        maxIndex: Int = Integer.MAX_VALUE, precision: Double = DEFAULT_EPSILON
): Double {
    return 0.0
}
