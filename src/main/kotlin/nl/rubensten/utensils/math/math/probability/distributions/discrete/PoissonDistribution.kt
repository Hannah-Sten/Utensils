package nl.rubensten.utensils.math.probability.distributions.discrete

import nl.rubensten.utensils.math.factorial
import nl.rubensten.utensils.math.probability.distributions.DiscreteDistribution
import nl.rubensten.utensils.math.regularizedGammaP
import kotlin.math.exp
import kotlin.math.pow

/**
 * Poisson (discrete) probability distribution.
 *
 * @property lambda rate parameter.
 *
 * @author Sten Wessel
 */
class PoissonDistribution(val lambda: Double) : DiscreteDistribution {

    init {
        require(lambda > 0) { "The parameter lambda must be strictly positive." }
    }

    override val supportLowerBound = 0

    override val mean by lazy { lambda }

    override val variance by lazy { lambda }

    override fun mass(x: Int) = when {
        x < 0 -> 0.0
        else -> lambda.pow(x) * exp(-lambda) / x.factorial().longValueExact()
    }

    override fun cumulativeProbability(x: Int) = when {
        x < 0 -> 0.0
        else -> 1 - regularizedGammaP(x + 1.0, lambda)
    }

    operator fun plus(other: PoissonDistribution) = PoissonDistribution(this.lambda + other.lambda)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PoissonDistribution

        if (lambda != other.lambda) return false

        return true
    }

    override fun hashCode(): Int {
        return lambda.hashCode()
    }

    override fun toString() = "PoissonDistribution($lambda)"

}
