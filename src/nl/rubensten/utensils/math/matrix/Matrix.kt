package nl.rubensten.utensils.math.matrix

/**
 * An immutable matrix.
 *
 * Classes should not implement this interface, but [MutableMatrix].
 *
 * @author Ruben Schellekens
 */
interface Matrix<T> : Iterable<Vector<T>> {

    /**
     * The amount of columns the matrix has.
     */
    fun width(): Int

    /**
     * The amount of rows the matrix has.
     */
    fun height(): Int

    /**
     * The amount of elements in the Matrix.
     */
    fun count() = width() * height()

    /**
     * Get the operations of T.
     */
    fun operations(): OperationSet<T>

    /**
     * The ordening of the matrix.
     */
    fun major(): Major

    /**
     * Puts all the values of the given row in a vector.
     *
     * @param row
     *         The row number of the row to fetch. The first row has index <i>0</i>.
     * @return A vector with the elements in the row. Sorted from left to right.
     * @throws IndexOutOfBoundsException
     *         When the row number is greater or equal to the height.
     */
    fun getRow(row: Int): Vector<T>

    /**
     * Puts all the values of the given column in a vector.
     *
     * @param col
     *         The column number of the column to fetch. The first column has index <i>0</i>.
     * @return A vector with the elements in the column. Sorted from top to bottom.
     * @throws IndexOutOfBoundsException
     *         When the column number is greater or equal to the width.
     */
    fun getColumn(column: Int): Vector<T>

    /**
     * Swaps the elements of two rows.
     *
     * @param row0
     *         The index of the first row (couting from <i>0</i>).
     * @param row1
     *         The index of the second row (couting from <i>0</i>)..
     * @return A new matrix with the rows swapped.
     * @throws IndexOutOfBoundsException
     *         When there is no row with the given indices.
     */
    fun swapRow(row0: Int, row1: Int): Matrix<T>

    /**
     * Swaps the elements of two columns.
     *
     * @param col0
     *         The index of the first column (couting from <i>0</i>).
     * @param col1
     *         The index of the second column (couting from <i>0</i>)..
     * @return A new matrix with the columns swapped.
     * @throws IndexOutOfBoundsException
     *         When there is no column with the given indices.
     */
    fun swapColumn(col0: Int, col1: Int): Matrix<T>

    /**
     * Adds all the elements from another matrix to this one following the addition rules of
     * matrices.
     * <p>
     * This will return a new JMatrix instance with the modified values.
     *
     * @param other
     *         The matrix to add to this one.
     * @return A new matrix with the modified values.
     * @throws DimensionMismatchException
     *         When the dimensions of both matrices are different.
     */
    infix fun add(other: Matrix<T>): Matrix<T>

    /**
     * Substracts all the elements of another matrix from this one following the addition rules of
     * matrices.
     * <p>
     * This will return a new JMatrix instance with the modified values.
     *
     * @param other
     *         The matrix to substract from this one.
     * @return A new matrix with the modified values.
     * @throws DimensionMismatchException
     *         When the dimensions of both matrices are different.
     */
    infix fun subtract(other: Matrix<T>): Matrix<T>

    /**
     * Multiplies all the elements of the matrix by a given value.
     * <p>
     * Follows the scalar multiplication rules for matrices.
     *
     * @param scalar
     *         The value to multiply all elements with.
     * @return A new matrix with the modified values.
     */
    infix fun scalar(scalar: T): Matrix<T>

    /**
     * Multiplies all the elements of the given row with <code>scalar</code>.
     *
     * @param scalar
     *         The number to multiply the elements of the row with.
     * @param row
     *         The index of the row to multiply (start counting from <i>0</i> ).
     * @return This (modified) matrix.
     * @throws IndexOutOfBoundsException
     *         When the row index doesn't exist.
     */
    fun scalarRow(row: Int, scalar: T): Matrix<T>

    /**
     * Multiplies all the elements of the given column with <code>scalar</code>.
     *
     * @param scalar
     *         The number to multiply the elements of the column with.
     * @param column
     *         The index of the column to multiply (start counting from <i>0</i>).
     * @return This (modified) matrix.
     * @throws IndexOutOfBoundsException
     *         When the column index doesn't exist.
     */
    fun scalarColumn(column: Int, scalar: T): Matrix<T>

    /**
     * Multiplies this matrix with another matrix using the rules for matrix multiplication.
     * <p>
     * Let this matrix be called <code>A</code> and let the other matrix be called <code>B</code>,
     * then this method will return the matrix <code>A&times;B</code>.
     *
     * @param other
     *         The matrix to multply this matrix with.
     * @return The product of this and the other matrix.
     * @throws DimensionMismatchException
     *         When the matrices don't have the right dimension to multiply.
     */
    infix fun multiply(other: Matrix<T>): Matrix<T>

