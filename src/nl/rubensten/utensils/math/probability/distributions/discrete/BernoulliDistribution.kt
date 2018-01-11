package nl.rubensten.utensils.math.probability.distributions.discrete

import nl.rubensten.utensils.math.probability.distributions.DiscreteDistribution
import java.util.*

/**
 * Bernoulli (discrete) probability distribution.
 *
 * @property p success probability.
 *
 * @author Sten Wessel
 */
class BernoulliDistribution(val p: Double) : DiscreteDistribution {

    override val mean by lazy { p }

    override val variance by lazy { p * (1 - p) }

    override fun mass(x: Long) = when (x) {
        0L -> 1 - p
        1L -> p
        else -> 0.0
    }

    override fun cumulativeProbability(x: Long) = when {
        x < 0 -> 0.0
        x >= 1 -> 1.0
        else -> 1 - p
    }

    override fun sample(generator: Random) = if (generator.nextDouble() <= p) 1L else 0L

}
