package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.probability.ProbabilityDistribution
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow

/**
 *
 * @author Sten Wessel
 */
class ExponentialDistribution(val lambda: Double) : ProbabilityDistribution<Double> {

    init {
        require(lambda > 0) { "Parameter lambda must be positive." }
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
}
