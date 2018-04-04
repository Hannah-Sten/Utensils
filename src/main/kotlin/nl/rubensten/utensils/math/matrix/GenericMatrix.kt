package nl.rubensten.utensils.math.matrix

/**
 * @author Ruben Schellekens, Sten Wessel
 */
open class GenericMatrix<T> : MutableMatrix<T> {

    companion object {

        /**
         * Creates an empty matrix, i.e. of size 0.
         */
        @JvmStatic
        fun <T> empty(operations: OperationSet<T>) = GenericMatrix(operations, emptyList())
    }

    /**
     * The rows or columns in the matrix (all the same size) ordered as determined by [myMajor].
     */
    protected val op: OperationSet<T>

    /**
     * How the vectors are stored in the matrix.
     */
    protected val myMajor: Major

    /**
     * Contains all the mathematical operations on the element type.
     */
    protected val elements: MutableList<MutableVector<T>>

    /**
     * @author Ruben Schellekens
     */
    class MatrixConstruction<T> {

        internal val rows: MutableList<MutableList<T>> = ArrayList()
        internal var size: Int? = null

        /**
         * Adds a new row to the
         */
        fun row(vararg elements: T): MatrixConstruction<T> {
            if (size != null && elements.size != size) {
                throw DimensionMismatchException("Wrong row size, got ${elements.size}, expected $size")
            }
            size = elements.size

            val row = ArrayList<T>()
            elements.forEach { row.add(it) }
            rows += row
            return this
        }
    }

    /**
     * Creates a new matrix from a complete list of vectors.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param major
     *          Whether the vectors in `elements` are rows ([Major.ROW]) or columns ([Major.COLUMN]).
     * @param elements
     *          The contents of the rows/columns.
     */
    constructor(op: OperationSet<T>, major: Major = Major.ROW, elements: MutableList<MutableVector<T>>) {
        this.op = op
        this.myMajor = major
        this.elements = elements
    }

    /**
     * Creates a new matrix from a list of vectors.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param elements
     *          A list containing all the vectors to put into the matrix.
     * @param major (default [Major.ROW])
     *          How the vectors must be put into the matrix.
     *          [Major.ROW] means every vector is a separate row.
     *          [Major.COLUMN] means every vector is a separate column.
     */
    constructor(op: OperationSet<T>, elements: List<Vector<T>>, major: Major = Major.ROW) : this(op,
            major, elements.map { it.toMutableVector() }.toMutableList()
    )

    /**
     * Creates a new matrix from an array of vectors.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param elements
     *          An array containing all the vectors to put into the matrix.
     * @param major (default [Major.ROW])
     *          How the vectors must be put into the matrix.
     *          [Major.ROW] means every vector is a separate row.
     *          [Major.COLUMN] means every vector is a separate column.
     */
    constructor(op: OperationSet<T>, vararg elements: Vector<T>, major: Major = Major.ROW) : this(op,
            major, elements.map { it.toMutableVector() }.toMutableList()
    )

    /**
     * Creates a new matrix containing `elements` as elements spread over rows of width `width`.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param elements
     *          The elements to put in the matrix.
     * @param width
     *          How many elements are in a matrix row.
     */
    constructor(op: OperationSet<T>, vararg elements: T, width: Int) : this(op,
            elements.toList().chunked(width) { row -> GenericVector(op, row) }
    )

    /**
     * Populates a new matrix based on indices.
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param height
     *          The amount of rows.
     * @param width
     *          The amount of columns.
     * @param populator
     *          A function that takes a zero-indexed `(rowIndex, colIndex)` tuple and produces the value
     *          that must be placed on that spot in the matrix.
     */
    constructor(op: OperationSet<T>, height: Int, width: Int, populator: (row: Int, col: Int) -> T) : this(op,
            (0 until height).map { i ->
                (0 until width).map { j ->
                    populator(i, j)
                }.toVector(op)
            }
    )

    /**
     * Creates a new matrix using a builder format.
     *
     * Example, produces the matrix {(1, 4), (2, 5), (3, 6)}:
     * ```
     * val matrix = GenericMatrix(IntOperations) {
     *     row(1, 2, 3)
     *     row(4, 5, 6)
     * }.transpose()
     * ```
     *
     * @param op
     *          The operations that apply to type `T`.
     * @param elements
     *          Block that generates the contents of the matrix as per format mentions above.
     *
     */
    constructor(op: OperationSet<T>, elements: MatrixConstruction<T>.() -> MatrixConstruction<T>) : this(op,
            MatrixConstruction<T>().elements().rows.map { GenericVector(op, it) }
    )

