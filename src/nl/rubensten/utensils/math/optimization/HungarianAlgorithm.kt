package nl.rubensten.utensils.math.optimization

import nl.rubensten.utensils.math.matrix.*

/**
 * Assigns each worker to a single unique job with a minimum cost given a `n`&times;`n` cost matrix.
 *
 * The cost of worker `i` performing job `j` must be on row `i`, column `j` in the cost matrix.
 *
 * Running time: `O(kn^3)` where `k` is the amount of times additional zeroes must be created.
 * For what I've tested, `k` tends to be relatively small (<30 for a 168x168 matrix).
 *
 * @author Ruben Schellekens
 */
class HungarianAlgorithm<T, W, J>(

        /**
         * A square cost matrix with the workers on the rows, and the workers on the columns.
         */
        val costMatrix: Matrix<T>,

        /**
         * The workers that need to be assigned to the jobs. Each worker represents 1 row in the matrix
         * in order from top to bottom (index `0` to index `n`).
         */
        val workers: List<W>,

        /**
         * The jobs that the workers need to be assigned to. Each job represents 1 column in the matrix
         * in order from left to right (index `0` to index `n`).
         */
        val jobs: List<J>
) {

    /**
     * The size (width AND height) of the matrix.
     */
    private val size = costMatrix.width()

    /**
     * The arithmetical operations of type `T`.
     */
    private val op = costMatrix.operations()

    /**
     * The matrix used by the algorithm, can be modified during the execution steps.
     */
    private lateinit var matrix: MutableMatrix<T>

    /**
     * Set containing all the indices of all covered rows.
     */
    private val rowCovers = HashSet<Int>()

    /**
     * Set containing all the indices of all covered columns.
     */
    private val columnCovers = HashSet<Int>()

    /**
     * The total cost of the optimal solution, only available after execute, otherwise null.
     */
    private var cost: T? = null

    init {
        checkSquare(costMatrix)
        require(costMatrix.width() > 0) { "Size of matrix must be greater than 0" }
    }

    /**
     * Minimises the cost of assigning all workers to exactly one job. `O(kn^3)`
     *
     * @return A mapping where each worker is mapped to the job in the optimal solution.
     */
    fun minimize(): Map<W, J> {
        matrix = costMatrix.toMutableMatrix().mutableClone()
        return executeAlgorithm()
    }

    /**
     * Maximises the cost of assigning all workers to exactly one job. `O(kn^3)`
     *
     * @return A mapping where each worker is mapped to the job in the optimal solution.
     */
    fun maximize(): Map<W, J> {
        matrix = costMatrix.toMutableMatrix().mutableClone()

        // Negate all values.
        var maximum: T? = null
        for (row in 0 until size) {
            for (col in 0 until size) {
                val value = matrix[row, col]
                matrix[row, col] = -value

                if (maximum == null || value > maximum) {
                    maximum = value
                }
            }
        }

        // Make nonnegative, i.e. add the negated minimum to all elements.
        for (row in 0 until size) {
            for (col in 0 until size) {
                matrix[row, col] = matrix[row, col] + maximum!!
            }
        }

        return executeAlgorithm()
    }

    /**
     * Executes the meat of the algorithm, after eventual preparation steps.
     */
    private fun executeAlgorithm(): Map<W, J> {
        subtractRowMinima()
        subtractColumnMinima()

        while (!hasEnoughCovers()) {
            clearCovers()
            findMinimumCover()

            if (!hasEnoughCovers()) {
                createAdditionalZeroes()
            }
        }

        return createResult()
    }

    /**
     * The total cost of the optimal solution.
     *
     * This value is available only after executing [minimize] or [maximize].
     *
     * @throws IllegalStateException When the algorithm hasn't been run yet.
     */
    @Throws(IllegalStateException::class)
    fun cost(): T {
        check(cost != null) { "Algorithm must be executed first!" }
        return cost!!
    }

    /**
     * Subtract the minimum of each row from all elements in that same row. `O(n^2)`
     */
    private fun subtractRowMinima() {
        for (row in 0 until size) {
            val minIndex = (0 until size).minWith(Comparator { o1, o2 -> op.compare(matrix[row, o1], matrix[row, o2]) }) ?: continue
            val min = matrix[row, minIndex]

            for (col in 0 until size) {
                matrix[row, col] = matrix[row, col] - min
            }
        }
    }

    /**
     * Subtract the minimum of each column from all elements in that same column. `O(n^2)`
     */
    private fun subtractColumnMinima() {
        for (col in 0 until size) {
            val minIndex = (0 until size).minWith(Comparator { o1, o2 -> op.compare(matrix[o1, col], matrix[o2, col]) }) ?: continue
            val min = matrix[minIndex, col]

            for (row in 0 until size) {
                matrix[row, col] = op.subtract(matrix[row, col], min)
            }
        }
    }

    /**
     * Checks if the desired amount of row/col covers has been reached. `O(1)`
     */
    private fun hasEnoughCovers() = rowCovers.size + columnCovers.size == size

    /**
     * Removes all saved row and column covers. `O(n)`
     */
    private fun clearCovers() {
        rowCovers.clear()
        columnCovers.clear()
    }

    /**
     * Finds the minimum cover to cross out all zeroes in the matrix. `O(n^3)`
     */
    private fun findMinimumCover() {
        // Required data structures.
        val assignedZeroes = HashSet<Pair<Int, Int>>()
        val crossedZeroes = HashSet<Pair<Int, Int>>()
        val markedRows = HashSet<Int>()
        val markedColumns = HashSet<Int>()

        // Extra little functions to enhance readability.
        fun Int.isMarkedColumn() = this in markedColumns

        fun Int.isMarkedRow() = this in markedRows
        fun Int.markColumn() = markedColumns.add(this)
        fun Int.markRow() = markedRows.add(this)
        fun List<Pair<Int, Int>>.withoutCrossedZeroes() = filter { it !in crossedZeroes }
        fun List<Pair<Int, Int>>.withoutAssignedZeroes() = filter { it !in assignedZeroes }
        fun Pair<Int, Int>.assign(): Pair<Int, Int> {
            assignedZeroes.add(this); return this
        }

        fun Pair<Int, Int>.crossOut(): Pair<Int, Int> {
            crossedZeroes.add(this); return this
        }

        fun Pair<Int, Int>.isAssigned() = assignedZeroes.contains(this)
        fun Pair<Int, Int>.isCrossedOut() = crossedZeroes.contains(this)

        // Step 1:
        // Assign as many tasks as possible.
        // Start with all rows that have only 1 valid zero.
        for (i in 0 until size) {
            var modifications = 0

            // Scan rows
            for (row in 0 until size) {
                val zeroes = findZeroesInRow(row).withoutCrossedZeroes().withoutAssignedZeroes()
                if (zeroes.size != 1) {
                    continue
                }

                // Assign when there is a zero in the row: cross out all other zeroes in the column.
                val assigned = zeroes[0].assign()
                modifications++

                // Cross out all column zeroes.
                val column = assigned.second
                findZeroesInColumn(column).withoutAssignedZeroes().forEach { it.crossOut() }
            }

            // Scan columns
            for (col in 0 until size) {
                val zeroes = findZeroesInColumn(col).withoutCrossedZeroes().withoutAssignedZeroes()
                if (zeroes.size != 1) {
                    continue
                }

                // Assign when there is a zero in the column: cross out all other zeroes in the row.
                val assigned = zeroes[0].assign()
                modifications++

                // Cross out all row zeroes.
                val row = assigned.first
                findZeroesInRow(row).withoutAssignedZeroes().forEach { it.crossOut() }
            }

            // When nothing gets assigned, continue to the next phase.
            if (modifications == 0) {
                break
            }
        }

        // Then cleanup all rows with multiple zeroes.
        for (row in 0 until size) {
            val zeroes = findZeroesInRow(row).withoutCrossedZeroes()
            if (zeroes.isEmpty()) {
                continue
            }

            // Assign when there is a zero in the row: cross out all other zeroes in the row.
            val assigned = zeroes[0].assign()
            for (i in 1 until zeroes.size) {
                zeroes[i].crossOut()
            }

            // Cross out all column zeroes.
            val column = assigned.second
            findZeroesInColumn(column).withoutAssignedZeroes().forEach { it.crossOut() }
        }

        // Step 2:
        // Prepare drawing - mark rows and columns.
        // Mark rows without any assignments.
        var modifications: Int
        do {
            modifications = 0

            for (row in 0 until size) {
                val zeroes = findZeroesInRow(row)
                val noAssignments = zeroes.none { it.isAssigned() }
                if (noAssignments) {
                    if (row.markRow()) modifications++
                }
            }

            // Mark all unmarked columns with zeroes in marked rows.
            for (col in 0 until size) {
                if (col.isMarkedColumn()) continue
                val zeroes = findZeroesInColumn(col)
                val anyInMarkedRow = zeroes.any { it.first.isMarkedRow() }
                if (anyInMarkedRow) {
                    if (col.markColumn()) modifications++
                }
            }

            // Mark all rows with assignments in new columns.
            for (row in 0 until size) {
                val zeroes = findZeroesInRow(row).filter { it.isAssigned() }
                val anyAssignmentInMarkedColumn = zeroes.any { it.second.isMarkedColumn() }
                if (anyAssignmentInMarkedColumn) {
                    if (row.markRow()) modifications++
                }
            }
        }
        while (modifications > 0)

        // Step 3:
        // Draw the covers.
        for (row in 0 until size) {
            if (row.isMarkedRow()) continue
            rowCovers += row
        }

        for (col in markedColumns) {
            columnCovers += col
        }
    }

    /**
     * Create additional zeroes. `O(n^2)`
     */
    private fun createAdditionalZeroes() {
        val elements = HashSet<T>()
        for (col in 0 until size) {
            if (col in columnCovers) continue
            for (row in 0 until size) {
                if (row in rowCovers) continue
                val value = matrix[row, col]
                if (!op.toDouble(value).isZero()) {
                    elements += value
                }
            }
        }

        val minimum = elements.minWith(Comparator { a, b -> op.compare(a, b) })!!

        for (row in 0 until size) {
            for (col in 0 until size) {
                val rowCovered = rowCovers.contains(row)
                val colCovered = columnCovers.contains(col)
                val value = matrix[row, col]

                if (rowCovered && colCovered) {
                    matrix[row, col] = value + minimum
                }
                else if (!rowCovered && !colCovered) {
                    matrix[row, col] = value - minimum
                }
            }
        }
    }

    /**
     * Interpret the resulting matrix and create the optimal worker-job assignment. `O(n^3)`
     */
    private fun createResult(): Map<W, J> {
        // Required data structures.
        val result = (0 until size).map<Int, Int?> { null }.toMutableList()
        val original: MutableList<MutableVector<T>?> = matrix.rows().map { it.toMutableVector() }.toMutableList()
        val updatedColumns = HashSet<Int>()

        // Handy extention functions for better readability.
        fun List<Vector<T>?>.columnZeroes(col: Int) = mapIndexed { index, vector -> Pair(index, vector) }
                .filter { it.second?.get(col)?.isZero() ?: false }
        fun Vector<T>.rowZeroes() = mapIndexed { index, elt -> Pair(index, elt) }.filter { it.second.isZero() }
        fun updateColumns() = updatedColumns.forEach { col -> original.filter { it != null }.forEach { row -> row!![col] = op.unit } }

        // Keep on picking rows until all rows are moved to the result array.
        outer@ while (result.any { it == null }) {
            updatedColumns.clear()

            // Scan all columns for lonely zeroes.
            for (col in 0 until size) {
                val zeroes = original.columnZeroes(col)
                if (zeroes.size != 1) {
                    continue
                }

                val (rowIndex, _) = zeroes[0]
                result[col] = rowIndex
                original[rowIndex] = null
                updatedColumns += col
            }

            // Scan all rows for lonely zeroes.
            for (row in 0 until size) {
                val rowVector = original[row] ?: continue
                val zeroes = rowVector.rowZeroes()
                if (zeroes.size != 1) {
                    continue
                }

                val (columnIndex, _) = zeroes[0]
                result[columnIndex] = row
                original[row] = null
                updatedColumns += columnIndex
            }

            if (updatedColumns.size > 0) {
                // Replace all zeroes in the given columns by something non-zero.
                updateColumns()
                continue@outer
            }

            // There were no updates: so all rows have multiple zeroes.
            // Just try to fit the first row in the new result matrix.
            for (col in 0 until size) {
                if (result[col] != null) continue

                for (rowIndex in 0 until size) {
                    val row = original[rowIndex] ?: continue
                    if (row[col].isZero()) {
                        result[col] = rowIndex
                        original[rowIndex] = null
                        updatedColumns += col
                        updateColumns()
                        continue@outer
                    }
                }
            }
        }

        // Store optimal cost.
        calculateCost(result)

        return result.mapIndexed { index, i -> workers[i!!] to jobs[index] }.toMap()
    }

    /**
     * Calculates the total cost of the optimal assignment given as `result` and stores it in [cost].
     */
    private fun calculateCost(result: List<Int?>) {
        cost = result.mapIndexed { job, worker -> costMatrix[worker!!, job] }
                .reduce { acc, t -> acc + t }
    }

    /**
     * Counts the amount of zeroes in the given row. `O(n)`
     */
    private fun countRowZeroes(rowIndex: Int) = matrix.getRow(rowIndex).count { it.isZero() }

    /**
     * Counts the amount of zeroes in the given column. `O(n)`
     */
    private fun countColumnZeroes(columnIndex: Int) = matrix.getColumn(columnIndex).count { it.isZero() }

    /**
     * Finds all the zeroes in the given row. `O(n)`
     *
     * @return A list of coordinates `(row, column)` that all have a zero at that spot in the matrix.
     */
    private fun findZeroesInRow(rowIndex: Int) = matrix.getRow(rowIndex)
            .mapIndexed { index, value -> Pair(index, value) }
            .filter { it.second.isZero() }
            .map { Pair(rowIndex, it.first) }

    /**
     * Finds all the zeroes in the given column. `O(n)`
     *
     * @return A list of coordinates `(row, column)` that all have a zero at that spot in the matrix.
     */
    private fun findZeroesInColumn(columnIndex: Int) = matrix.getColumn(columnIndex)
            .mapIndexed { index, value -> Pair(index, value) }
            .filter { it.second.isZero() }
            .map { Pair(it.first, columnIndex) }

    // Extension functions/operator for OperationSet operations.
    private fun T.toDouble() = op.toDouble(this)
    private fun T.isZero() = toDouble().isZero()
    private operator fun T.plus(other: T) = op.add(this, other)
    private operator fun T.minus(other: T) = op.subtract(this, other)
    private operator fun T.unaryMinus() = op.negate(this)
    private operator fun T.compareTo(other: T) = op.compare(this, other)
}