package nl.rubensten.utensils.math.probability.distributions.discrete

import nl.rubensten.utensils.math.probability.distributions.DiscreteDistribution
import kotlin.math.pow

/**
 * Geometric (discrete) probability distribution with success probability [p].
 *
 * This distribution represents the number of Bernoulli trials needed to get one success.
 * The support set is _{1, 2, 3, ...}_.
 *
 * @property p success probability.
 *
 * @author Sten Wessel
 */
class GeometricDistribution(val p: Double) : DiscreteDistribution {

    init {
        require(p in 0.0..1.0) { "The parameter p must be a probability." }
    }

    override val supportLowerBound = 1

    override val mean by lazy { 1 / p }

    override val variance by lazy { (1 - p) / p / p }

    override fun mass(x: Int) = when {
        x >= 1 -> (1 - p).pow(x - 1) * p
        else -> 0.0
    }

    override fun cumulativeProbability(x: Int) = when {
        x < 1 -> 0.0
        else -> 1 - (1 - p).pow(x)
    }

    operator fun plus(other: GeometricDistribution): NegativeBinomialDistribution {
        return this.toNegativeBinomialDistribution() + other.toNegativeBinomialDistribution()
    }

    operator fun plus(negBinom: NegativeBinomialDistribution) = negBinom + this.toNegativeBinomialDistribution()

    fun toNegativeBinomialDistribution() = NegativeBinomialDistribution(1, p)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeometricDistribution

        if (p != other.p) return false

        return true
    }

    override fun hashCode(): Int {
        return p.hashCode()
    }

    override fun toString() = "GeometricDistribution($p)"

}

fun min(vararg geos: GeometricDistribution): GeometricDistribution {
    return GeometricDistribution(1 - geos.map { it.p }.reduce { p1, p2 -> (1 - p1) * (1 - p2) })
}

fun iidSum(times: Int, geo: GeometricDistribution) = NegativeBinomialDistribution(times, geo.p)
