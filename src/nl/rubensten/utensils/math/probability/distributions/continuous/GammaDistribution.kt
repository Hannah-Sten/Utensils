package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.gamma
import nl.rubensten.utensils.math.inverseRegularizedGammaP
import nl.rubensten.utensils.math.probability.ProbabilityDistribution
import nl.rubensten.utensils.math.regularizedGammaP
import kotlin.math.exp
import kotlin.math.pow

/**
 * @author Sten Wessel
 */
class GammaDistribution(val shape: Double, val scale: Double) : ProbabilityDistribution<Double> {

    init {
        require(shape > 0) { "Shape parameter must be positive." }
        require(scale > 0) { "Scale parameter must be positive." }
    }

    val rate by lazy { 1 / scale }

    override val supportLowerBound = 0.0
    override val supportUpperBound = Double.POSITIVE_INFINITY

    override val mean by lazy { shape * scale }

    override val variance by lazy { shape * scale.pow(2) }

    override fun density(x: Double) = when {
        x > 0 -> x.pow(shape - 1) * exp(-x / scale) / scale.pow(shape) / gamma(shape)
        else -> 0.0
    }

    override fun cumulativeProbability(x: Double) = regularizedGammaP(shape, x / scale)

    override fun inverseCumulativeProbability(p: Double) = inverseRegularizedGammaP(shape, p)
}
