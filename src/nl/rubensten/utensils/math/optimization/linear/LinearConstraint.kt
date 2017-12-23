package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.math.matrix.Vector

/**
 *
 * @author Sten Wessel
 */
sealed class LinearConstraint<T : Comparable<T>>(val left: LinearExpression<T>, val right: LinearExpression<T>) {

    init {
        require(left.dimension == right.dimension) { "Left and right hand side must have the same dimension." }
    }

    protected abstract val compare: (T, T) -> Boolean

    fun evaluate() = left.evaluate().zip(right.evaluate()).all { (x, y) -> compare(x, y) }

    class Equal<T : Comparable<T>>(left: LinearExpression<T>, right: LinearExpression<T>) : LinearConstraint<T>(left, right) {
        override val compare: (T, T) -> Boolean = { x, y -> x == y }
    }

    class LessThanEqual<T : Comparable<T>>(left: LinearExpression<T>, right: LinearExpression<T>) : LinearConstraint<T>(left, right) {
        override val compare: (T, T) -> Boolean = { x, y -> x <= y }
    }

    class GreaterThanEqual<T : Comparable<T>>(left: LinearExpression<T>, right: LinearExpression<T>) : LinearConstraint<T>(left, right) {
        override val compare: (T, T) -> Boolean = { x, y -> x >= y }
    }
}

infix fun <T : Comparable<T>> LinearExpression<T>.lessThanEqual(other: LinearExpression<T>) = LinearConstraint.LessThanEqual(this, other)

infix fun <T : Comparable<T>> LinearExpression<T>.lessThanEqual(vector: Vector<T>) = this lessThanEqual LinearExpression(vector)

infix fun <T : Comparable<T>> LinearExpression<T>.greaterThanEqual(other: LinearExpression<T>) = LinearConstraint.GreaterThanEqual(this, other)

infix fun <T : Comparable<T>> LinearExpression<T>.greaterThanEqual(vector: Vector<T>) = this greaterThanEqual LinearExpression(vector)

infix fun <T : Comparable<T>> LinearExpression<T>.equalTo(other: LinearExpression<T>) = LinearConstraint.Equal(this, other)

infix fun <T : Comparable<T>> LinearExpression<T>.equalTo(vector: Vector<T>) = this equalTo LinearExpression(vector)
