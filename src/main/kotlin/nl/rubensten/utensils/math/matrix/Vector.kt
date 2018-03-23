package nl.rubensten.utensils.math.matrix

import nl.rubensten.utensils.collections.Quadruple

/**
 * An immutable vector with a certain amount of rows.
 *
 * Classes should not implement this interface, but [MutableVector].
 *
 * @author Ruben Schellekens
 */
interface Vector<T>: Iterable<T> {

    /**
     * The first element (0) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are no elements in the vector.
     */
    val x: T
        @Throws(IndexOutOfBoundsException::class)
        get() = this[0]

    /**
     * The second element (1) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there is only 1 element in the vector.
     */
    val y: T
        @Throws(IndexOutOfBoundsException::class)
        get() = this[1]

    /**
     * The third element (2) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are only 2 elements in the vector.
     */
    val z: T
        @Throws(IndexOutOfBoundsException::class)
        get() = this[2]

    /**
     * The fourth element (3) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are only 3 elements in the vector.
     */
    val w: T
        @Throws(IndexOutOfBoundsException::class)
        get() = this[3]

    /**
     * Get the first and second element (0 and 1) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there is at most 1 element in the vector.
     */
    val xy: Pair<T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Pair(x, y)

    /**
     * Get the first and third element (0 and 2) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 2 elements in the vector.
     */
    val xz: Pair<T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Pair(x, z)

    /**
     * Get the first and fourth element (0 and 3) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 3 elements in the vector.
     */
    val xw: Pair<T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Pair(x, w)

    /**
     * Get the second and third element (1 and 2) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 2 elements in the vector.
     */
    val yz: Pair<T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Pair(y, z)

    /**
     * Get the second and fourth element (1 and 3) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 3 elements in the vector.
     */
    val yw: Pair<T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Pair(y, w)

    /**
     * Get the third and fourth element (2 and 3) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 3 elements in the vector.
     */
    val zw: Pair<T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Pair(z, w)

    /**
     * Get the first, second and third element (0, 1, and 2) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 2 elements in the vector.
     */
    val xyz: Triple<T, T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Triple(x, y, z)

    /**
     * Get the first, second and third element (0, 1, and 2) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 2 elements in the vector.
     */
    val rgb: Triple<T, T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = xyz

    /**
     * Get the first, second, third and fourth element (0, 1, 2, and 3) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 3 elements in the vector.
     */
    val xyzw: Quadruple<T, T, T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = Quadruple(x, y, z, w)

    /**
     * Get the first, second, third and fourth element (0, 1, 2, and 3) of the vector.
     *
     * @throws IndexOutOfBoundsException
     *         When there are at most 3 elements in the vector.
     */
    val rgba: Quadruple<T, T, T, T>
        @Throws(IndexOutOfBoundsException::class)
        get() = xyzw

    /**
     * The amount of elements in the vector.
     */
    val size: Int
        get() = size()

    /**
     * Get the amount of elements in the vector.
     *
     * @return The amount of elements in the vector.
     */
    fun size(): Int

    /**
     * The operations of T.
     */
    val operations: OperationSet<T>
        get() = operations()

    /**
     * Get the operations of T.
     */
    fun operations(): OperationSet<T>

    /**
     * Calls [MatrixUtils.extendToBasis] with only this vector as parameter.
     *
     * @return An array of vectors that form a basis for the dimension of this vector.
     */
    fun extendToBasis() = MatrixUtils.extendToBasis(this)

    /**
     * Calculates the perpendicular projection to other vectors.
     *
     * @return The perpendicular projection to other given vectors.
     */
    fun perpendicularProjectionTo(vararg others: Vector<T>): Vector<T>

    /**
     * Adds all the elements from another vector to this one following the addition rules of
     * vectors.
     * 
     * This will return a new Vector instance with the modified values.
     *
     * @param other
     *         The vector to add to this one.
     * @return A new vector with the modified values.
     * @throws DimensionMismatchException
     *         When the sizes of both vectors are different.
     */
    infix fun add(other: Vector<T>): Vector<T>

    /**
     * Subtracts all the elements from another vector from this one following the addition rules of
     * vectors.
     * 
     * This will return a new vector instance with the modified values.
     *
     * @param other
     *         The vector to subtract from this one.
     * @return A new vector with the modified values.
     * @throws DimensionMismatchException
     *         When the sizes of both vectors are different.
     */
    infix fun subtract(other: Vector<T>): Vector<T>

