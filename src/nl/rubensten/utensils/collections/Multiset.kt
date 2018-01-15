package nl.rubensten.utensils.collections

/**
 * A generic unordered collection of elements that supports duplicate elements.
 * Methods in this interface support only read-only access to the set;
 * read/write access is supported through the [MutableMultiset] interface.
 *
 * The total amount of elements (including frequencies) are available through [totalCount].
 * The total amount of distinct keys is available through [size].
 *
 * @param E
 *          The type of elements contained in the multisetset.
 *          The multisetset is invariant on its element type.
 *
 * @author Ruben Schellekens
 */
interface Multiset<E> : Collection<E> {

    /**
     * The total amount of elements in the multiset.
     *
     * This value takes frequencies into account, contrary to [size].
     * E.g. a multiset `[1*A,2*B,3*C]` has a `totalCount` of `6`.
     */
    val totalCount: Int

    /**
     * Get the amount of times the given element is contained in the multiset.
     *
     * @param element
     *          The element to get the frequency of.
     * @return The frequency of the given element in the multiset. Always nonnegative.
     */
    fun count(element: E): Int

    /**
     * Iterator that iterates over all elements in the multiset (frequency > 0) with their frequencies.
     *
     * All pairs have the elements in the multiset as first element, and their frequencies as second element.
     */
    fun valueIterator(): Iterator<Pair<E, Int>>

    /** See [count]. **/
    operator fun get(element: E) = count(element)
}

/**
 * A generic unordered collection of elements that does support duplicate elements,
 * and supports adding and removing elements.
 *
 * The total amount of elements (including frequencies) are available through [totalCount].
 * The total amount of distinct keys is available through [size].
 *
 * @param E
 *          The type of elements contained in the multiset.
 *          The mutable multisetset is invariant on its element type.
 *
 * @author Ruben Schellekens
 */
interface MutableMultiset<E> : Multiset<E>, MutableCollection<E> {

    /**
     * Adds the given element to the collection with an initial count.
     *
     * Does nothing when the given element is already present in the multiset.
     *
     * @param element
     *          The element to add.
     * @param initialCount
     *          The initial frequency of the given element.
     *          Must be non-negative.
     * @throws IllegalArgumentException When the `initialCount` is negative.
     */
    @Throws(IllegalArgumentException::class)
    fun add(element: E, initialCount: Int)

    /**
     * Set the frequency of the given element.
     *
     * @param element
     *          The element to set the frequency of.
     * @param count
     *          The new frequency of the element. Must be nonnegative. A value of 0 will remove the element from
     *          the set.
     * @throws IllegalArgumentException when the new frequency is negative.
     */
    @Throws(IllegalArgumentException::class)
    fun setCount(element: E, count: Int)

    /**
     * Removes all instances of the given element from the multiset and returns its last count.
     *
     * @param element
     *          The element to remove all instances from the multiset of.
     * @return The previous count of the given element in the multiset.
     */
    fun clearElement(element: E): Int

    /** See [setCount]. **/
    operator fun set(element: E, count: Int) = setCount(element, count)
}

/**
 * An immutable multiset without any elements.
 *
 * @author Ruben Schellekens
 */
internal class EmptyMultiset<E> : Multiset<E> {

    override val totalCount = 0
    override val size = 0

    override fun contains(element: E) = false
    override fun containsAll(elements: Collection<E>) = false
    override fun isEmpty() = true
    override fun iterator() = EmptyIterator
    override fun count(element: E) = 0
    override fun valueIterator() = EmptyIterator
}

/**
 * Iterator that doesn't iterate over anything.
 *
 * @author JetBrains
 */
internal object EmptyIterator : ListIterator<Nothing> {
    override fun hasNext(): Boolean = false
    override fun hasPrevious(): Boolean = false
    override fun nextIndex(): Int = 0
    override fun previousIndex(): Int = -1
    override fun next(): Nothing = throw NoSuchElementException()
    override fun previous(): Nothing = throw NoSuchElementException()
}

/**
 * Returns an empty read-only multiset.
 */
fun <T> emptyMultiset(): Multiset<T> = EmptyMultiset()

/**
 * Returns a new read-only multiset with the given elements.
 */
fun <T> multisetOf(vararg elements: T): Multiset<T> = if (elements.isNotEmpty()) {
    val result = HashMultiset<T>()
    result.addAll(elements)
    result
}
else emptyMultiset()

/**
 * Returns a new multiset with the given elements.
 */
fun <T> mutableMultisetOf(vararg elements: T): MutableMultiset<T> {
    val result = HashMultiset<T>()
    result.addAll(result)
    return result
}

/**
 * Returns a new read-only [Multiset] of all elements.
 */
fun <T> Iterable<T>.toMultiset(): Multiset<T> = toMutableMultiset()

/**
 * Returns a new [MutableMultiset] of all elements.
 */
fun <T> Iterable<T>.toMutableMultiset(): MutableMultiset<T> {
    val result = HashMultiset<T>()
    result.addAll(this)
    return result
}

/**
 * Returns a new read-only [Multiset] with all elements from the array.
 */
fun <T> Array<T>.toMultiset() = if (isEmpty()) emptyMultiset() else multisetOf(*this)

/**
 * Returns a new [MutableMultiset] with all elements from the array.
 */
fun <T> Array<T>.toMutableMultiset() = mutableMultisetOf(*this)

/**
 * Uses the contents of a map to construct a multiset.
 *
 * All keys will be assigned frequencies corresponding to their values in the map.
 * All values must be nonnegative.
 *
 * @throws IllegalArgumentException When a map value is negative.
 */
@Throws(IllegalArgumentException::class)
fun <T> Map<T, Int>.toMutableMultiset(): MutableMultiset<T> {
    val result = HashMultiset<T>()
    for (entry in entries) {
        result.add(entry.key, entry.value)
    }
    return result
}

/**
 * Uses the contents of a map to construct a read-only multiset.
 *
 * All keys will be assigned frequencies corresponding to their values in the map.
 * All values must be nonnegative.
 *
 * @throws IllegalArgumentException When a map value is negative.
 */
@Throws(IllegalArgumentException::class)
fun <T> Map<T, Int>.toMultiset(): Multiset<T> = toMutableMultiset()