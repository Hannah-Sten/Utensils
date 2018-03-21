package nl.rubensten.utensils.math.probability.distributions

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

}
