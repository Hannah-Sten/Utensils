package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.math.matrix.*

/**
 *
 * @author Sten Wessel
 */
/**
 * Represents a generic linear expression `A x + B y + C z + ...`.
 */
class LinearExpression<T : Comparable<T>>(val terms: List<Pair<Matrix<T>, Vector<T>>>) {

    companion object {
        fun <T : Comparable<T>> zero(dimension: Int, op: OperationSet<T>): LinearExpression<T> {
            return LinearExpression(GenericVector(op, dimension) { op.zero })
        }
    }

    constructor(vararg terms: Pair<Matrix<T>, Vector<T>>) : this(terms.toList())

    constructor(vector: Vector<T>) : this(listOf(MatrixUtils.identity(vector.size(), vector.operations()) to vector))

    init {
        require(terms.isNotEmpty()) { "At least one term must be specified." }
        require(terms.all { (A, x) -> A.width() == x.size() }) { "Matrix and vector sizes are incompatible." }
        require(terms.map { (A, _) -> A.height() }.distinct().count() == 1) { "Term sizes are incompatible." }
    }

    val size
        get() = terms.size

    val isUnit
        get() = size == 1

    val dimension
        get() = terms[0].first.height()

    fun evaluate() = terms.map { (A, x) -> A * x }.reduce { sum, x -> sum + x }

    operator fun plus(that: LinearExpression<T>) = LinearExpression(this.terms + that.terms)

    operator fun plus(vector: Vector<T>) = this + LinearExpression(vector)

    operator fun minus(other: LinearExpression<T>) = this + -other

    operator fun minus(vector: Vector<T>) = this - LinearExpression(vector)

    operator fun times(scalar: T) = LinearExpression(terms.map { (A, x) -> (A scalar scalar) to x })

    operator fun div(scalar: T) = LinearExpression(terms.map { (A, x) -> (A scalar A.operations().inverse(scalar)) to x })

    operator fun unaryPlus() = this

    operator fun unaryMinus() = LinearExpression(terms.map { (A, x) ->
        val op = A.operations()
        (A scalar op.negate(op.unit)) to x
    })

    operator fun get(index: Int): Pair<Matrix<T>, Vector<T>> = terms[index]

    override fun toString() = terms.map { (A, x) -> "$A * $x" }.reduce { result, s -> "$result + $s" }

    override operator fun equals(other: Any?) = when {
        this === other -> true
        this.evaluate() == other -> true
        else -> false
    }

    override fun hashCode(): Int {
        return terms.hashCode()
    }
}