    override fun operations() = op

    override fun major() = myMajor

    override fun width() = if (elements.isEmpty()) 0 else when (myMajor) {
        Major.ROW -> elements[0].size()
        Major.COLUMN -> elements.size
    }

    override fun height() = if (elements.isEmpty()) 0 else when (myMajor) {
        Major.ROW -> elements.size
        Major.COLUMN -> elements[0].size()
    }

    override fun getRow(row: Int) = when (myMajor) {
        Major.ROW -> elements[row]
        Major.COLUMN -> (0 until width()).map { elements[it][row] }.toVector(op)
    }

    override fun swapRow(row0: Int, row1: Int): Matrix<T> {
        val clone = clone().toMutableMatrix()
        clone.swapRowModify(row0, row1)
        return clone
    }

    override fun getColumn(column: Int) = when (myMajor) {
        Major.ROW -> (0 until height()).map { elements[it][column] }.toVector(op)
        Major.COLUMN -> elements[column]
    }

    override fun swapColumn(col0: Int, col1: Int): Matrix<T> {
        val clone = mutableClone()
        clone.swapColumnModify(col0, col1)
        return clone
    }

    override fun add(other: Matrix<T>): Matrix<T> {
        checkDimensions(this, other)

        val vectors = elements.mapIndexed { index, vector -> (vector + other.getVector(index)).toMutableVector() }
        return GenericMatrix(op, myMajor, vectors.toMutableList())
    }

    override fun subtract(other: Matrix<T>): Matrix<T> {
        checkDimensions(this, other)

        val vectors = elements.mapIndexed { index, vector -> (vector - other.getVector(index)).toMutableVector() }
        return GenericMatrix(op, myMajor, vectors.toMutableList())
    }

    override fun scalar(scalar: T): Matrix<T> {
        val vectors = elements.map { (it * scalar).toMutableVector() }
        return GenericMatrix(op, myMajor, vectors.toMutableList())
    }

    override fun scalarRow(row: Int, scalar: T): Matrix<T> {
        val vectors = ArrayList(elements)
        when (myMajor) {
            Major.COLUMN -> {
                vectors.forEach {
                    it[row] = it[row] * scalar
                }
            }
            Major.ROW -> {
                vectors[row] = (vectors[row] * scalar).toMutableVector()
            }
        }
        return GenericMatrix(op, myMajor, vectors)
    }

    override fun scalarColumn(column: Int, scalar: T): Matrix<T> {
        val vectors = ArrayList(elements)
        when (myMajor) {
            Major.COLUMN -> {
                vectors[column] = (vectors[column] * scalar).toMutableVector()
            }
            Major.ROW -> {
                vectors.forEach {
                    it[column] = it[column] * scalar
                }
            }
        }
        return GenericMatrix(op, myMajor, vectors)
    }

    override fun multiply(other: Matrix<T>): Matrix<T> {
        checkDimensions(width(), other.height()) { "Sizes are wrong for multiplication, got $it" }

        val columns = ArrayList<MutableVector<T>>()
        for (column in 0 until other.width()) {
            val columnElements = ArrayList<T>()
            for (row in 0 until height()) {
                columnElements += getRow(row) * other.getColumn(column)
            }
            columns += GenericVector(op, columnElements)
        }

        return GenericMatrix(op, Major.COLUMN, columns)
    }

    override fun multiply(other: Vector<T>): Vector<T> {
        return multiply(other.toMatrix()).getColumn(0)
    }

    override fun power(exponent: Int): Matrix<T> {
        require(exponent >= 0) { "Exponent must be non-negative, got $exponent" }
        checkSquare(this)

        return when (exponent) {
            0 -> MatrixUtils.identity(width(), op.zero, op.unit, op)
            1 -> this
            2 -> this * this
            else -> {
                var result: Matrix<T> = this
                for (i in 2..exponent) {
                    result *= this
                }
                result
            }
        }
    }

