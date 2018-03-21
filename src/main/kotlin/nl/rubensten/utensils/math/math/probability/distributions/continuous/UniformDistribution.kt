package nl.rubensten.utensils.math.probability.distributions.continuous

import nl.rubensten.utensils.math.probability.distributions.ContinuousDistribution
import kotlin.math.pow

/**
 * Continuous uniform probability distribution.
 *
 * @property a the support lower bound.
 * @property b the support upper bound.
 *
 * @author Sten Wessel
 */
class UniformDistribution(val a: Double = 0.0, val b: Double = 1.0) : ContinuousDistribution {

    init {
        require(a.isFinite() && b.isFinite()) { "Parameters a and b must be finite." }
        require(b >= a) { "Interval has a negative length." }
    }

    override val mean by lazy { 0.5 * (a + b) }

    override val variance by lazy { (b - a).pow(2) / 12 }

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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UniformDistribution

        if (a != other.a) return false
        if (b != other.b) return false

        return true
    }

    override fun hashCode(): Int {
        var result = a.hashCode()
        result = 31 * result + b.hashCode()
        return result
    }

    override fun toString() = "UniformDistribution[$a, $b]"

}
