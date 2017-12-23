package nl.rubensten.utensils.math.matrix

/**
 * @param T
 *          The type of object contained in the vector.
 * @author Ruben Schellekens, Sten Wessel
 */
open class GenericVector<T>(

        /**
         * Set of operations of type T.
         */
        protected val op: OperationSet<T>,

        /**
         * Array containing all the elements in the vector.
         */
        protected val elements: MutableList<T>

) : MutableVector<T> {

    /**
     * Creates a new vector with the given elements.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param elements
     *          The elements of the vector.
     */
    constructor(op: OperationSet<T>, vararg elements: T) : this(op, listOf(*elements))

    /**
     * Creates a new vector with the given elements.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param elements
     *          The elements of the vector.
     */
    constructor(op: OperationSet<T>, elements: Collection<T>) : this(op, elements.toMutableList())

    /**
     * Populate a vector with a custom population function.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param size
     *          The size of the vector, i.e. how many elements the vector contains.
     * @param populator
     *          A function that takes the index of the element in the vector (zero-indexed)
     *          and produces the value that must be placed in the vector at the given index.
     */
    constructor(op: OperationSet<T>, size: Int, populator: (Int) -> T) : this(op, (0 until size).map(populator))

    override fun size() = elements.size

    override fun operations() = op

    override fun perpendicularProjectionTo(vararg others: Vector<T>): Vector<T> {
        require(others.isNotEmpty()) { "Must specify at least one vector." }

        val sum = nullVector()
        for (other in others) {
            val product = this dot other
            val term = other scalar product
            sum.addModify(term)
        }

        return sum
    }

    override fun add(other: Vector<T>): Vector<T> {
        checkDimensions(size(), other.size()) { "Sizes don't match, got $it" }

        val vector = clone()
        for (i in 0 until size()) {
            vector[i] = op.add(vector[i], other[i])
        }

        return vector
    }

    override fun subtract(other: Vector<T>): Vector<T> {
        checkDimensions(size(), other.size()) { "Sizes don't match, got $it" }

        val vector = clone()
        for (i in 0 until size()) {
            vector[i] = op.subtract(vector[i], other[i])
        }

        return vector
    }

    override fun dot(other: Vector<T>): T {
        checkDimensions(size(), other.size()) { "Sizes don't match, got $it" }

        var sum = op.zero
        for (i in 0 until size()) {
            sum = op.add(sum, op.multiply(elements[i], other[i]))
        }

        return sum
    }

    override fun cross(other: Vector<T>): Vector<T> {
        if (size() != 3) {
            throw DimensionMismatchException("This vector doesn't have size 3, got ${size()}")
        }
        if (other.size() != 3) {
            throw DimensionMismatchException("Other vector doesn't have size 3, got ${other.size()}")
        }

        val result = clone()
        result[0] = op.subtract(op.multiply(this[1], other[2]), op.multiply(this[2], other[1]))
        result[1] = op.subtract(op.multiply(this[2], other[0]), op.multiply(this[0], other[2]))
        result[2] = op.subtract(op.multiply(this[0], other[1]), op.multiply(this[1], other[0]))

        return result
    }

    override fun scalar(value: T): Vector<T> {
        val result = clone()
        for (i in 0 until size()) {
            result[i] = op.multiply(this[i], value)
        }

        return result
    }

    override fun length(): Double {
        var sum = op.zero
        for (element in this) {
            sum = op.add(sum, op.multiply(element, element))
        }

        return Math.sqrt(op.toDouble(sum))
    }

    override fun normalize(): Vector<T> {
        val elements = nullVector()
        val length = length()
        if (length.isZero()) {
            return elements
        }

        val scalar = 1.0 / length
        return scalar(op.fromDouble(scalar))
    }

    override fun isPerpendicularTo(other: Vector<T>): Boolean {
        val product = this dot other
        return product.isZero()
    }

    override fun isNullVector() = all {
        it.isZero()
    }

    override fun addModify(other: Vector<T>) {
        checkDimensions(size(), other.size()) { "Sizes don't match, got $it" }

        for (i in 0 until size()) {
            elements[i] = op.add(elements[i], other[i])
        }
    }

    override fun subtractModify(other: Vector<T>) {
        checkDimensions(size(), other.size()) { "Sizes don't match, got $it" }

        for (i in 0 until size()) {
            elements[i] = op.subtract(elements[i], other[i])
        }
    }

    override fun scalarModify(scalar: T) {
        for (i in 0 until size()) {
            elements[i] = op.multiply(elements[i], scalar)
        }
    }

    override fun normalizeModify() {
        val length = length()
        if (length.isZero()) {
            return
        }

        val scalar = op.inverse(op.fromDouble(length))
        for (i in 0 until size()) {
            this[i] = op.multiply(this[i], scalar)
        }
    }

    override fun append(other: T): Vector<T> {
        return GenericVector(op, elements + other)
    }

    override fun append(elements: Vector<T>): Vector<T> {
        return GenericVector(op, this.elements + elements)
    }

    override operator fun get(index: Int) = elements[index]

    override operator fun set(index: Int, value: T) {
        elements[index] = value
    }

    override fun clone() = GenericVector(op, ArrayList(elements))

    override fun iterator() = elements.iterator()

    private fun T.isZero() = op.toDouble(this).isZero()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GenericVector<*>) return false

        if (elements != other.elements) return false

        return true
    }

    override fun hashCode() = elements.hashCode()

    override fun toString() = "(${elements.joinToString(",")})"
}

