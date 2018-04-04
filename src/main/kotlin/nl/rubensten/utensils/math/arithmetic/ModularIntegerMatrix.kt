package nl.rubensten.utensils.math.arithmetic

import nl.rubensten.utensils.math.matrix.*

/**
 * @author Ruben Schellekens
 */
open class ModularIntegerMatrix : GenericMatrix<ModularInteger> {

    constructor(modulus: Long, major: Major = Major.ROW, vararg vectors: MutableVector<ModularInteger>)
            : super(ModularIntegerOperations(modulus), major, vectors.toMutableList())

    constructor(modulus: Long, vectors: List<Vector<ModularInteger>>, major: Major = Major.ROW)
            : super(ModularIntegerOperations(modulus), vectors, major)

    constructor(modulus: Long, vararg vectors: Vector<ModularInteger>, major: Major = Major.ROW)
            : super(ModularIntegerOperations(modulus), *vectors, major = major)

    constructor(modulus: Long, vararg elements: ModularInteger, width: Int)
            : super(ModularIntegerOperations(modulus), *elements, width = width)

    constructor(modulus: Long, height: Int, width: Int, populator: (row: Int, col: Int) -> ModularInteger)
            : super(ModularIntegerOperations(modulus), height, width, populator)

    constructor(modulus: Long, elements: MatrixConstruction<Long>.() -> MatrixConstruction<Long>) : super(
            ModularIntegerOperations(modulus),
            MatrixConstruction<Long>().elements().rows.map {
                ModularIntegerVector(modulus, it.map { it mod modulus })
            }
    )
}

/**
 * @author Ruben Schellekens
 */
open class ModularIntegerVector(modulus: Long, vararg modInts: ModularInteger) : GenericVector<ModularInteger>(
        ModularIntegerOperations(modulus),
        modInts.toMutableList()
) {

    constructor(modulus: Long, ints: Collection<ModularInteger>) : this(modulus, *ints.toTypedArray())
    constructor(modulus: Long, size: Int, populator: (Int) -> ModularInteger) : this(modulus, *(0 until size).map(populator).toTypedArray())
}

/**
 * @author Ruben Schellekens
 */
class ModularIntegerOperations(val modulus: Long) : OperationSet<ModularInteger>(
        0 mod modulus,
        1 mod modulus,
        { i, j -> i + j },
        { i, j -> i - j },
        { i, j -> i * j },
        { i, j -> i / j },
        { it.inverse() },
        { it },
        { it.toDouble() },
        { ModularInteger.reduce(it.toLong(), modulus) },
        { i, j -> i.compareTo(j) }
)

//
//  Extension functions
//

// Vectors
fun Array<ModularInteger>.toVector(modulus: Long) = ModularIntegerVector(modulus, *this)
fun Collection<ModularInteger>.toVector(modulus: Long) = ModularIntegerVector(modulus, *toTypedArray())
infix fun ModularInteger.`&`(other: ModularInteger): ModularIntegerVector {
    require(modulus == other.modulus) { "Moduli must be equal, got $modulus vs ${other.modulus}"}
    return ModularIntegerVector(modulus, this, other)
}
fun modIntVectorOf(modulus: Long, vararg modInts: ModularInteger) = ModularIntegerVector(modulus, *modInts)

// Matrices
fun Array<Vector<ModularInteger>>.toMatrix(modulus: Long, major: Major = Major.ROW) = ModularIntegerMatrix(modulus, *this, major = major)
fun List<Vector<ModularInteger>>.toMatrix(modulus: Long, major: Major = Major.ROW) = ModularIntegerMatrix(modulus, this, major = major)
fun modIntMatrixOf(modulus: Long, width: Int, vararg modInts: ModularInteger) = ModularIntegerMatrix(modulus, *modInts, width = width)