    /**
     * Calculates the standard dot product of this and another vector.
     *
     * @param other
     *         The vector to calculate the dot product with.
     * @return The dot product of this vector and the other vector.
     * @throws DimensionMismatchException
     *         When the vectors don't have the same sizes.
     */
    infix fun dot(other: Vector<T>): T

    /**
     * Calculates the standard cross product of this and another vector.
     * 
     * Let this vector be called `V` and let the other matrix be called `U`,
     * then this method will return the vector `V`&times;`U`. The vectors must have size 3.
     *
     * @param other
     *         The other vector to take the standard cross product with.
     * @return The cross product of both vectors.
     * @throws DimensionMismatchException
     *         When either this or the other vector has a size not equal to 3.
     */
    infix fun cross(other: Vector<T>): Vector<T>

    /**
     * Multiplies all the elements of the vector by a given value.
     * 
     * Follows the scalar multiplication rules for vectors.
     *
     * @param value
     *         The value to multiply all elements with.
     * @return A new vector with the modified values.
     */
    infix fun scalar(value: T): Vector<T>

    /**
     * Negates all elements of the vector.
     *
     * @return A new vector with all elements negated according to the [OperationSet].
     */
    fun negate(): Vector<T>

    /**
     * Calculates the pythagorean length of the vector.
     *
     * @return The length of the vector.
     */
    fun length(): Double

    /**
     * Creates a vector with elements in a given (inclusive) range.
     */
    fun slice(startIndexInclusive: Int, endIndexInclusive: Int): Vector<T>

    /**
     * Creates a vector with elements in a given range.
     */
    fun slice(indexRange: IntRange) = slice(indexRange.start, indexRange.endInclusive)

    /**
     * Checks if the two vectors are perpendicular to each other.
     *
     * @param other
     *         The vector to check if it is perpendicular to this vector.
     * @return _true_ when the vectors are perpendicular, _false_ if not.
     */
    fun isPerpendicularTo(other: Vector<T>): Boolean

    /**
     * Checks whether the vector is a null vector (all elements being _0.0_ ) or not.
     * 
     * An empty array is considered a null vector.
     *
     * @return _true_ if the vector is a null vector, _false_ if not.
     */
    fun isNullVector(): Boolean

    /**
     * Normalises the vector.
     * 
     * When the vector is a null vector, this method will return a null vector.
     *
     * @return The normalised vector, or a null vector when the length is 0.
     */
    fun normalize(): Vector<T>

    /**
     * Appends an element to the vector.
     */
    infix fun append(other: T): Vector<T>

    /**
     * Appends several elements to the vector.
     */
    infix fun append(elements: Vector<T>): Vector<T>

    /**
     * Turns the vector into a mutable vector.
     */
    fun toMutableVector() = this as MutableVector<T>

    /**
     * Converts the vector to a matrix with [Major.COLUMN] (width=1, height=size())
     */
    fun toMatrix() = GenericMatrix(operations(), Major.COLUMN, mutableListOf(toMutableVector()))

    /**
     * Create a null vector of the same size as this vector.
     */
    fun nullVector(): MutableVector<T> {
        val result = toMutableVector().clone()
        for (i in 0 until size()) {
            result[i] = operations().zero
        }
        return result
    }

    /**
     * Calculates the azimuth of the vector.
     *
     * @throws DimensionMismatchException if the vector is not of size 3.
     */
    fun azimuth(): Double {
        checkDimensions(size(), 3) { "Only applicable to size 3, got $it" }

        val normalised = normalize()
        val op = operations()
        val x = op.toDouble(normalised[0])
        val y = op.toDouble(normalised[1])
        return Math.atan2(y, x)
    }

    /**
     * Calculates the inclination/polar angle of the vector.
     *
     * @throws DimensionMismatchException if the vector is not of size 3.
     */
    fun inclination(): Double {
        checkDimensions(size(), 3) { "Only applicable to size 3, got $it" }

        val normalised = normalize()
        val op = operations()
        val x = op.toDouble(normalised[0])
        val y = op.toDouble(normalised[1])
        val z = op.toDouble(normalised[2])
        return Math.PI * 0.5 - Math.atan2(z ,Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0)))
    }

    /**
     * Get the element at the specified index in the JVector.
     * 
     * The first element has index _0_.
     *
     * @param index
     *         The index of the element to fetch.
     * @return The element at position `index`.
     * @throws IndexOutOfBoundsException
     *         When the index is greater or equal than the size of the vector.
     */
    operator fun get(index: Int): T

    /** See [slice] **/
    operator fun get(indexRange: IntRange): Vector<T> = slice(indexRange)

    /** See [add] **/
    operator fun plus(other: Vector<T>) = add(other)

    /** See [subtract] **/
    operator fun minus(other: Vector<T>) = subtract(other)

    /** See [dot] **/
    operator fun times(other: Vector<T>) = dot(other)

    /** See [scalar] **/
    operator fun times(scalar: T) = scalar(scalar)

    /** See [negate] **/
    operator fun unaryMinus() = negate()

    /** Returns this vector **/
    operator fun unaryPlus() = this
}

