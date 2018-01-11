package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.probability.distributions.ContinuousDistribution

/**
 * Erlang (continuous) probability distribution.
 *
 * @property shape the shape parameter. Must be positive.
 * @property rate the rate parameter. Must be positive.
 *
 * @author Sten Wessel
 */
class ErlangDistribution private constructor(val shape: Int, val rate: Double, private val gamma: GammaDistribution) :
    ContinuousDistribution by gamma {

    constructor(shape: Int, rate: Double) : this(shape, rate, GammaDistribution(shape.toDouble(), 1 / rate))

    operator fun times(a: Double) = ErlangDistribution(shape, rate / a)

    operator fun plus(other: ErlangDistribution): ErlangDistribution {
        require(rate == other.rate) { "Rate parameters must be equal." }

        return ErlangDistribution(shape + other.shape, rate)
    }

    operator fun plus(exp: ExponentialDistribution) = this + exp.toErlangDistribution()

    fun toGammaDistribution() = gamma

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
