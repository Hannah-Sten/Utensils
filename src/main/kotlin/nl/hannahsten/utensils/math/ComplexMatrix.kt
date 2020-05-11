package nl.hannahsten.utensils.math

import nl.hannahsten.utensils.math.matrix.*
import javax.naming.OperationNotSupportedException

/**
 * @author Hannah Schellekens
 */
open class ComplexMatrix : GenericMatrix<Complex> {

    constructor( major: Major = Major.ROW, vararg vectors: MutableVector<Complex>)
            : super(ComplexOperations, major, vectors.toMutableList())

    constructor(vectors: List<Vector<Complex>>, major: Major = Major.ROW)
            : super(ComplexOperations, vectors, major)

    constructor(vararg vectors: Vector<Complex>, major: Major = Major.ROW)
            : super(ComplexOperations, *vectors, major = major)

    constructor(vararg elements: Complex, width: Int)
            : super(ComplexOperations, *elements, width = width)

    constructor(height: Int, width: Int, populator: (row: Int, col: Int) -> Complex)
            : super(ComplexOperations, height, width, populator)

    constructor(builder: MatrixConstruction<Complex>.() -> MatrixConstruction<Complex>)
            : super(ComplexOperations, builder)
}

/**
 * @author Hannah Schellekens
 */
open class ComplexVector(vararg complex: Complex) : GenericVector<Complex>(ComplexOperations, complex.toMutableList()) {

    constructor(ints: Collection<Complex>) : this(*ints.toTypedArray())
    constructor(size: Int, populator: (Int) -> Complex) : this(*(0 until size).map(populator).toTypedArray())
}

/**
 * @author Hannah Schellekens
 */
object ComplexOperations : OperationSet<Complex>(Complex.ZERO, Complex.ONE,
        { i, j -> i + j },
        { i, j -> i - j },
        { i, j -> i * j },
        { i, j -> i / j },
        { it.inverse() },
        { -it },
        { if (it.b.isZero()) it.a else Double.NaN },
        { Complex(it, 0.0) },
        { _, _ -> throw OperationNotSupportedException() }
)

//
//  Extension functions
//

// Vectors
fun Array<Complex>.toVector() = ComplexVector(*this)
fun Collection<Complex>.toVector() = ComplexVector(*toTypedArray())
infix fun Complex.`&`(other: Complex) = ComplexVector(this, other)
fun complexVectorOf(vararg complex: Complex) = ComplexVector(*complex)

// Matrices
fun Array<Vector<Complex>>.toMatrix(major: Major = Major.ROW) = ComplexMatrix(*this, major = major)
fun List<Vector<Complex>>.toMatrix(major: Major = Major.ROW) = ComplexMatrix(this, major = major)
fun complexMatrixOf(width: Int, vararg complex: Complex) = ComplexMatrix(*complex, width = width)