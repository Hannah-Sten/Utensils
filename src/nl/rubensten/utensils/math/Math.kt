package nl.rubensten.utensils.math

import java.math.BigInteger
import kotlin.math.*

/**
 * Default precision value.
 */
const val DEFAULT_EPSILON = 10e-15

/**
 * Computes the factorial value.
 */
fun BigInteger.factorial(): BigInteger {
    var i = this
    var result = this
    while (i > 1.toBigInteger()) {
        result *= --i
    }

    return result
}

/**
 * Computes the factorial value.
 *
 * @see BigInteger.factorial
 */
fun Long.factorial() = this.toBigInteger().factorial()

/**
 * Computes the factorial value.
 *
 * @see BigInteger.factorial
 */
fun Int.factorial() = this.toBigInteger().factorial()

/**
 * Computes the gamma function for integer arguments, for which _Γ(n) = (n-1)!_.
 */
fun gamma(n: BigInteger) = (n - 1.toBigInteger()).factorial()

/**
 * Computes the gamma function for integer arguments, for which _Γ(n) = (n-1)!_.
 */
fun gamma(n: Long) = gamma(n.toBigInteger())

/**
 * Computes the gamma function for integer arguments, for which _Γ(n) = (n-1)!_.
 */
fun gamma(n: Int) = gamma(n.toBigInteger())

private fun logGamma(x: Double): Double {
    val tmp = (x - 0.5) * ln(x + 4.5) - (x + 4.5)
    val ser = 1 + 76.18009173   / (x + 0) - 86.50532033   / (x + 1)
                + 24.01409822   / (x + 2) - 1.231739516   / (x + 3)
                + 0.00120858003 / (x + 4) - 0.00000536382 / (x + 5)

    return tmp + ln(ser * sqrt(2 * PI))
}

/**
 * Computes the gamma function for non-integer arguments.
 */
fun gamma(x: Double): Double {
    return exp(logGamma(x))
}

/**
 * Gamma distribution cumulative distribution function (CDF).
 */
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

/**
 * Gamma distribution inverse cumulative distribution function.
 */
fun inverseRegularizedGammaP(
        a: Double, p: Double
): Double {
    require(a > 0) { "Parameter a must be positive." }
    require(p in 0.0..1.0) { "Parameter p must be a probability." }

    if (p == 0.0) return 0.0
    if (p == 1.0) return Double.POSITIVE_INFINITY

    TODO("Implement according to DiDonato article.")

}
