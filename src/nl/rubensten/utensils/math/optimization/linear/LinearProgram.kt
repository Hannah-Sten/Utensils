package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.math.matrix.OperationSet
import nl.rubensten.utensils.math.matrix.Vector

/**
 * A generic form of a linear program.
 *
 * @param T the type of the variables in the program.
 *
 * @property goalFunction The linear function that is maximized.
 * @property operationSet The set of operations that belong to [T].
 * @property negated Whether the value of the goal function needs to be negated (for minimization problems).
 *
 * @constructor Defines a linear program that maximizes the goal function. When the
 *
 * @author Sten Wessel
 */
open class LinearProgram<T : Comparable<T>> private constructor(val goalFunction: LinearFunction<T>,
                                                                val operationSet: OperationSet<T>,
                                                                val negated: Boolean = false) {

    companion object {

        /**
         * Create a new maximization problem.
         *
         * @param goalFunction The linear function to maximize.
         * @param operationSet The set of operations that belong to [T].
         * @param init Initialization function to add variables and constraints.
         *
         * @return A linear program corresponding to the supplied problem.
         */
        fun <T : Comparable<T>> maximize(goalFunction: LinearFunction<T>, operationSet: OperationSet<T>,
                                         init: LinearProgram<T>.() -> Unit): LinearProgram<T> {
            return LinearProgram(goalFunction, operationSet).also(init)
        }

        /**
         * Create a new minimization problem.
         *
         * @param goalFunction The linear function to minimize.
         * @param operationSet The set of operations that belong to [T].
         * @param init Initialization function to add variables and constraints.
         *
         * @return A linear program corresponding to the supplied problem.
         */
        fun <T : Comparable<T>> minimize(goalFunction: LinearFunction<T>, operationSet: OperationSet<T>,
                                         init: LinearProgram<T>.() -> Unit): LinearProgram<T> {
            return LinearProgram(-goalFunction, operationSet, negated = true).also(init)
        }
    }

    /**
     * The set of variables.
     *
     * The actual value of the elements in the vector are not used and can be anything initially.
     */
    val variables: MutableSet<Vector<T>> = mutableSetOf()

    /**
     * The set of constraints.
     */
    val constraints: MutableSet<LinearConstraint<T>> = mutableSetOf()
}
