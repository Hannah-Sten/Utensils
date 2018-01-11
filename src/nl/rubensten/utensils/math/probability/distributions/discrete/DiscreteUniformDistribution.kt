package nl.rubensten.utensils.math.probability.distributions.discrete

import nl.rubensten.utensils.math.probability.distributions.DiscreteDistribution
import kotlin.math.pow

/**
 * Discrete uniform probability distribution over the set of values _{[a], ..., [b]}_.
 *
 * @property a support lower bound.
 * @property b support upper bound.
 *
 * @author Sten Wessel
 */
class DiscreteUniformDistribution(val a: Int, val b: Int) : DiscreteDistribution {

    init {
        require(b >= a) { "Interval has a negative length." }
    }

    override val supportLowerBound = a
    override val supportUpperBound = b

    override val mean by lazy { 0.5 * (a + b) }

    override val variance by lazy { ((b - a + 1).toDouble().pow(2) - 1) / 12 }

    override fun mass(x: Int) = when (x) {
        in a..b -> 1.0 / (b - a + 1)
        else -> 0.0
    }

    override fun cumulativeProbability(x: Int) = when {
        x <= a -> 0.0
        x >= b -> 1.0
        else -> (x - a + 1).toDouble() / (b - a + 1)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiscreteUniformDistribution

        if (a != other.a) return false
        if (b != other.b) return false

        return true
    }

    override fun hashCode(): Int {
        var result = a
        result = 31 * result + b
        return result
    }

    override fun toString() = "DiscreteUniformDistribution{$a, $b}"

}