    override fun subMatrix(row: Int, column: Int, width: Int, height: Int): Matrix<T> {
        require(row in 0 until height()) { "row index out of bounds: $row !in 0 until ${height()}" }
        require(column in 0 until width()) { "column index out of bounds: $column !in 0 until ${width()}" }
        require(row + height - 1 < height()) { "height out of bounds: ${row + height - 1} >= ${height()}" }
        require(column + width - 1 < width()) { "width out of bounds: ${column + width - 1} >= ${width()}" }

        val rows = ArrayList<MutableVector<T>>()

        for (y in row ofSize height) {
            val rowElements = ArrayList<T>()
            for (x in column ofSize width) {
                rowElements += get(y, x)
            }
            rows += GenericVector(op, rowElements)
        }

        return GenericMatrix(op, Major.ROW, rows)
    }

    override fun glueRight(other: Matrix<T>): Matrix<T> {
        checkDimensions(height(), other.height()) { "Heights don't match: $it" }

        val rows = rowsMutable().toMutableList()
        for (row in 0 until height()) {
            val glued = rows[row].asIterable() + other.getRow(row).asIterable()
            rows[row] = GenericVector(op, glued.toMutableList())
        }

        return GenericMatrix(op, Major.ROW, rows)
    }

    override fun glueLeft(other: Matrix<T>): Matrix<T> {
        checkDimensions(height(), other.height()) { "Heights don't match: $it" }

        val rows = rowsMutable().toMutableList()
        for (row in 0 until height()) {
            val glued = other.getRow(row).asIterable() + rows[row].asIterable()
            rows[row] = GenericVector(op, glued.toMutableList())
        }

        return GenericMatrix(op, Major.ROW, rows)
    }

    override fun glueTop(other: Matrix<T>): Matrix<T> {
        checkDimensions(width(), other.width()) { "Widths don't match: $it" }

        val columns = columnsMutable().toMutableList()
        for (column in 0 until width()) {
            val glued = other.getColumn(column).asIterable() + columns[column].asIterable()
            columns[column] = GenericVector(op, glued.toMutableList())
        }

        return GenericMatrix(op, Major.COLUMN, columns)
    }

    override fun glueBottom(other: Matrix<T>): Matrix<T> {
        checkDimensions(width(), other.width()) { "Widths don't match: $it" }

        val columns = columnsMutable().toMutableList()
        for (column in 0 until width()) {
            val glued = columns[column].asIterable() + other.getColumn(column).asIterable()
            columns[column] = GenericVector(op, glued.toMutableList())
        }

        return GenericMatrix(op, Major.COLUMN, columns)
    }

    override fun determinant(): T {
        checkSquare(this)

        // Base cases
        if (width() < 2) {
            return this[0, 0]
        }
        if (width() == 2) {
            return this[0, 0] * this[1, 1] - this[1, 0] * this[0, 1]
        }

        // Split matrix into pieces
        var sign = op.unit
        var determinantSum = op.zero
        for (col in 0 until width()) {
            val columns = columns()
            val elements = ArrayList<MutableVector<T>>()

            // Cover all columns, except the current one
            for (x in 0 until columns.size) {
                if (x == col) {
                    continue
                }

                val column = columns[x]
                val columnElements = ArrayList<T>()
                for (y in 1 until column.size()) {
                    columnElements += column[y]
                }
                elements.add(GenericVector(op, columnElements))
            }

            val part = GenericMatrix(op, Major.COLUMN, elements)
            determinantSum += this[0, col] * part.determinant() * sign
            sign = -sign
        }

        return determinantSum
    }

    override fun transpose(): Matrix<T> {
        return when (myMajor) {
            Major.COLUMN -> GenericMatrix(op, Major.ROW, elements.toMutableList())
            Major.ROW -> GenericMatrix(op, Major.COLUMN, elements.toMutableList())
        }
    }

    @Deprecated("Not yet fully working")
    override fun inverse(): Matrix<T>? {
        checkSquare(this)
        require(width() >= 2) { "Size must be at least 2x2, got ${height()}x${width()}" }

        // When determinant == 0 => There is no inverse.
        if (determinant().isZero()) {
            return null
        }

        // Standard matrix sweeping... Not fun.
        val solve = glueRight(MatrixUtils.identity(width(), op.zero, op.unit, op)) as GenericMatrix<T>

        for (i in 0 until solve.height()) {
            solve.swapToNonZero(i, i)
            val value = solve[i, i]
            solve.scalarRow(i, value.inverse())

            for (j in 0 until solve.height()) {
                if (j == i) {
                    continue
                }

                val factor = solve[j, i]
                val pivotRowScaled = solve.getRow(i) * factor
                val otherRow = solve.getRow(j)
                solve.setRow(j, otherRow - pivotRowScaled)
            }
        }

        return solve.subMatrix(0, width(), width(), height())
    }

