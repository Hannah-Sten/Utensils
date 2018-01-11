package nl.rubensten.utensils.math.probability.distributions.discrete

import nl.rubensten.utensils.math.choose
import nl.rubensten.utensils.math.probability.distributions.DiscreteDistribution
import kotlin.math.pow

/**
 * Binomial (discrete) probability distribution.
 *
 * @property n number of success trials.
 * @property p success probability.
 *
 * @author Sten Wessel
 */
class BinomialDistribution(val n: Int, val p: Double) : DiscreteDistribution {

    init {
        require(n >= 0) { "The number of trials may not be negative." }
        require(p in 0.0..1.0) { "The parameter p must be a probability." }
    }

    override val supportLowerBound = 0
    override val supportUpperBound = n

    override val mean by lazy { n * p }

    override val variance by lazy { n * p * (1 - p) }

    override fun mass(x: Int) = when (x) {
        in 0..n -> (n choose x) * p.pow(x) * (1 - p).pow(n - x)
        else -> 0.0
    }

    operator fun plus(other: BinomialDistribution): BinomialDistribution {
        require(this.p == other.p) { "Success probabilities must be equal." }
        return BinomialDistribution(this.n + other.n, p)
    }

    operator fun plus(bernoulli: BernoulliDistribution) = this + bernoulli.toBinomialDistribution()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BinomialDistribution

        if (n != other.n) return false
        if (p != other.p) return false

        return true
    }

    override fun hashCode(): Int {
        var result = n
        result = 31 * result + p.hashCode()
        return result
    }

    override fun toString() = "BinomialDistribution($n, $p)"
}
