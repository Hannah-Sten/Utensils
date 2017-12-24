package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.math.matrix.Vector

/**
 *
 * @author Sten Wessel
 */
interface LPSolver<T : Comparable<T>> {

    fun solve(): LPSolution<T>
}

data class LPSolution<T : Comparable<T>>(val value: T, val vector: Vector<T>)