    override fun negate() = when (myMajor) {
        // Switch on major to improve performance.
        Major.ROW -> rows().map { it.negate() }.toMatrix(op)
        Major.COLUMN -> columns().map { it.negate() }.toMatrix(op)
    }

    override fun negateModify(): MutableMatrix<T> {
        for (row in 0 until height()) {
            for (col in 0 until width()) {
                this[row, col] = -this[row, col]
            }
        }
        return this
    }

    /**
     * Looks for rows below the given row with a non-zero value at the specified column and swaps it
     * with the given row.
     * <p>
     * when the given cell already equals 0, this method will do nothing.
     *
     * @param row
     *         The index of the position that has to be non-zero.
     * @param col
     *         The index of the position that has to be non-zero.
     */
    private fun swapToNonZero(row: Int, col: Int) {
        if (this[row, col].isNonZero()) {
            return
        }

        for (i in (row + 1) until height()) {
            if (this[i, col].isNonZero()) {
                swapRow(row, i)
                return
            }
        }
    }

    override fun order(maxIterations: Int): Int {
        val matrix = this
        var product: Matrix<T> = this
        for (order in 1..maxIterations) {
            if (product.isIdentity()) {
                return order
            }

            product *= matrix
        }

        throw IllegalStateException("Max iterations exceeded!")
    }

    override fun setRow(row: Int, values: Vector<T>) {
        checkDimensions(width(), values.size()) { "Width must equal the vector size, got $it" }

        for (column in 0 until width()) {
            this[row, column] = values[column]
        }
    }

    override fun setColumn(column: Int, values: Vector<T>) {
        checkDimensions(height(), values.size()) { "Height must equal the vector size, got $it" }

        for (row in 0..height()) {
            this[row, column] = values[row]
        }
    }

    override fun swapRowModify(row0: Int, row1: Int): MutableMatrix<T> {
        when (myMajor) {
            Major.ROW -> {
                val temp = elements[row0]
                elements[row0] = elements[row1]
                elements[row1] = temp
            }
            Major.COLUMN -> {
                val replaced = getRow(row0)

                for (i in 0 until width()) {
                    elements[i][row0] = elements[i][row1]
                    elements[i][row1] = replaced[i]
                }
            }
        }
        return this
    }

    override fun swapColumnModify(col0: Int, col1: Int): MutableMatrix<T> {
        when (myMajor) {
            Major.COLUMN -> {
                val temp = elements[col0]
                elements[col0] = elements[col1]
                elements[col1] = temp
            }
            Major.ROW -> {
                val replaced = getColumn(col0)

                for (i in 0 until height()) {
                    elements[i][col0] = elements[i][col1]
                    elements[i][col1] = replaced[i]
                }
            }
        }
        return this
    }

    override fun addModify(other: Matrix<T>): MutableMatrix<T> {
        checkDimensions(this, other)

        for (col in 0 until width()) {
            for (row in 0 until height()) {
                this[row, col] += other[row, col]
            }
        }
        return this
    }

    override fun subtractModify(other: Matrix<T>): MutableMatrix<T> {
        checkDimensions(this, other)

        for (col in 0 until width()) {
            for (row in 0 until height()) {
                this[row, col] -= other[row, col]
            }
        }
        return this
    }

    override fun scalarModify(scalar: T): MutableMatrix<T> {
        for (col in 0 until width()) {
            for (row in 0 until height()) {
                this[row, col] *= scalar
            }
        }
        return this
    }

    override fun scalarRowModify(row: Int, scalar: T): MutableMatrix<T> {
        for (col in 0 until width()) {
            this[row, col] *= scalar
        }
        return this
    }

    override fun scalarColumnModify(column: Int, scalar: T): MutableMatrix<T> {
        for (row in 0 until height()) {
            this[row, column] *= scalar
        }
        return this
    }