    /**
     * Multiplies this matrix with another vector using the rules for matrix multiplication.
     * <p>
     * Let this matrix be called <code>A</code> and let the vector be called <code>B</code>,
     * then this method will return the matrix <code>A&times;B</code>.
     *
     * @param other
     *         The matrix to multply this matrix with.
     * @return The product of this and the other matrix.
     * @throws DimensionMismatchException
     *         When the matrices don't have the right dimension to multiply.
     */
    infix fun multiply(other: Vector<T>): Vector<T>

    /**
     * Raises this matrix to a given power.
     *
     * @param exponent
     *          The power to calculate, must be nonnegative.
     * @throws DimensionMismatchException
     *          When the matrix is not square.
     * @throws IllegalArgumentException
     *          When the exponent is smaller than zero.
     */
    infix fun power(exponent: Int): Matrix<T>

    /**
     * Cuts a rectangle of elements out of the matrix and puts it in a new matrix.
     * <p>
     * You specify a starting point in the matrix and a size. The new matrix will be cut out of the
     * original matrix starting at the specified point and going <code>width</code> elements to the
     * right and <code>height</code> elements downwards. The end matrix has the given dimensions
     * (including the specified point).
     *
     * @param row
     *         The row to start the cutting. First row has index <i>0</i>.
     * @param column
     *         The column to start the cutting. First column has index <i>0</i>.
     * @param width
     *         The amount of columns you want the cut matrix to have.
     * @param height
     *         The amount of rows you want the cut matrix to have.
     * @return A new matrix with the cut out elements.
     * @throws IndexOutOfBoundsException
     *         When the specified region goes outside of the matrix's bounds.
     * @throws IllegalArgumentException
     *         When the width or height is negative.
     */
    fun subMatrix(row: Int, column: Int, width: Int, height: Int): Matrix<T>

    /**
     * Creates a new matrix where the given matrix is glued to the right of this matrix.
     *
     * @param matrix
     *         The matrix to glue to the right of this matrix.
     * @return A new matrix with the other matrix glued to the right of it.
     * @throws DimensionMismatchException
     *         When the height of the other matrix doesn't match up with the height of this matrix.
     */
    infix fun glueRight(other: Matrix<T>): Matrix<T>

    /**
     * Creates a new matrix where the given matrix is glued to the left of this matrix.
     *
     * @param matrix
     *         The matrix to glue to the left of this matrix.
     * @return A new matrix with the other matrix glued to the right of it.
     * @throws DimensionMismatchException
     *         When the height of the other matrix doesn't match up with the height of this matrix.
     */
    infix fun glueLeft(other: Matrix<T>): Matrix<T>

    /**
     * Creates a new matrix where the given matrix is glued to the top of this matrix.
     *
     * @param matrix
     *         The matrix to glue to the top of this matrix.
     * @return A new matrix with the other matrix glued to the right of it.
     * @throws DimensionMismatchException
     *         When the height of the other matrix doesn't match up with the height of this matrix.
     */
    infix fun glueTop(other: Matrix<T>): Matrix<T>

    /**
     * Creates a new matrix where the given matrix is glued to the bottom of this matrix.
     *
     * @param matrix
     *         The matrix to glue to the bottom of this matrix.
     * @return A new matrix with the other matrix glued to the right of it.
     * @throws DimensionMismatchException
     *         When the height of the other matrix doesn't match up with the height of this matrix.
     */
    infix fun glueBottom(other: Matrix<T>): Matrix<T>

    /**
     * Calculates the determinant of a matrix.
     *
     * @return The determinant of the matrix.
     */
    fun determinant(): T

    /**
     * Creates a new matrix that is the transposed matrix of this matrix.
     * <p>
     * The transposed matrix will turn all rows to columns and all original columns to rows. This
     * means that the width and height will be flipped.
     *
     * @return The (new instance) transposed matrix.
     */
    fun transpose(): Matrix<T>

    /**
     * Calculates the inverse matrix if it exist.
     * <p>
     * This method will not return a matrix when the inverse matrix doesn't exist, i.e. when the
     * determinant equals <i>0</i>.
     *
     * @return Optional containing the inverse when it exist, or nothing when the inverse doesn't
     * exist.
     * @throws IllegalStateException
     *         When the matrix is not square or when the size of the matrix is smaller than 2.
     */
    fun inverse(): Matrix<T>?

    /**
     * Negates all aelements of the matrix.
     *
     * @return A new matrix with all elements negated according to the [OperationSet].
     */
    fun negate(): Matrix<T>

