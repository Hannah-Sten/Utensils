package nl.rubensten.utensils.math.matrix

import nl.rubensten.utensils.math.optimization.HungarianAlgorithm

/**
 * @author Ruben Schellekens, Sten Wessel
 */
object MatrixUtils {

    /**
     * Creates a new (square) identity matrix with `size` amount of rows and columns.
     *
     * @param size
     *         The amount of rows and columns. Must be 0 (empty matrix) or greater.
     * @param zero
     *         The zero value of type `T`.
     * @param unit
     *         The unit value of type `T`.
     * @param operations
     *         The operations of type `T`.
     * @throws IllegalArgumentException
     *         When the size is smaller than 0.
     */
    @JvmStatic
    fun <T> identity(size: Int, zero: T, unit: T, operations: OperationSet<T>): Matrix<T> {
        val elements = (0 until size).map { row ->
            (0 until size).map { col ->
                if (row == col) unit else zero
            }.toVector(operations)
        }
        return GenericMatrix(operations, elements)
    }

    /**
     * Creates a new (square) identity matrix with `size` amount of rows and columns.
     *
     * @param size
     *         The amount of rows and columns. Must be 0 (empty matrix) or greater.
     * @param operations
     *         The operations of type `T`.
     * @throws IllegalArgumentException
     *         When the size is smaller than 0.
     */
    @JvmStatic
    fun <T> identity(size: Int, operations: OperationSet<T>): Matrix<T> {
        val zero = operations.zero
        val unit = operations.unit
        return identity(size, zero, unit, operations)
    }

    /**
     * Generates an byte identity matrix.
     */
    @JvmStatic
    fun byteIdentity(size: Int): Matrix<Byte> = identity(size, 0.toByte(), 1.toByte(), ByteOperations)

    /**
     * Generates a short identity matrix.
     */
    @JvmStatic
    fun shortIdentity(size: Int): Matrix<Short> = identity(size, 0.toShort(), 1.toShort(), ShortOperations)

    /**
     * Generates an integer identity matrix.
     */
    @JvmStatic
    fun intIdentity(size: Int): Matrix<Int> = identity(size, 0, 1, IntOperations)

    /**
     * Generates a long identity matrix.
     */
    @JvmStatic
    fun longIdentity(size: Int): Matrix<Long> = identity(size, 0, 1, LongOperations)

    /**
     * Generates a float identity matrix.
     */
    @JvmStatic
    fun floatIdentity(size: Int): Matrix<Float> = identity(size, 0f, 1f, FloatOperations)

    /**
     * Generates a double identity matrix.
     */
    @JvmStatic
    fun doubleIdentity(size: Int): Matrix<Double> = identity(size, 0.0, 1.0, DoubleOperations)

    /**
     * Finds a basis for the dimension of the vectors supplied. It finds the basis with the sifting
     * graph.
     * <p>
     * If the number of vectors that are linearly independent is greater than the dimension, the
     * last vectors will be truncated from the basis.
     *
     * @param vectors
     *         The vectors to form a basis with.
     * @return A list of vectors that form a basis for the dimension of the vectors.
     * @throws DimensionMismatchException
     *         When the vectors have not the same dimension.
     */
    @JvmStatic
    fun <T> extendToBasis(vararg vectors: Vector<T>): List<Vector<T>> {
        TODO("Implement extendToBasis")
    }

    /**
     * Creates a orthonormal basis containing the supplied vectors. The basis is calculated with the
     * Gram-Schmidt process. The number of vectors returned is equal to the dimension of the
     * supplied vectors. When an empty array of vectors is supplied, the method returns an empty
     * array.
     *
     * @param vectors
     *         The vectors the basis should contain.
     * @return A list of vectors all with length 1 and perpendicular to each other.
     * @throws DimensionMismatchException
     *         When the vectors do not have the same size. IllegalArgumentException When the
     *         vectorarray is either null or empty.
     */
    @JvmStatic
    fun <T> orthonormalBasisContaining(vararg vectors: Vector<T>): List<Vector<T>> {
        TODO("Implement orthonormalBasisContaining")
    }

    /**
     * Checks whether the supplied vectors are linearly independent from each other.
     *
     * @param vectors
     *         The vectors to check.
     * @return Whether the vectors are linearly independent.
     * @throws DimensionMismatchException
     *         When the vectors do not have the same dimension.
     */
    @JvmStatic
    fun <T> isLinearlyIndependent(vararg vectors: Vector<T>): Boolean {
        TODO("Implement isLinearlyIndependent")
    }

    /**
     * See [isLinearlyIndependent].
     */
    @JvmStatic
    inline fun <reified T> isLinearlyIndependent(vectors: Collection<Vector<T>>): Boolean {
        val array = vectors.toTypedArray()
        return isLinearlyIndependent(*array)
    }

