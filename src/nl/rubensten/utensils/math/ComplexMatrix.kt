package nl.rubensten.utensils.math

import nl.rubensten.utensils.math.matrix.*
import javax.naming.OperationNotSupportedException

/**
 * @author Ruben Schellekens
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
 * @author Ruben Schellekens
 */
open class ComplexVector : GenericVector<Complex> {

    constructor(complexNumbers: Collection<Complex>) : super(ComplexOperations, complexNumbers.toMutableList())
    constructor(size: Int, populator: (Int) -> Complex) : super(ComplexOperations, (0 until size).map(populator).toMutableList())
}

/** @author Ruben Schellekens **/
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