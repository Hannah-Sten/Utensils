package nl.rubensten.utensils.math.probability.distributions

import nl.rubensten.utensils.general.lowerBinarySearchBy

/**
 * Specification of a continuous probability distribution.
 *
 * @author Sten Wessel
 */
interface DiscreteDistribution : Distribution<Int> {

    val supportLowerBound: Int
        get() = Int.MIN_VALUE

    val supportUpperBound: Int
        get() = Int.MAX_VALUE

    /**
     * Returns the value of the mass function (PMF) at [x].
     *
     * When [x] lies outside of the support domain, _0_ is returned.
     * The returned value will be a probability within the range _`[`0, 1`]`_.
     */
    fun mass(x: Int): Double

    override fun cumulativeProbability(x: Int): Double {
        if (x <= supportLowerBound) return 0.0
        if (x >= supportUpperBound) return 1.0

        // Check whether summing form the right side is less expensive
        if (supportUpperBound - x < x - supportLowerBound) {
            return 1 - (x..supportUpperBound).sumByDouble { mass(it) }
        }

        return (supportLowerBound..x).sumByDouble { mass(it) }

    }

    override fun inverseCumulativeProbability(p: Double): Int {
        require(p in 0.0..1.0) { "Argument must be a probability." }

        if (p == 0.0) return supportLowerBound
        if (p == 1.0) return supportUpperBound

        val lower = supportLowerBound.takeIf { it >= Int.MIN_VALUE / 2 + 1 } ?: (Int.MIN_VALUE / 2 + 1)
        val upper = supportUpperBound.takeIf { it <= Int.MAX_VALUE / 2 } ?: (Int.MAX_VALUE / 2)

        return (lower..upper).lowerBinarySearchBy(p) { x -> cumulativeProbability(x) }
    }
}