    /**
     * Get the order of the matrix.
     *
     * I.e. the value of `n` for which `A^n = I`.
     *
     * @param maxIterations
     *          The maximum amount of values for `n` that get checked (`maxIterations <= n`).
     * @return The order `n` such that `A^n = I`.
     * @throws IllegalStateException When the maximum amount of iterations has been exceeded.
     */
    @Throws(IllegalStateException::class)
    fun order(maxIterations: Int = Integer.MAX_VALUE): Int

    /**
     * Checks if the matrix is a square matrix.
     * <p>
     * I.e, if the height equals the width.
     *
     * @return <i>true</i> if the matrix is square, <i>false</i> if not.
     */
    fun isSquare() = width() == height()

    /**
     * Checks whether the matrix is the identity matrix or not.
     *
     * @return `true` when the matrix is the identity matrix, `false` otherwise.
     */
    fun isIdentity(): Boolean

    /**
     * Turns the matrix into a mutable matrix.
     */
    fun toMutableMatrix() = this as MutableMatrix<T>

    /**
     * Get either the row or the column at the given index depending on the major.
     */
    fun getVector(index: Int) = when (major()) {
        Major.ROW -> getRow(index)
        Major.COLUMN -> getColumn(index)
    }

    /**
     * Get all elements in the matrix.
     */
    fun elements(): Iterable<T>

    /**
     * Get a list of all the rows in the matrix.
     */
    fun rows(): List<Vector<T>>

    /**
     * Get a list of all the rows in the matrix made mutable.
     */
    fun rowsMutable(): List<MutableVector<T>>

    /**
     * Get a list of all vectors in the matrix.
     */
    fun columns(): List<Vector<T>>

    /**
     * Get a list of all vectors in the matrix made mutable.
     */
    fun columnsMutable(): List<MutableVector<T>>

    /**
     * Get the element at the given row and column.
     * <p>
     * The row and column indices start with <i>0</i>.
     *
     * @param row
     *         The row of the element (beginning with 0).
     * @param col
     *         The column of the element (beginning with 0).
     * @return The value at the given position.
     * @throws IndexOutOfBoundsException
     *         When the column or row index goes out of the matrix's bounds.
     */
    operator fun get(row: Int, col: Int): T

    /** See [add] **/
    operator fun plus(other: Matrix<T>) = add(other)

    /** See [subtract] **/
    operator fun minus(other: Matrix<T>) = subtract(other)

    /** See [scalar] **/
    operator fun times(scalar: T) = scalar(scalar)

    /** See [multiply(Matrix)] **/
    operator fun times(other: Matrix<T>) = multiply(other)

    /** See [multiply(Vector)] **/
    operator fun times(other: Vector<T>) = multiply(other)

    /** See [negate] **/
    operator fun unaryMinus() = negate()

    /** Does nothing with the matrix, just returns `this` **/
    operator fun unaryPlus() = this
}

/**
 * A mutable matrix.
 *
 * @author Ruben Schellekens
 */
interface MutableMatrix<T> : Matrix<T> {

    /**
     * Modifies all the values in a row.
     * <p>
     * All the new values will be mapped to the indices of the given row vector.
     *
     * @param row
     *         The vector with the new row values.
     * @param index
     *         The row number to modify. First row has index <i>0</i>.
     * @return This (modified) matrix.
     * @throws DimensionMismatchException
     *         When the size of the vector does not match up with the width of the matrix.
     * @throws IndexOutOfBoundsException
     *         When the given row index does not exist in the matrix.
     */
    fun setRow(row: Int, values: Vector<T>)

    /**
     * Modifies all the values in a column.
     * <p>
     * All the new values will be mapped to the indices of the given column
     * vector.
     *
     * @param column
     *         The vector with the new column values.
     * @param index
     *         The row number to modify. First row has index <i>0</i>.
     * @return This (modified) matrix.
     * @throws DimensionMismatchException
     *         When the size of the vector does not match up with the height of the matrix.
     * @throws IndexOutOfBoundsException
     *         When the given column index does not exist in the matrix.
     */
    fun setColumn(column: Int, values: Vector<T>)

    /**
     * See [swapRow], but then modifies the matrix instead of returning a new one.
     *
     * @return `this`
     */
    fun swapRowModify(row0: Int, row1: Int): MutableMatrix<T>

    /**
     * See [swapColumn], but then modifies the matrix instead of returning a new one.
     *
     * @return `this`
     */
    fun swapColumnModify(col0: Int, col1: Int): MutableMatrix<T>

    /**
     * See [add], but then modifies the matrix instead of returning a new one.
     *
     * @return `this`
     */
    fun addModify(other: Matrix<T>): MutableMatrix<T>

    /**
     * See [subtract], but then modifies the matrix instead of returning a new one.
     *
     * @return `this`
     */
    fun subtractModify(other: Matrix<T>): MutableMatrix<T>

    /**
     * See [scalar], but then modifies the matrix instead of returning a new one.
     *
     * @return `this`
     */
    fun scalarModify(scalar: T): MutableMatrix<T>

