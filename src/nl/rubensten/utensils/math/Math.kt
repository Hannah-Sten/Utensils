package nl.rubensten.utensils.math

import kotlin.math.*

/**
 * @author Sten Wessel
 */
val DEFAULT_EPSILON = 10e-15

fun Int.factorial() = (1L..this).asSequence().reduce { a, b -> a * b }

fun Long.factorial() = (1..this).asSequence().reduce { a, b -> a * b }

fun gamma(n: Long) = (n - 1).factorial()

fun gamma(n: Int) = gamma(n.toLong())

private fun logGamma(x: Double): Double {
    val tmp = (x - 0.5) * ln(x + 4.5) - (x + 4.5)
    val ser = 1 + 76.18009173   / (x + 0) - 86.50532033   / (x + 1)
                + 24.01409822   / (x + 2) - 1.231739516   / (x + 3)
                + 0.00120858003 / (x + 4) - 0.00000536382 / (x + 5)

    return tmp + ln(ser * sqrt(2 * PI))
}

fun gamma(x: Double): Double {
    return exp(logGamma(x))
}

fun regularizedGammaP(
        a: Double, x: Double,
        precision: Double = DEFAULT_EPSILON, maxIndex: Int = Integer.MAX_VALUE
): Double {
    require(a > 0) { "Parameter a must be positive." }
    require(x >= 0) { "Parameter a must be non-negative." }

    if (x == 0.0) return 0.0

    var n = 0
    var a_n = 1 / a
    var sum = a_n

    while (abs(a_n / sum) > precision && n < maxIndex && sum < Double.POSITIVE_INFINITY) {
        n++
        a_n *= (x / (a + n))
        sum += a_n
    }

    return sum
}

fun inverseRegularizedGammaP(
        a: Double, p: Double
): Double {
    require(a > 0) { "Parameter a must be positive." }
    require(p in 0.0..1.0) { "Parameter p must be a probability." }

    if (p == 0.0) return 0.0
    if (p == 1.0) return Double.POSITIVE_INFINITY

    TODO("Implement according to DiDonato article.")

}
