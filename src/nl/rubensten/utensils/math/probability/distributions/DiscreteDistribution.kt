package nl.rubensten.utensils.math.probability.distributions

import nl.rubensten.utensils.general.lowerBinarySearchBy

/**
 * Specification of a continuous probability distribution.
 *
 * @author Sten Wessel
 */
interface DiscreteDistribution : Distribution<Long> {

    val supportLowerBound: Long?
        get() = null

    val supportUpperBound: Long?
        get() = null

    /**
     * Returns the value of the mass function (PMF) at [x].
     *
     * When [x] lies outside of the support domain, _0_ is returned.
     * The returned value will be a probability within the range _`[`0, 1`]`_.
     */
    fun mass(x: Long): Double

    /**
     * Returns the value of the mass function (PMF) at [x].
     */
    fun mass(x: Int) = mass(x.toLong())

    override fun inverseCumulativeProbability(p: Double): Long {
        val lower = supportLowerBound?.takeIf { it >= Long.MIN_VALUE / 2 + 1 } ?: (Long.MIN_VALUE / 2 + 1)
        val upper = supportUpperBound?.takeIf { it <= Long.MAX_VALUE / 2 } ?: (Long.MAX_VALUE / 2)

        return (lower..upper).lowerBinarySearchBy(p) { x -> cumulativeProbability(x) }
    }
}
