package nl.rubensten.utensils.math.optimization.linear

import nl.rubensten.utensils.math.matrix.GenericVector
import nl.rubensten.utensils.math.matrix.OperationSet
import nl.rubensten.utensils.math.matrix.Vector

/**
 *
 * @author Sten Wessel
 */
class LinearFunction<T : Comparable<T>>(val terms: List<Pair<Vector<T>, Vector<T>>>) {

    constructor(vararg terms: Pair<Vector<T>, Vector<T>>) : this(terms.toList())

    constructor(scalar: T, op: OperationSet<T>) : this(listOf(GenericVector<T>(op, op.unit) to GenericVector<T>(op, scalar)))

    init {
        require(terms.isNotEmpty()) { "At least one term must be specified." }
        require(terms.all { (c, x) -> c.size() == x.size() }) { "Vector sizes are incompatible." }
    }

    private val op = terms[0].first.operations()

    fun evaluate(): T {
        return terms.map { (c, x) -> c dot x }.reduce { sum, x -> op.add(sum, x) }
    }

    operator fun plus(that: LinearFunction<T>) = LinearFunction(this.terms + that.terms)

    operator fun plus(scalar: T) = this + LinearFunction(scalar, op)

    operator fun minus(other: LinearFunction<T>) = this + -other

    operator fun minus(scalar: T) = this - LinearFunction(scalar, op)

    operator fun times(scalar: T) = LinearFunction(terms.map { (c, x) -> (c scalar scalar) to x })

    operator fun div(scalar: T) = LinearFunction(terms.map { (c, x) -> (c scalar c.operations().inverse(scalar)) to x })

    operator fun unaryPlus() = this

    operator fun unaryMinus() = LinearFunction(terms.map { (c, x) ->
        val op = c.operations()
        (c scalar op.negate(op.unit)) to x
    })

    override fun toString() = terms.map { (c, x) -> "$c * $x" }.reduce { result, s -> "$result + $s" }

    override operator fun equals(other: Any?) = when {
        this === other -> true
        this.evaluate() == other -> true
        else -> false
    }

    override fun hashCode(): Int {
        return terms.hashCode()
    }
}
