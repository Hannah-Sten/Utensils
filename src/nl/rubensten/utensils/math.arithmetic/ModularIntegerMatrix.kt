package nl.rubensten.utensils.arithmetic

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
open class ModularIntegerVector : NumberVector<ModularInteger> {

    constructor(modulus: Long, modularIntegers: Collection<ModularInteger>)
            : super(ModularIntegerOperations(modulus), modularIntegers.toMutableList())

    constructor(modulus: Long, size: Int, populator: (Int) -> ModularInteger)
            : super(ModularIntegerOperations(modulus), (0 until size).map(populator).toMutableList())
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
        { ModularInteger.reduce(it.toLong(), modulus) }
)