package nl.rubensten.utensils.math.matrix

/**
 * @author Ruben Schellekens
 */
open class OperationSet<T>(
        val zero: T,
        val unit: T,
        inline val add: (T, T) -> T,
        inline val subtract: (T, T) -> T,
        inline val multiply: (T, T) -> T,
        inline val division: (T, T) -> T,
        inline val inverse: (T) -> T,
        inline val negate: (T) -> T,
        inline val toDouble: (T) -> Double,
        inline val fromDouble: (Double) -> T,
        inline val compare: (T, T) -> Int
)

/**
 * @author Ruben Schellekens
 */
object NoOperations : OperationSet<Any?>(null, null,
        { _, _ -> },
        { _, _ -> },
        { _, _ -> },
        { _, _ -> },
        { _ -> },
        { _ -> },
        { _ -> 0.0 },
        { _ -> null },
        { _, _ -> 0 }
)

/**
 * @author Ruben Schellekens
 */
object ByteOperations : OperationSet<Byte>(0.toByte(), 1.toByte(),
        { i, j -> (i + j).toByte() },
        { i, j -> (i - j).toByte() },
        { i, j -> (i * j).toByte() },
        { i, j -> (i / j).toByte() },
        { (1.0 / it).toByte() },
        { (-it).toByte() },
        { it.toDouble() },
        { it.toByte() },
        { i, j -> i.compareTo(j) }
)

/**
 * @author Ruben Schellekens
 */
object ShortOperations : OperationSet<Short>(0.toShort(), 1.toShort(),
        { i, j -> (i + j).toShort() },
        { i, j -> (i - j).toShort() },
        { i, j -> (i * j).toShort() },
        { i, j -> (i / j).toShort() },
        { (1.0 / it).toShort() },
        { (-it).toShort() },
        { it.toDouble() },
        { it.toShort() },
        { i, j -> i.compareTo(j) }
)

/**
 * @author Ruben Schellekens
 */
object IntOperations : OperationSet<Int>(0, 1,
        { i, j -> i + j },
        { i, j -> i - j },
        { i, j -> i * j },
        { i, j -> i / j },
        { (1.0 / it).toInt() },
        { -it },
        { it.toDouble() },
        { it.toInt() },
        { i, j -> i.compareTo(j) }
)

/**
 * @author Ruben Schellekens
 */
object LongOperations : OperationSet<Long>(0L, 1L,
        { i, j -> i + j },
        { i, j -> i - j },
        { i, j -> i * j },
        { i, j -> i / j },
        { (1.0 / it).toLong() },
        { -it },
        { it.toDouble() },
        { it.toLong() },
        { i, j -> i.compareTo(j) }
)

/**
 * @author Ruben Schellekens
 */
object FloatOperations : OperationSet<Float>(0f, 1f,
        { i, j -> i + j },
        { i, j -> i - j },
        { i, j -> i * j },
        { i, j -> i / j },
        { 1.0f / it },
        { -it },
        { it.toDouble() },
        { it.toFloat() },
        { i, j -> i.compareTo(j) }
)

/**
 * @author Ruben Schellekens
 */
object DoubleOperations : OperationSet<Double>(0.0, 1.0,
        { i, j -> i + j },
        { i, j -> i - j },
        { i, j -> i * j },
        { i, j -> i / j },
        { 1.0 / it },
        { -it },
        { it },
        { it },
        { i, j -> i.compareTo(j) }
)

/**
 * @author Ruben Schellekens
 */
object StringOperations : OperationSet<String>("", "",
        { a, b -> a + b },
        { a, b -> a.removeSuffix(b) },
        { _, _ -> throw UnsupportedOperationException() },
        { a, b -> a.replace(b, "") },
        { it.reversed() },
        { it.reversed() },
        { it.toDouble() },
        { it.toString() },
        { i, j -> i.compareTo(j) }
)