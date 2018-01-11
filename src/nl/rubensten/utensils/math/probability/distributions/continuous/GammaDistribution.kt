package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.gamma
import nl.rubensten.utensils.math.inverseRegularizedGammaP
import nl.rubensten.utensils.math.probability.distributions.ContinuousDistribution
import nl.rubensten.utensils.math.regularizedGammaP
import kotlin.math.exp
import kotlin.math.pow

/**
 * Gamma (continuous) probability distribution.
 *
 * @property shape the shape parameter. Must be positive.
 * @property rate the rate parameter. Must be positive.
 *
 * @author Sten Wessel
 */
class GammaDistribution(val shape: Double, val scale: Double) : ContinuousDistribution {

    init {
        require(shape > 0) { "Shape parameter must be positive." }
        require(scale > 0) { "Scale parameter must be positive." }
    }

    /**
     * Rate parameter.
     */
    val rate by lazy { 1 / scale }

    override val mean by lazy { shape * scale }

    override val variance by lazy { shape * scale.pow(2) }

    override fun density(x: Double) = when {
        x > 0 -> x.pow(shape - 1) * exp(-x / scale) / scale.pow(shape) / gamma(shape)
        else -> 0.0
    }

    override fun cumulativeProbability(x: Double) = regularizedGammaP(shape, x / scale)

    override fun inverseCumulativeProbability(p: Double) = inverseRegularizedGammaP(shape, p)

    operator fun times(c: Double) = GammaDistribution(shape, c * scale)

    operator fun plus(other: GammaDistribution): GammaDistribution {
        require(this.scale == other.scale) { "Scale parameters must be equal." }

        return GammaDistribution(this.shape + other.shape, scale)
    }

}
