package nl.rubensten.utensils.math.probability.distributions

import nl.rubensten.utensils.general.lowerBinarySearchBy

/**
 * Specification of a continuous probability distribution.
 *
 * @author Sten Wessel
 */
interface DiscreteDistribution : Distribution<Int> {

    val supportLowerBound: Int?
        get() = null

    val supportUpperBound: Int?
        get() = null

    /**
     * Returns the value of the mass function (PMF) at [x].
     *
     * When [x] lies outside of the support domain, _0_ is returned.
     * The returned value will be a probability within the range _`[`0, 1`]`_.
     */
    fun mass(x: Int): Double

    override fun cumulativeProbability(x: Int): Double {
        if (supportLowerBound?.let { x <= it } == true) return 0.0
        if (supportUpperBound?.let { x >= it } == true) return 1.0

        if (supportLowerBound == null && supportUpperBound == null)
            throw NotImplementedError("Not implemented for unbounded support intervals.")

        if (supportLowerBound != null) {
            if (supportUpperBound?.let { it - x <= supportLowerBound!! - x } == true) {
                return 1 - (x..supportUpperBound!!).sumByDouble { mass(it) }
            }
            return (supportLowerBound!!..x).sumByDouble { mass(it) }
        }

        return 1 - (x..supportUpperBound!!).sumByDouble { mass(it) }
    }

    override fun inverseCumulativeProbability(p: Double): Int {
        require(p in 0.0..1.0) { "Argument must be a probability." }

        if (p == 0.0) return supportLowerBound ?: Int.MIN_VALUE
        if (p == 1.0) return supportUpperBound ?: Int.MAX_VALUE

        val lower = supportLowerBound?.takeIf { it >= Int.MIN_VALUE / 2 + 1 } ?: (Int.MIN_VALUE / 2 + 1)
        val upper = supportUpperBound?.takeIf { it <= Int.MAX_VALUE / 2 } ?: (Int.MAX_VALUE / 2)

        return (lower..upper).lowerBinarySearchBy(p) { x -> cumulativeProbability(x) }
    }
}
