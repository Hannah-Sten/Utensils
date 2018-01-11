package nl.rubensten.utensils.math.probability

import java.util.*

/**
 * Specification of a generic probability distribution over the domain [N].
 *
 * @author Sten Wessel
 */
interface ProbabilityDistribution<N : Comparable<N>> {

    /**
     * Lower bound of the support.
     */
    val supportLowerBound: N

    /**
     * Upper bound of the support.
     */
    val supportUpperBound: N

    /**
     * The computed mean of the distribution.
     */
    val mean: N

    /**
     * The computed variance of the distribution.
     */
    val variance: N

    /**
     * Returns the value of the density function (PDF) at [x].
     *
     * When [x] lies outside of the support domain, _0_ is returned.
     * The returned value will be a probability within the range _`[`0, 1`]`_.
     */
    fun density(x: N): Double

    /**
     * Returns the value of the cumulative distribution function (CDF) at [x].
     *
     * When [x] lies below the support domain, _0_ is returned.
     * When [x] lies above the support domain, _1_ is returned.
     * The result will be a probability within the range _`[`0, 1`]`_.
     */
    fun cumulativeProbability(x: N): Double

    /**
     * Returns the value _x_ for which _CDF(x) = [p]_.
     *
     * @param p a probability within the range _`[`0, 1`]`_.
     *
     * @see cumulativeProbability
     */
    fun inverseCumulativeProbability(p: Double): N

    /**
     * Generates a sample from this distribution based on a random sample from [generator].
     *
     * The default implementation uses the
     * [inverse transform sampling](https://en.wikipedia.org/wiki/Inverse_transform_sampling) method.
     */
    fun sample(generator: Random = Random()): N {
        return inverseCumulativeProbability(generator.nextDouble())
    }

}
