package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.probability.ProbabilityDistribution

/**
 * Erlang (continuous) probability distribution.
 *
 * @property shape the shape parameter. Must be positive.
 * @property rate the rate parameter. Must be positive.
 *
 * @author Sten Wessel
 */
class ErlangDistribution(val shape: Int, val rate: Double) :
        ProbabilityDistribution<Double> by GammaDistribution(shape.toDouble(), 1 / rate) {

    operator fun times(a: Double) = ErlangDistribution(shape, rate / a)

    operator fun plus(other: ErlangDistribution): ErlangDistribution {
        require(rate == other.rate) { "Rate parameters must be equal." }

        return ErlangDistribution(shape + other.shape, rate)
    }

    operator fun plus(exp: ExponentialDistribution): ErlangDistribution {
        require(rate == exp.lambda) { "Rate parameters must be equal." }

        return ErlangDistribution(shape + 1, rate)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ErlangDistribution

        if (shape != other.shape) return false
        if (rate != other.rate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shape
        result = 31 * result + rate.hashCode()
        return result
    }

    override fun toString() = "ErlangDistribution(shape=$shape, rate=$rate)"
}