    /**
     * See [scalarRow], but then modifies the matrix instead of returning a new one.
     *
     * @return `this`
     */
    fun scalarRowModify(row: Int, scalar: T): MutableMatrix<T>

    /**
     * See [scalarColumn], but then modifies the matrix instead of returning a new one.
     *
     * @return `this`
     */
    fun scalarColumnModify(column: Int, scalar: T): MutableMatrix<T>

    /**
     * See [negate], but then modifies the matrix instead of returning a new one.
     *
     * @return The modified matrix with all elements negated according to the [OperationSet].
     */
    fun negateModify(): MutableMatrix<T>

    /**
     * Creates a copy of the matrix. Results in an immutable matrix.
     */
    fun clone(): Matrix<T> = mutableClone()

    /**
     * Creates a copy of the matrix. Results in a mutable matrix.
     */
    fun mutableClone(): MutableMatrix<T>

    /**
     * Set the value of an element at the given row and column.
     * <p>
     * The row and column indices start with <i>0</i>.
     *
     * @param row
     *         The row of the element (beginning with 0).
     * @param col
     *         The column of the element (beginning with 0).
     * @param value
     *         The value the element should have.
     * @return The old value at the given position.
     * @throws IndexOutOfBoundsException
     *         When the column or row index goes out of the matrix's bounds.
     */
    operator fun set(row: Int, col: Int, value: T)
}

/**
 * Constants for the two ways a matrix is ordened.
 *
 * @author Ruben Schellekens
 */
enum class Major {

    /**
     * Ordening is per column.
     */
    COLUMN,

    /**
     * Orderning is per row.
     */
    ROW
}

// Convert arrays of vectors to matrices.
fun Array<out Vector<Byte>>.toMatrix(major: Major = Major.ROW) = ByteMatrix(*this, major = major)
fun Array<out Vector<Short>>.toMatrix(major: Major = Major.ROW) = ShortMatrix(*this, major = major)
fun Array<out Vector<Int>>.toMatrix(major: Major = Major.ROW) = IntMatrix(*this, major = major)
fun Array<out Vector<Long>>.toMatrix(major: Major = Major.ROW) = LongMatrix(*this, major = major)
fun Array<out Vector<Float>>.toMatrix(major: Major = Major.ROW) = FloatMatrix(*this, major = major)
fun Array<out Vector<Double>>.toMatrix(major: Major = Major.ROW) = DoubleMatrix(*this, major = major)
fun Array<out Vector<String>>.toMatrix(major: Major = Major.ROW) = StringMatrix(*this, major = major)
fun <T> Array<out Vector<T>>.toMatrix(operations: OperationSet<T>, major: Major = Major.ROW) = GenericMatrix(operations, toMutableList(), major = major)

// Convert collections of vectors to matrices.
fun List<Vector<Byte>>.toMatrix(major: Major = Major.ROW) = ByteMatrix(this, major = major)
fun List<Vector<Short>>.toMatrix(major: Major = Major.ROW) = ShortMatrix(this, major = major)
fun List<Vector<Int>>.toMatrix(major: Major = Major.ROW) = IntMatrix(this, major = major)
fun List<Vector<Long>>.toMatrix(major: Major = Major.ROW) = LongMatrix(this, major = major)
fun List<Vector<Float>>.toMatrix(major: Major = Major.ROW) = FloatMatrix(this, major = major)
fun List<Vector<Double>>.toMatrix(major: Major = Major.ROW) = DoubleMatrix(this, major = major)
fun List<Vector<String>>.toMatrix(major: Major = Major.ROW) = StringMatrix(this, major = major)
fun <T> List<Vector<T>>.toMatrix(operations: OperationSet<T>, major: Major = Major.ROW) = GenericMatrix(operations, this, major = major)

// Creation functions.
fun byteMatrixOf(width: Int, vararg bytes: Byte) = ByteMatrix(*bytes, width = width)
fun shortMatrixOf(width: Int, vararg shorts: Short) = ShortMatrix(*shorts, width = width)
fun intMatrixOf(width: Int, vararg ints: Int) = IntMatrix(*ints, width = width)
fun longMatrixOf(width: Int, vararg longs: Long) = LongMatrix(*longs, width = width)
fun floatMatrixOf(width: Int, vararg floats: Float) = FloatMatrix(*floats, width = width)
fun doubleMatrixOf(width: Int, vararg doubles: Double) = DoubleMatrix(*doubles, width = width)
fun stringMatrixOf(width: Int, vararg strings: String) = StringMatrix(*strings, width = width)
fun <T> matrixOf(operations: OperationSet<T>, width: Int, vararg elements: T) = GenericMatrix(operations, width = width, elements = *elements)