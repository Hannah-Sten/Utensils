package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.probability.ProbabilityDistribution

/**
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
}
