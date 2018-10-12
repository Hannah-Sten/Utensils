package nl.rubensten.utensils.collections

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * @author Ruben Schellekens
 */
abstract class MultisetTest {

    /**
     * Hash multiset without any elements.
     */
    abstract val emptySet: MutableMultiset<Int>

    /**
     * Test set with:
     * `1*-1`, `3*2`, `2*3`, `1*4`.
     */
    abstract val testSet: MutableMultiset<Int>

    @Test
    fun emptyMultiset() {
        assertMultisetEquals(emptySet, emptyMultiset<Int>())
        assertEquals(0, emptyMultiset<Int>().size)
        assertEquals(0, emptyMultiset<Int>().totalCount)
    }

    @Test
    fun multisetOf() {
        assertMultisetEquals(emptySet, multisetOf<Int>())
        val created = multisetOf(-1, 2, 2, 2, 3, 3, 4)
        assertMultisetEquals(testSet, created)
    }

    @Test
    fun mutableMultisetOf() {
        assertMultisetEquals(emptySet, mutableMultisetOf<Int>())
        val created = mutableMultisetOf(-1, 2, 2, 2, 3, 3, 4)
        assertMultisetEquals(testSet, created)
    }

    @Test
    fun toMultiset() {
        val elts = listOf(-1, 2, 2, 2, 3, 3, 4)
        assertMultisetEquals(testSet, elts.toMultiset())
        assertMultisetEquals(emptySet, emptyList<Int>().toMultiset())
    }

    @Test
    fun toMutableMultiset() {
        val elts = listOf(-1, 2, 2, 2, 3, 3, 4)
        assertMultisetEquals(testSet, elts.toMutableMultiset())
        assertMultisetEquals(emptySet, emptyList<Int>().toMutableMultiset())
    }

    @Test
    fun arrayToMultiset() {
        val empty = emptyArray<Int>()
        val array = intArrayOf(-1, 2, 2, 2, 3, 3, 4)
        assertMultisetEquals(emptySet, empty.toMultiset())
        assertMultisetEquals(testSet, array.toMultiset())
    }

    @Test
    fun arrayToMutableMultiset() {
        val empty = emptyArray<Int>()
        val array = intArrayOf(-1, 2, 2, 2, 3, 3, 4)
        assertMultisetEquals(emptySet, empty.toMutableMultiset())
        assertMultisetEquals(testSet, array.toMutableMultiset())
    }

    @Test
    fun mapToMultiset() {
        val empty = emptyMap<Int, Int>()
        val map = mapOf(
                -1 to 1,
                2 to 3,
                3 to 2,
                4 to 1
        )
        assertMultisetEquals(emptySet, empty.toMultiset())
        assertMultisetEquals(testSet, map.toMultiset())
    }

    @Test
    fun mapToMutableMultiset() {
        val empty = emptyMap<Int, Int>()
        val map = mapOf(
                -1 to 1,
                2 to 3,
                3 to 2,
                4 to 1
        )
        assertMultisetEquals(emptySet, empty.toMutableMultiset())
        assertMultisetEquals(testSet, map.toMutableMultiset())
    }

    @Test
    fun getSize() {
        assertEquals(0, emptySet.size)
        assertEquals(4, testSet.size)
    }

    @Test
    fun getTotalCount() {
        assertEquals(0, emptySet.totalCount)
        assertEquals(7, testSet.totalCount)
    }

    @Test
    fun count() {
        val empty = emptySet
        (-3..7).forEach { assertEquals(0, empty[it]) }

        val test = testSet
        assertEquals(1, test[-1])
        assertEquals(0, test[0])
        assertEquals(0, test[1])
        assertEquals(3, test[2])
        assertEquals(2, test[3])
        assertEquals(1, test[4])
        assertEquals(0, test[5])
        assertEquals(0, test[Int.MAX_VALUE])
    }

    @Test
    fun setCount() {
        val empty = emptySet
        empty.setCount(2, 14)
        empty[4] = Int.MAX_VALUE
        assertEquals(14, empty[2])
        assertEquals(Int.MAX_VALUE, empty.count(4))

        val test = testSet
        assertEquals(3, test[2])
        test[2] = 19
        assertEquals(19, test.count(2))
    }

    @Test(expected = IllegalArgumentException::class)
    fun setCountNegative() {
        val empty = emptySet
        empty.setCount(3, -1)
    }

    @Test
    fun clearElement() {
        val empty = emptySet
        val naught = empty.clearElement(29)
        assertEquals(0, empty[29])
        assertEquals(0, naught)

        val test = testSet
        val nothing = test.clearElement(-23)
        val threeTwos = test.clearElement(2)
        assertEquals(0, test[-23])
        assertEquals(0, test[2])
        assertEquals(0, nothing)
        assertEquals(3, threeTwos)
    }

    @Test
    fun isEmpty() {
        assertTrue(emptySet.isEmpty())

        val test = testSet
        assertFalse(test.isEmpty())
        test.clearElement(-1)
        test.clearElement(2)
        test[3] = 0
        test.setCount(4, 0)
        assertTrue(test.isEmpty())
    }

    @Test
    fun clear() {
        val empty = emptySet
        empty.clear()
        assertTrue(empty.isEmpty())

        val test = testSet
        test.clear()
        assertTrue(test.isEmpty())
    }