    override fun isIdentity(): Boolean {
        for (column in 0 until width()) {
            for (row in 0 until height()) {
                // Diagonal: ones
                if (column == row) {
                    if (this[row, column].isNotUnit()) {
                        return false
                    }
                }
                // Non diagonal: zeroes
                else if (this[row, column].isNonZero()) {
                    return false
                }
            }
        }

        // When no defects found: it's the identity.
        return true
    }

    override fun elements(): Iterable<T> {
        return elements.flatten()
    }

    override fun rows(): List<Vector<T>> {
        return when (myMajor) {
            Major.ROW -> elements.map { it.clone().toMutableVector() }.toList()
            Major.COLUMN -> (0 until width()).map { getRow(it) }
        }
    }

    override fun rowsMutable(): List<MutableVector<T>> {
        return when (myMajor) {
            Major.ROW -> elements.map { it }.toList()
            Major.COLUMN -> (0 until width()).map { getRow(it) }
        }
    }

    override fun columns(): List<Vector<T>> {
        return when (myMajor) {
            Major.COLUMN -> elements.map { it.clone() }.toList()
            Major.ROW -> (0 until width()).map { getColumn(it) }
        }
    }

    override fun columnsMutable(): List<MutableVector<T>> {
        return when (myMajor) {
            Major.COLUMN -> elements.map { it }.toList()
            Major.ROW -> (0 until width()).map { getColumn(it) }
        }
    }

    override fun mutableClone(): MutableMatrix<T> {
        return GenericMatrix(op, myMajor, elements.map { it.clone() }.toMutableList())
    }

    override fun get(row: Int, col: Int): T {
        return when (myMajor) {
            Major.ROW -> elements[row][col]
            Major.COLUMN -> elements[col][row]
        }
    }

    override fun set(row: Int, col: Int, value: T) {
        when (myMajor) {
            Major.ROW -> elements[row][col] = value
            Major.COLUMN -> elements[col][row] = value
        }
    }

    override fun iterator() = elements.iterator()

    // Extension functions/operator for OperationSet operations.
    private fun T.toDouble() = op.toDouble(this)
    private fun T.isZero() = toDouble().isZero()
    private fun T.isUnit() = (this - op.unit).isZero()
    private fun T.isNotUnit() = !isUnit()
    private fun T.isNonZero() = !isZero()
    private fun T.inverse() = op.inverse(this)
    private operator fun T.times(other: T) = op.multiply(this, other)
    private operator fun T.plus(other: T) = op.add(this, other)
    private operator fun T.minus(other: T) = op.subtract(this, other)
    private operator fun T.unaryMinus() = op.negate(this)

    override fun toString() = buildString {
        val maxLength = elements.flatMap { it }.map { it.toString().length }.max() ?: return@buildString
        val format = ("%${maxLength}s ".repeat(width()).trim() + "\n").repeat(height()).trim()
        val strings = rows().flatMap { it }.map { it.toString() }.toTypedArray()
        append(format.format(*strings))
    }.trimEnd()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GenericMatrix<*>) return false

        if (myMajor != other.myMajor) return false
        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        var result = myMajor.hashCode()
        result = 31 * result + elements.hashCode()
        return result
    }
}

/**
 * @author Ruben Schellekens
 */
abstract class NumberMatrix<T : Number> : GenericMatrix<T> {

    constructor(operations: OperationSet<T>, major: Major = Major.ROW, vectors: MutableList<MutableVector<T>>)
            : super(operations, major, vectors)

    constructor(operations: OperationSet<T>, elements: List<Vector<T>>, major: Major = Major.ROW)
            : super(operations, elements, major)

    constructor(operations: OperationSet<T>, vararg vectors: Vector<T>, major: Major = Major.ROW)
            : super(operations, *vectors, major = major)

    constructor(operations: OperationSet<T>, vararg elements: T, width: Int)
            : super(operations, *elements, width = width)

    constructor(operations: OperationSet<T>, height: Int, width: Int, populator: (row: Int, col: Int) -> T)
            : super(operations, height, width, populator)

    constructor(operations: OperationSet<T>, builder: MatrixConstruction<T>.() -> MatrixConstruction<T>)
            : super(operations, builder)

    /** Creates a new matrix with all elements converted to bytes. **/
    fun toByteMatrix(major: Major = Major.ROW) = ByteMatrix(major, *elements.map { (it as NumberVector).toByteVector() }.toTypedArray())

