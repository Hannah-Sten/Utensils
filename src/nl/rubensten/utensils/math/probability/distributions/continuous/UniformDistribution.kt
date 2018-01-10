package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.probability.ProbabilityDistribution
import kotlin.math.pow

/**
 *
 * @author Sten Wessel
 */
class UniformDistribution(val a: Double = 0.0, val b: Double = 1.0) : ProbabilityDistribution<Double> {

    init {
        require(a.isFinite() && b.isFinite()) { "Parameters a and b must be finite." }
    }

    override val supportLowerBound = a
    override val supportUpperBound = b

    override val mean by lazy { 0.5 * (a + b) }

    override val variance by lazy { 1 / 12 * (b - a).pow(2) }

    override fun density(x: Double) = when (x) {
        in a..b -> 1 / (b - a)
        else -> 0.0
    }

    override fun cumulativeProbability(x: Double) = when {
        x < a -> 0.0
        x > b -> 1.0
        else -> (x - a) / (b - a)
    }

    override fun inverseCumulativeProbability(p: Double): Double {
        require(p in 0.0..1.0) { "Argument must be a probability." }

        return a + p * (b - a)
    }

}
