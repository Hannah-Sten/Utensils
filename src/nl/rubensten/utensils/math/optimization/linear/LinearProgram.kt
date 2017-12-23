package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.math.matrix.MutableVector
import nl.rubensten.utensils.math.matrix.OperationSet

/**
 *
 * @author Sten Wessel
 */
open class LinearProgram<T : Comparable<T>> private constructor(
        val goalFunction: LinearFunction<T>,
        val operationSet: OperationSet<T>,
        val negated: Boolean = false
) {

    companion object {

        fun <T : Comparable<T>> maximize(goalFunction: LinearFunction<T>, operationSet: OperationSet<T>, init: LinearProgram<T>.() -> Unit) = LinearProgram(goalFunction, operationSet).also(init)

        fun <T : Comparable<T>> minimize(goalFunction: LinearFunction<T>, operationSet: OperationSet<T>, init: LinearProgram<T>.() -> Unit) = LinearProgram(-goalFunction, operationSet, negated = true).also(init)
    }

    val variables: MutableSet<MutableVector<T>> = mutableSetOf()
    val constraints: MutableSet<LinearConstraint<T>> = mutableSetOf()
}