    /** Creates a new matrix with all elements converted to short. **/
    fun toShortMatrix(major: Major = Major.ROW) = ShortMatrix(major, *elements.map { (it as NumberVector).toShortVector() }.toTypedArray())

    /** Creates a new matrix with all elements converted to int. **/
    fun toIntMatrix(major: Major = Major.ROW) = IntMatrix(major, *elements.map { (it as NumberVector).toIntVector() }.toTypedArray())

    /** Creates a new matrix with all elements converted to long. **/
    fun toLongMatrix(major: Major = Major.ROW) = LongMatrix(major, *elements.map { (it as NumberVector).toLongVector() }.toTypedArray())

    /** Creates a new matrix with all elements converted to float. **/
    fun toFloatMatrix(major: Major = Major.ROW) = FloatMatrix(major, *elements.map { (it as NumberVector).toFloatVector() }.toTypedArray())

    /** Creates a new matrix with all elements converted to double. **/
    fun toDoubleMatrix(major: Major = Major.ROW) = DoubleMatrix(major, *elements.map { (it as NumberVector).toDoubleVector() }.toTypedArray())
}

/**
 * @author Ruben Schellekens
 */
open class ByteMatrix : NumberMatrix<Byte> {

    companion object {

        /** A 0x0 byte matrix with 0 elements. **/
        private val emptyByteMatrix = ByteMatrix(0, 0) { _, _ -> 0 }

        /**
         * Returns a 0x0 byte matrix with 0 elements.
         */
        @JvmStatic
        fun empty(): Matrix<Byte> = emptyByteMatrix
    }

    constructor(major: Major = Major.ROW, vararg vectors: MutableVector<Byte>)
            : super(ByteOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<Byte>>, major: Major = Major.ROW)
            : super(ByteOperations, vectors, major)

    constructor(vararg vectors: Vector<Byte>, major: Major = Major.ROW)
            : super(ByteOperations, *vectors, major = major)

    constructor(vararg elements: Byte, width: Int)
            : super(ByteOperations, *elements.toTypedArray(), width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> Byte)
            : super(ByteOperations, height, width, populator)

    constructor(builder: MatrixConstruction<Byte>.() -> MatrixConstruction<Byte>)
            : super(ByteOperations, builder)
}

/**
 * @author Ruben Schellekens
 */
open class ShortMatrix : NumberMatrix<Short> {

    companion object {

        /** A 0x0 short matrix with 0 elements. **/
        private val emptyShortMatrix = ShortMatrix(0, 0) { _, _ -> 0 }

        /**
         * Returns a 0x0 short matrix with 0 elements.
         */
        @JvmStatic
        fun empty(): Matrix<Short> = emptyShortMatrix
    }

    constructor(major: Major = Major.ROW, vararg vectors: MutableVector<Short>)
            : super(ShortOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<Short>>, major: Major = Major.ROW)
            : super(ShortOperations, vectors, major)

    constructor(vararg vectors: Vector<Short>, major: Major = Major.ROW)
            : super(ShortOperations, *vectors, major = major)

    constructor(vararg elements: Short, width: Int)
            : super(ShortOperations, *elements.toTypedArray(), width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> Short)
            : super(ShortOperations, height, width, populator)

    constructor(builder: MatrixConstruction<Short>.() -> MatrixConstruction<Short>)
            : super(ShortOperations, builder)
}

/**
 * @author Ruben Schellekens
 */
open class IntMatrix : NumberMatrix<Int> {

    companion object {

        /** A 0x0 int matrix with 0 elements. **/
        private val emptyIntMatrix = IntMatrix(0, 0) { _, _ -> 0 }

        /**
         * Returns a 0x0 int matrix with 0 elements.
         */
        @JvmStatic
        fun empty(): Matrix<Int> = emptyIntMatrix
    }

    constructor(major: Major = Major.ROW, vararg vectors: MutableVector<Int>)
            : super(IntOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<Int>>, major: Major = Major.ROW)
            : super(IntOperations, vectors, major)

    constructor(vararg vectors: Vector<Int>, major: Major = Major.ROW)
            : super(IntOperations, *vectors, major = major)

    constructor(vararg elements: Int, width: Int)
            : super(IntOperations, *elements.toTypedArray(), width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> Int)
            : super(IntOperations, height, width, populator)

    constructor(builder: MatrixConstruction<Int>.() -> MatrixConstruction<Int>)
            : super(IntOperations, builder)
}