    /**
     * Calculate the average (per index) position of the given vectors.
     */
    @JvmStatic
    fun <T> average(vararg vectors: Vector<T>): Vector<T> {
        require(vectors.isNotEmpty()) { "There must be at least 1 vector" }

        val op = vectors[0].operations()
        val sum = vectors[0].nullVector()
        for (vector in vectors) {
            sum.addModify(vector)
        }

        return sum.scalar(op.fromDouble(1.0 / vectors.size))
    }

    /**
     * @see [average]
     */
    @JvmStatic
    fun <T> average(vectors: Collection<Vector<T>>) = average(*(vectors.toTypedArray()))

    /**
     * Creates a new vector from polar coordinates.
     *
     * @param radius
     *          The length of the vector.
     * @param angle
     *          The angle between the vector and the positive X-axis.
     * @return A vector with the specified angle and length.
     */
    @JvmStatic
    fun fromPolar(radius: Double, angle: Double) = DoubleVector(
            radius * Math.cos(angle),
            radius * Math.sin(angle)
    )

    /**
     * Creates a new vector from spherical coordinates.
     *
     * @param radius
     *          Length of the vector.
     * @param inclination
     *          The angle between the vector and the XY-plane.
     * @param azimuth
     *          The angle between the vector projected on the XY-plane and the positive X-axis.
     * @return A vector with the angles and length as specified.
     */
    @JvmStatic
    fun fromSpherical(radius: Double, inclination: Double, azimuth: Double) = DoubleVector(
            radius * Math.cos(azimuth) * Math.cos(inclination),
            radius * Math.sin(azimuth) * Math.cos(inclination),
            radius * Math.sin(inclination)
    )
}

/**
 * Assigns each worker to a single unique job with a minimum cost given a `n`&times;`n` cost matrix.
 *
 * The cost of worker `i` performing job `j` must be on row `i`, column `j` in the cost matrix.
 *
 * Uses the Hungarian Algorithm, see [HungarianAlgorithm].
 *
 * @param workers
 *          The workers that need to be assigned to the jobs. Each worker represents 1 row in the matrix
 *          in order from top to bottom (index `0` to index `n`).
 * @param jobs
 *          The jobs that the workers need to be assigned to. Each job represents 1 column in the matrix
 *          in order from left to right (index `0` to index `n`).
 * @return A mapping where each worker is mapped to the job in the optimal solution.
 */
fun <T, W, J> Matrix<T>.assignJobs(workers: List<W>, jobs: List<J>) = HungarianAlgorithm(this, workers, jobs).minimize()

/**
 * Calls [MathUtil.isZero] with a default margin of *1E-9*.
 *
 * @return *true* if `d` is close enough to zero, *false* if not.
 */
fun Double.isZero() = isZero(1E-9)

/**
 * Checks if a double is `margin` close to zero.
 *
 * @param margin
 *          The maximal accepted distance to 0.
 * @return *true* if the double `d` is `margin` close to *0*,
 * *false* if not.
 */
fun Double.isZero(margin: Double) = Math.abs(this) <= margin

/**
 * Creates an int range starting with `this` of `length` elements.
 */
infix fun Int.ofSize(length: Int) = this until (this + length)

/**
 * Throws a [DimensionMismatchException] with the result of calling [lazyMessage] if the dimensions don't match.
 */
@Throws(DimensionMismatchException::class)
inline fun checkDimensions(size: Int, otherSize: Int, lazyMessage: (String) -> Any) {
    if (size != otherSize) {
        val message = lazyMessage("$size vs $otherSize")
        throw DimensionMismatchException(message.toString())
    }
}

/**
 * Throws a [DimensionMismatchException] with the result of calling [lazyMessage] if the dimensions don't match.
 */
@Throws(DimensionMismatchException::class)
inline fun checkDimensions(width: Int, otherWidth: Int, height: Int, otherHeight: Int, lazyMessage: (String) -> Any) {
    if (width != otherWidth || height != otherHeight) {
        val message = lazyMessage("${height}x$width vs ${otherHeight}x$otherWidth")
        throw DimensionMismatchException(message.toString())
    }
}

/**
 * Checks for the same dimensions of `matrix` and `other`.
 */
@Throws(DimensionMismatchException::class)
fun <T> checkDimensions(matrix: Matrix<T>, other: Matrix<T>) {
    checkDimensions(matrix.width(), other.width(), matrix.height(), other.height()) { "Sizes don't match, got $it" }
}

/**
 * Checks whether the matrix is square or not, if not it will throw a [DimensionMismatchException]
 */
@Throws(DimensionMismatchException::class)
fun <T> checkSquare(matrix: Matrix<T>) {
    if (matrix.width() != matrix.height()) {
        val message = "Matrix is not square, got ${matrix.height()}x${matrix.width()}"
        throw DimensionMismatchException(message)
    }
}