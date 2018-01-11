package nl.rubensten.utensils.math.probability.distributions.discrete

import nl.rubensten.utensils.math.choose
import nl.rubensten.utensils.math.probability.distributions.DiscreteDistribution
import kotlin.math.pow

/**
 * Negative binomial (discrete) probability distribution.
 *
 * @property r number of failures.
 * @property p success probability.
 *
 * @author Sten Wessel
 */
class NegativeBinomialDistribution(val r: Int, val p: Double) : DiscreteDistribution {

    init {
        require(r > 0) { "The number of failures must be strictly positive." }
        require(p in 0.0..1.0) { "The parameter p must be a probability." }
    }

    override val supportLowerBound = 0

    override val mean by lazy { p * r / (1 - p) }

    override val variance by lazy { p * r / (1 - p) / (1 - p) }

    override fun mass(x: Int) = when {
        x < 0 -> 0.0
        else -> ((x + r - 1) choose x) * p.pow(x) * (1 - p).pow(r)
    }

    operator fun plus(other: NegativeBinomialDistribution): NegativeBinomialDistribution {
        require(this.p == other.p) { "Success probabilities must be equal." }
        return NegativeBinomialDistribution(this.r + other.r, p)
    }

    operator fun plus(geo: GeometricDistribution): NegativeBinomialDistribution {
        return this + geo.toNegativeBinomialDistribution()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NegativeBinomialDistribution

        if (r != other.r) return false
        if (p != other.p) return false

        return true
    }

    override fun hashCode(): Int {
        var result = r
        result = 31 * result + p.hashCode()
        return result
    }

    override fun toString() = "NegativeBinomialDistribution($r, $p)"

}