/**
 * Base class for vectors with types that implement [Number] to provide conversion methods.
 *
 * @author Ruben Schellekens
 */
abstract class NumberVector<T : Number>(operations: OperationSet<T>, numbers: MutableList<T>) : GenericVector<T>(operations, numbers) {

    /** Creates a new vector with all elements converted to bytes. **/
    fun toByteVector() = ByteVector(*ByteArray(elements.size) { Math.round(elements[it].toFloat()).toByte() })

    /** Creates a new vector with all elements converted to shorts. **/
    fun toShortVector() = ShortVector(*ShortArray(elements.size) { Math.round(elements[it].toFloat()).toShort() })

    /** Creates a new vector with all elements converted to ints. **/
    fun toIntVector() = IntVector(*IntArray(elements.size) { Math.round(elements[it].toFloat()) })

    /** Creates a new vector with all elements converted to longs. **/
    fun toLongVector() = LongVector(*LongArray(elements.size) { Math.round(elements[it].toDouble()) })

    /** Creates a new vector with all elements converted to floats. **/
    fun toFloatVector() = FloatVector(*FloatArray(elements.size) { elements[it].toFloat() })

    /** Creates a new vector with all elements converted to doubles. **/
    fun toDoubleVector() = DoubleVector(*DoubleArray(elements.size) { elements[it].toDouble() })
}

/** @author Ruben Schellekens **/
open class ByteVector(vararg bytes: Byte) : NumberVector<Byte>(ByteOperations, bytes.toMutableList()) {

    constructor(bytes: Collection<Byte>) : this(*bytes.toByteArray())
    constructor(size: Int, populator: (Int) -> Byte) : this(*(0 until size).map(populator).toByteArray())
}

/** @author Ruben Schellekens **/
open class ShortVector(vararg shorts: Short) : NumberVector<Short>(ShortOperations, shorts.toMutableList()) {

    constructor(shorts: Collection<Short>) : this(*shorts.toShortArray())
    constructor(size: Int, populator: (Int) -> Short) : this(*(0 until size).map(populator).toShortArray())
}

/** @author Ruben Schellekens **/
open class IntVector(vararg ints: Int) : NumberVector<Int>(IntOperations, ints.toMutableList()) {

    constructor(ints: Collection<Int>) : this(*ints.toIntArray())
    constructor(size: Int, populator: (Int) -> Int) : this(*(0 until size).map(populator).toIntArray())
}

/** @author Ruben Schellekens **/
open class LongVector(vararg longs: Long) : NumberVector<Long>(LongOperations, longs.toMutableList()) {

    constructor(longs: Collection<Long>) : this(*longs.toLongArray())
    constructor(size: Int, populator: (Int) -> Long) : this(*(0 until size).map(populator).toLongArray())
}

/** @author Ruben Schellekens **/
open class FloatVector(vararg floats: Float) : NumberVector<Float>(FloatOperations, floats.toMutableList()) {

    constructor(floats: Collection<Float>) : this(*floats.toFloatArray())
    constructor(size: Int, populator: (Int) -> Float) : this(*(0 until size).map(populator).toFloatArray())
}

/** @author Ruben Schellekens **/
open class DoubleVector(vararg doubles: Double) : NumberVector<Double>(DoubleOperations, doubles.toMutableList()) {

    constructor(doubles: Collection<Double>) : this(*doubles.toDoubleArray())
    constructor(size: Int, populator: (Int) -> Double) : this(*(0 until size).map(populator).toDoubleArray())
}

/** @author Ruben Schellekens **/
open class StringVector(vararg strings: String) : GenericVector<String>(StringOperations, *strings) {

    constructor(strings: Collection<String>) : this(*strings.toTypedArray())
    constructor(size: Int, populator: (Int) -> String) : this(*(0 until size).map(populator).toTypedArray())
}