/**
 * @author Ruben Schellekens
 */
open class LongMatrix : NumberMatrix<Long> {

    companion object {

        /** A 0x0 long matrix with 0 elements. **/
        private val emptyLongMatrix = LongMatrix(0, 0) { _, _ -> 0 }

        /**
         * Returns a 0x0 long matrix with 0 elements.
         */
        @JvmStatic
        fun empty(): Matrix<Long> = emptyLongMatrix
    }

    constructor(major: Major = Major.ROW, vararg vectors: MutableVector<Long>)
            : super(LongOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<Long>>, major: Major = Major.ROW)
            : super(LongOperations, vectors, major)

    constructor(vararg vectors: Vector<Long>, major: Major = Major.ROW)
            : super(LongOperations, *vectors, major = major)

    constructor(vararg elements: Long, width: Int)
            : super(LongOperations, *elements.toTypedArray(), width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> Long)
            : super(LongOperations, height, width, populator)

    constructor(builder: MatrixConstruction<Long>.() -> MatrixConstruction<Long>)
            : super(LongOperations, builder)
}

/**
 * @author Ruben Schellekens
 */
open class FloatMatrix : NumberMatrix<Float> {

    companion object {

        /** A 0x0 float matrix with 0 elements. **/
        private val emptyFloatMatrix = FloatMatrix(0, 0) { _, _ -> 0f }

        /**
         * Returns a 0x0 float matrix with 0 elements.
         */
        @JvmStatic
        fun empty(): Matrix<Float> = emptyFloatMatrix
    }

    constructor(major: Major = Major.ROW, vararg vectors: MutableVector<Float>)
            : super(FloatOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<Float>>, major: Major = Major.ROW)
            : super(FloatOperations, vectors, major)

    constructor(vararg vectors: Vector<Float>, major: Major = Major.ROW)
            : super(FloatOperations, *vectors, major = major)

    constructor(vararg elements: Float, width: Int)
            : super(FloatOperations, *elements.toTypedArray(), width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> Float)
            : super(FloatOperations, height, width, populator)

    constructor(builder: MatrixConstruction<Float>.() -> MatrixConstruction<Float>)
            : super(FloatOperations, builder)
}

/**
 * @author Ruben Schellekens
 */
open class DoubleMatrix : NumberMatrix<Double> {

    companion object {

        /** A 0x0 double matrix with 0 elements. **/
        private val emptyDoubleMatrix = DoubleMatrix(0, 0) { _, _ -> 0.0 }

        /**
         * Returns a 0x0 double matrix with 0 elements.
         */
        @JvmStatic
        fun empty(): Matrix<Double> = emptyDoubleMatrix
    }

    constructor(major: Major = Major.ROW, vararg vectors: MutableVector<Double>)
            : super(DoubleOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<Double>>, major: Major = Major.ROW)
            : super(DoubleOperations, vectors, major)

    constructor(vararg vectors: Vector<Double>, major: Major = Major.ROW)
            : super(DoubleOperations, *vectors, major = major)

    constructor(vararg elements: Double, width: Int)
            : super(DoubleOperations, *elements.toTypedArray(), width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> Double)
            : super(DoubleOperations, height, width, populator)

    constructor(builder: MatrixConstruction<Double>.() -> MatrixConstruction<Double>)
            : super(DoubleOperations, builder)
}

/**
 * @author Ruben Schellekens
 */
open class StringMatrix : GenericMatrix<String> {

    companion object {

        /** A 0x0 string matrix with 0 elements. **/
        private val emptyStringMatrix = StringMatrix(0, 0) { _, _ -> "" }

        /**
         * Returns a 0x0 string matrix with 0 elements.
         */
        @JvmStatic
        fun empty(): Matrix<String> = emptyStringMatrix
    }

    constructor(major: Major = Major.ROW, vararg vectors: MutableVector<String>)
            : super(StringOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<String>>, major: Major = Major.ROW)
            : super(StringOperations, vectors, major)

    constructor(vararg vectors: Vector<String>, major: Major = Major.ROW)
            : super(StringOperations, *vectors, major = major)

    constructor(vararg elements: String, width: Int)
            : super(StringOperations, *elements, width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> String)
            : super(StringOperations, height, width, populator)

    constructor(builder: MatrixConstruction<String>.() -> MatrixConstruction<String>)
            : super(StringOperations, builder)
}