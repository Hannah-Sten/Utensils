package nl.rubensten.utensils.math.probability.distributions

/**
 * Specification of a continuous probability distribution.
 *
 * @author Sten Wessel
 */
interface DiscreteDistribution : Distribution<Long> {

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
}
