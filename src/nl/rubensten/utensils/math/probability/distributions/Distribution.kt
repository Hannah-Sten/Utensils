package nl.rubensten.utensils.math.probability.distributions

import java.util.*

/**
 * Specification of a probability distribution over domain [N].
 *
 * @author Sten Wessel
 */
interface Distribution<N : Number> {

    /**
     * The computed mean of the distribution.
     */
    val mean: Double

    /**
     * The computed variance of the distribution.
     */
    val variance: Double

    /**
     * Returns the value of the cumulative distribution function (CDF) at [x].
     *
     * When [x] lies below the support domain, _0_ is returned.
     * When [x] lies above the support domain, _1_ is returned.
     * The result will be a probability within the range _`[`0, 1`]`_.
     */
    fun cumulativeProbability(x: N): Double

    /**
     * Generates a sample from this distribution based on a random sample from [generator].
     */
    fun sample(generator: Random = Random()): N

}