/**
 * A mutable vector.
 *
 * @author Ruben Schellekens
 */
interface MutableVector<T> : Vector<T> {

    /**
     * See [add], but then modifies the vector instead of returning a new one.
     *
     * @return This (modified) vector.
     */
    infix fun addModify(other: Vector<T>): MutableVector<T>

    /**
     * See [subtract], but then modifies the vector instead of returning a new one.
     *
     * @return This (modified) vector.
     */
    infix fun subtractModify(other: Vector<T>): MutableVector<T>

    /**
     * See [scalar], but the modifies the vector instead of returning a new one.
     *
     * @return This (modified) vector.
     */
    infix fun scalarModify(scalar: T): MutableVector<T>

    /**
     * See [negate], but then modifies the vector instead of returning a new one.
     *
     * @return The modified vector with all elements negated according to the [OperationSet].
     */
    fun negateModify(): MutableVector<T>

    /**
     * See [normalize], but then modifies the vector instead of returning a new one.
     *
     * @return This (modified) vector.
     */
    fun normalizeModify(): MutableVector<T>

    /**
     * Create a clone of this vector.
     */
    fun clone(): MutableVector<T>

    /**
     * Set the value of element [index] in the vector.
     */
    operator fun set(index: Int, value: T)
}

//
//  Extension functions
//

// Convert arrays to vectors.
fun ByteArray.toVector() = ByteVector(*this)
fun ShortArray.toVector() = ShortVector(*this)
fun IntArray.toVector() = IntVector(*this)
fun LongArray.toVector() = LongVector(*this)
fun FloatArray.toVector() = FloatVector(*this)
fun DoubleArray.toVector() = DoubleVector(*this)
fun Array<String>.toVector() = GenericVector(StringOperations, toMutableList())
fun <T> Array<T>.toVector(operations: OperationSet<T>) = GenericVector(operations, toMutableList())

// Convert collections to vectors.
fun Collection<Byte>.toVector() = ByteVector(*toByteArray())
fun Collection<Short>.toVector() = ShortVector(*toShortArray())
fun Collection<Int>.toVector() = IntVector(*toIntArray())
fun Collection<Long>.toVector() = LongVector(*toLongArray())
fun Collection<Float>.toVector() = FloatVector(*toFloatArray())
fun Collection<Double>.toVector() = DoubleVector(*toDoubleArray())
fun Collection<String>.toVector() = GenericVector(StringOperations, toMutableList())
fun <T> Collection<T>.toVector(operations: OperationSet<T>) = GenericVector(operations, toMutableList())

// Vector construction.
infix fun Byte.`&`(other: Byte) = ByteVector(this, other)
infix fun Short.`&`(other: Short) = ShortVector(this, other)
infix fun Int.`&`(other: Int) = IntVector(this, other)
infix fun Long.`&`(other: Long) = LongVector(this, other)
infix fun Float.`&`(other: Float) = FloatVector(this, other)
infix fun Double.`&`(other: Double) = DoubleVector(this, other)
infix fun String.`&`(other: String) = GenericVector(StringOperations, this, other)

infix fun <T> Vector<T>.`&`(other: T) = append(other)
infix fun <T> Vector<T>.`&`(other: Vector<T>) = append(other)

fun byteVectorOf(vararg bytes: Byte) = ByteVector(*bytes)
fun shortVectorOf(vararg shorts: Short) = ShortVector(*shorts)
fun intVectorOf(vararg ints: Int) = IntVector(*ints)
fun longVectorOf(vararg longs: Long) = LongVector(*longs)
fun floatVectorOf(vararg floats: Float) = FloatVector(*floats)
fun doubleVectorOf(vararg doubles: Double) = DoubleVector(*doubles)
fun stringVectorOf(vararg strings: String) = StringVector(*strings)
fun <T> vectorOf(operations: OperationSet<T>, vararg elements: T) = GenericVector(operations, *elements)