package nl.rubensten.utensils.math.matrix

/**
 * Returns a new matrix where each element is mapped to another element.
 * The transformation function also contains the row and column index of the elements.
 *
 * The resulting matrix carries over the major of the original matrix.
 *
 * @param transform
 *         **`element`**:
 *         The element that is being mapped.
 *         **`vectorIndex`:**
 *         The index of the vector the element is in.
 *         When in [Major.ROW] major, this is the row index.
 *         When in [Major.COLUMN] major, this is the column index.
 *         **`elementIndex`**:
 *         The index of the element within the vector.
 *         When in [Major.ROW] major, this is the column index.
 *         When in [Major.COLUMN] major, this is the row index.
 */
inline fun <T> Matrix<T>.mapElementsIndexed(transform: (element: T, vectorIndex: Int, elementIndex: Int) -> T): Matrix<T> {
    // Empty matrix.
    if (size == 0) {
        return this
    }

    // There is at least 1 vector.
    val vectorSize = getVector(0).size

    // Iterate over all vectors.
    val resultVectors = ArrayList<Vector<T>>(vectors())
    for ((vectorIndex, vector) in this.withIndex()) {
        val elements = ArrayList<T>(vectorSize)
        for (elementIndex in 0 until vector.size) {
            elements += transform(vector[elementIndex], vectorIndex, elementIndex)
        }
        resultVectors += elements.toVector(operations)
    }

    return resultVectors.toMatrix(operations)
}

/**
 * Returns a new matrix where each elements is mapped to another element.
 *
 * The resulting matrix carries over the major of the original matrix.
 */
inline fun <T> Matrix<T>.mapElements(transform: (element: T) -> T) = mapElementsIndexed { element, _, _ -> transform(element) }

/**
 * Returns a new matrix where each vector is mapped to another vector.
 *
 * The resulting matrix carries over the major of the original matrix.
 */
inline fun <T> Matrix<T>.mapVectors(transform: (vector: Vector<T>) -> Vector<T>) = map { transform(it) }.toMatrix(operations)

/**
 * Returns a new matrix where each vector is mapped to another vector.
 * The transformation also contains the vector index.
 * When in [Major.ROW] major, this is the row index, and when in [Major.COLUMN] major, this is the column index.
 *
 * The resulting matrix carries over the major of the original matrix.
 */
inline fun <T> Matrix<T>.mapVectorsIndexed(transform: (vectorIndex: Int, vector: Vector<T>) -> Vector<T>) = mapIndexed { index, vector ->
    transform(index, vector)
}.toMatrix(operations)

/**
 * Returns a new vector where each element is mapped to another element.
 */
inline fun <T> Vector<T>.mapElements(transform: (element: T) -> T): Vector<T> = map { transform(it) }.toVector(operations)

/**
 * Returns a new vector where each element is mapped to another element.
 * The transformation also contains the element index.
 */
inline fun <T> Vector<T>.mapElementsIndexed(transform: (index: Int, element: T) -> T): Vector<T> = mapIndexed { index, element ->
    transform(index, element)
}.toVector(operations)