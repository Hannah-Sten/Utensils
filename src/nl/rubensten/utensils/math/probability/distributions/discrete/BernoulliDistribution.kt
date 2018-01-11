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

    override val supportLowerBound = 0
    override val supportUpperBound = 1

    override val mean by lazy { p }

    override val variance by lazy { p * (1 - p) }

    override fun mass(x: Int) = when (x) {
        0 -> 1 - p
        1 -> p
        else -> 0.0
    }

    override fun cumulativeProbability(x: Int) = when {
        x < 0 -> 0.0
        x >= 1 -> 1.0
        else -> 1 - p
    }

    override fun sample(generator: Random) = if (generator.nextDouble() <= p) 1 else 0

    operator fun plus(other: BernoulliDistribution) = this.toBinomialDistribution() + other.toBinomialDistribution()

    fun toBinomialDistribution() = BinomialDistribution(1, p)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BernoulliDistribution

        if (p != other.p) return false

        return true
    }

    override fun hashCode(): Int {
        return p.hashCode()
    }

    override fun toString() = "BernoulliDistribution($p)"

}

fun iidSum(times: Int, bernoulli: BernoulliDistribution) = BinomialDistribution(times, bernoulli.p)
