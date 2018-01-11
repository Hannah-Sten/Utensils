package nl.rubensten.utensils.math.probability.distributions

import java.util.*

/**
 * Specification of a continuous probability distribution.
 *
 * @author Sten Wessel
 */
interface ContinuousDistribution : Distribution<Double> {

    /**
     * Returns the value of the density function (PDF) at [x].
     *
     * When [x] lies outside of the support domain, _0_ is returned.
     * The returned value will be a probability within the range _`[`0, 1`]`_.
     */
    fun density(x: Double): Double

    /**
     * Returns the value _x_ for which _CDF(x) = [p]_.
     *
     * @param p a probability within the range _`[`0, 1`]`_.
     *
     * @see cumulativeProbability
     */
    fun inverseCumulativeProbability(p: Double): Double

    /**
     * Generates a sample from this distribution based on a random sample from [generator].
     *
     * The default implementation uses the
     * [inverse transform sampling](https://en.wikipedia.org/wiki/Inverse_transform_sampling) method.
     */
    override fun sample(generator: Random) = inverseCumulativeProbability(generator.nextDouble())

}
