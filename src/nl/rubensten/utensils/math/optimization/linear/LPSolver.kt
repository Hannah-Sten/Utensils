package nl.rubensten.utensils.math.optimization.linear

/**
 *
 * @author Sten Wessel
 */
interface LPSolver<T : Comparable<T>> {

    fun solve(): T
}
