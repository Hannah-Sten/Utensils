package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.probability.ProbabilityDistribution
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow

/**
 * Exponential (continuous) probability distribution.
 *
 * @property lambda rate parameter (also known as the _inverse scale_ parameter). Must be strictly positive.
 *
 * @author Sten Wessel
 */
class ExponentialDistribution(val lambda: Double) : ProbabilityDistribution<Double> {

    init {
        require(lambda > 0) { "Parameter λ must be strictly positive." }
    }

    override val supportLowerBound = 0.0
    override val supportUpperBound = Double.POSITIVE_INFINITY

    override val mean by lazy { 1 / lambda }

    override val variance by lazy { 1 / lambda.pow(2) }

    override fun density(x: Double) = when {
        x >= 0 -> lambda * exp(-lambda * x)
        else -> 0.0
    }

    override fun cumulativeProbability(x: Double) = when {
        x >= 0 -> 1 - exp(-lambda * x)
        else -> 0.0
    }

    override fun inverseCumulativeProbability(p: Double): Double {
        require(p in 0.0..1.0) { "Argument must be a probability." }

        return -ln(1 - p) / lambda
    }

    operator fun times(k: Double) = ExponentialDistribution(k * lambda)

    operator fun div(k: Double) = ExponentialDistribution(lambda / k)

    operator fun plus(other: ExponentialDistribution): ErlangDistribution {
        require(lambda == other.lambda) { "Distributions must be i.i.d." }

        return ErlangDistribution(2, lambda)
    }

    operator fun plus(other: ErlangDistribution): ErlangDistribution {
        require(lambda == other.rate) { "Distributions must have the same rate (λ)." }

        return ErlangDistribution(other.shape + 1, lambda)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExponentialDistribution

        if (lambda != other.lambda) return false

        return true
    }

    override fun hashCode(): Int {
        return lambda.hashCode()
    }

    override fun toString() = "ExponentialDistribution($lambda)"

}

/**
 * The minimum of multiple [ExponentialDistribution]s gives a new [ExponentialDistribution] with rate the sum of the
 * rates of the given distributions.
 */
fun min(vararg exps: ExponentialDistribution) = ExponentialDistribution(exps.map { it.lambda }.sum())

/**
 * Returns the sum of [times] i.i.d. [ExponentialDistribution]s.
 */
fun iidSum(exp: ExponentialDistribution, times: Int) = ErlangDistribution(times, exp.lambda)
