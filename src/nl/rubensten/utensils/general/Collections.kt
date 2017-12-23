package nl.rubensten.utensils.general

/**
 *
 * @author Sten Wessel
 */
fun <T> Iterable<T>.partitionOrNull(predicate: (T) -> Boolean): Pair<List<T>?, List<T>?> {
    val (a, b) = this.partition(predicate)
    return (if (a.isEmpty()) null else a) to (if (b.isEmpty()) null else b)
}