    @Test
    fun contains() {
        val empty = emptySet
        (-3..7).forEach { assertFalse(it in empty) }

        val test = testSet
        assertFalse(-2 in test)
        assertTrue(-1 in test)
        assertFalse(0 in test)
        assertFalse(1 in test)
        assertTrue(2 in test)
        assertTrue(3 in test)
        assertTrue(4 in test)
        assertFalse(5 in test)
        assertFalse(Int.MAX_VALUE in test)
    }

    @Test
    fun containsAll() {
        val empty = emptySet
        assertTrue(empty.containsAll(emptyList()))
        assertFalse(empty.containsAll(listOf(0)))
        assertFalse(empty.containsAll(listOf(1, 2, 3, 4, 5)))

        val test = testSet
        assertTrue(test.containsAll(emptySet()))
        assertTrue(test.containsAll(listOf(3)))
        assertTrue(test.containsAll(listOf(-1, 2, 3, 4)))
        assertFalse(test.containsAll(listOf(-1, 9, 3, 4)))
        assertFalse(test.containsAll(listOf(-10)))
    }

    @Test
    fun put() {
        val empty = emptySet
        empty.put(3, 19)
        assertEquals(19, empty[3])
        empty.put(3, 129389)
        assertEquals(19, empty[3])
    }

    @Test(expected = IllegalArgumentException::class)
    fun putNegativeCount() {
        emptySet.put(3, -1)
    }

    @Test
    fun add() {
        val empty = emptySet
        assertTrue(empty.add(3))
        assertTrue(empty.add(3))
        empty.add(3, 2)
        empty.add(2, 10)
        assertEquals(4, empty[3])
        assertEquals(10, empty[2])
    }

    @Test
    fun addAll() {
        val empty = emptySet
        assertTrue(empty.addAll(listOf(1, 2, 2, 3)))
        assertEquals(1, empty[1])
        assertEquals(2, empty[2])
        assertEquals(1, empty[3])

        val singleton = emptySet
        assertTrue(singleton.addAll(listOf(-19)))
        assertEquals(1, singleton[-19])

        val none = emptySet
        assertFalse(none.addAll(emptyList()))
        assertEquals(0, none[12])
        assertEquals(0, none[-1])
    }

    @Test
    fun remove() {
        val empty = emptySet
        assertFalse(empty.remove(3))
        assertEquals(0, empty.size)

        val test = testSet
        assertTrue(test.remove(4))
        assertTrue(test.remove(2))
        assertFalse(test.remove(4))
        assertEquals(0, test[-2])
        assertEquals(1, test[-1])
        assertEquals(2, test[2])
        assertEquals(2, test[3])
        assertEquals(0, test[4])
    }

    @Test
    fun removeAll() {
        val empty = emptySet
        assertFalse(empty.removeAll(emptyList()))
        assertFalse(empty.removeAll(listOf(1)))
        assertFalse(empty.removeAll(listOf(1, 2, 3, 4)))

        val test = testSet
        assertFalse(test.removeAll(emptyList()))
        assertFalse(test.removeAll(listOf(100)))
        assertFalse(test.removeAll(listOf(100, 200, 300, 400)))
        assertTrue(test.removeAll(listOf(2, 3, 3, 3, 4)))
        assertTrue(test.removeAll(listOf(2)))
        assertEquals(0, test[-2])
        assertEquals(1, test[-1])
        assertEquals(1, test[2])
        assertEquals(0, test[3])
        assertEquals(0, test[4])
    }

    @Test
    fun retainAll() {
        val empty = emptySet
        assertFalse(empty.retainAll(emptyList()))
        assertFalse(empty.retainAll(listOf(1)))
        assertFalse(empty.retainAll(listOf(1, 2, 3, 4)))

        val test = testSet
        assertTrue(test.retainAll(emptyList()))
        assertTrue(test.isEmpty())

        val test1 = testSet
        assertTrue(test1.retainAll(listOf(-1)))
        test1.forEach { assertTrue(it < 0) }

        val test2 = testSet
        assertTrue(test2.retainAll(listOf(2, 4)))
        assertEquals(0, test2[-2])
        assertEquals(0, test2[-1])
        assertEquals(3, test2[2])
        assertEquals(0, test2[3])
        assertEquals(1, test2[4])
        assertEquals(0, test2[5])
    }

    @Test
    fun valueIterator() {
        val empty = emptySet
        assertEquals(0, empty.toList().size)

        val test = testSet
        assertEquals(setOf(-1, 2, 3, 4), test.toSet())
    }

    @Test
    fun `value iterator`() {
        assertEquals(0, emptySet.valueIterator().asSequence().map { it.second }.sum())
        assertEquals(7, testSet.valueIterator().asSequence().map { it.second }.sum())
    }

    @Test
    fun `to string`() {
        assertEquals("[]", emptySet.toString())
        assertEquals("[1*-1, 3*2, 2*3, 1*4]", testSet.toString())
    }

    @Test
    fun equals() {
        assertEquals(emptySet, emptySet)
        assertEquals(testSet, testSet)
        assertNotEquals(emptySet, testSet)
    }

    fun <T> assertMultisetEquals(expected: Multiset<T>, actual: Multiset<T>) {
        assertTrue(expected.elementEquals(actual))
    }

    fun <T> Multiset<T>.elementEquals(other: Multiset<T>): Boolean {
        for (key in this) {
            val value = this[key]
            if (other[key] != value) {
                return false
            }
        }
        for (key in other) {
            val value = other[key]
            if (this[key] != value) {
                return false
            }
        }
        return true
    }
}