package nl.rubensten.utensils.math.probability

import java.util.*

/**
 *
 * @author Sten Wessel
 */
interface ProbabilityDistribution<N : Comparable<N>> {

    val supportLowerBound: N
    val supportUpperBound: N

    val mean: N
    val variance: N

    fun density(x: N): Double

    fun cumulativeProbability(x: N): Double

    fun inverseCumulativeProbability(p: Double): N

    fun sample(generator: Random = Random()): N {
        return inverseCumulativeProbability(generator.nextDouble())
    }

}
