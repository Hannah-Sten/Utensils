package nl.hannahsten.utensils.collections

import java.util.*
import kotlin.math.max

/**
 * Implementation of [MutableMultiset] with a [HashMap] as underlying data structure.
 *
 * Performance is similar to that of [HashMap] for all basic operations.
 *
 * @author Hannah Schellekens
 */
open class HashMultiset<E> : MutableMultiset<E> {

    private val elements = HashMap<E, Int>()

    override val size: Int
        get() = elements.size

    override val totalCount: Int
        get() = elements.values.sum()

    override fun count(element: E) = elements[element] ?: 0

    override fun setCount(element: E, count: Int) {
        require(count >= 0) { "Value must be non-negative, got $count" }

        if (count == 0) {
            elements.remove(element)
        }
        else {
            elements[element] = count
        }
    }

    override fun clearElement(element: E) = elements.remove(element) ?: 0

    override fun isEmpty() = elements.isEmpty()

    override fun clear() = elements.clear()

    override fun contains(element: E) = elements.containsKey(element)

    override fun containsAll(elements: Collection<E>) = elements.all { contains(it) }

    override fun put(element: E, initialCount: Int) {
        require(initialCount >= 0) { "Initial count must be non-negative, got $initialCount" }

        if (element in this) {
            return
        }

        if (initialCount > 0) {
            elements[element] = initialCount
        }
    }

    override fun add(element: E, amount: Int) {
        val newValue = this[element] + amount
        setCount(element, max(0, newValue))
    }

    override fun add(element: E): Boolean {
        val newValue = elements[element] ?: 0
        elements[element] = newValue + 1
        return true
    }

    override fun addAll(elements: Collection<E>): Boolean {
        var changed = false
        elements.forEach {
            val result = add(it)
            changed = result || changed
        }
        return changed
    }

    override fun remove(element: E): Boolean {
        val value = elements[element] ?: 0
        return if (value <= 1) {
            (elements.remove(element) ?: 0) > 0
        }
        else {
            elements[element] = value - 1
            true
        }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        var changed = false
        elements.forEach {
            val result = remove(it)
            changed = result || changed
        }
        return changed
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        var changed = false

        val iterator = this.elements.keys.iterator()
        while (iterator.hasNext()) {
            val key = iterator.next()
            if (key !in elements) {
                changed = true
                iterator.remove()
            }
        }

        return changed
    }

    override fun valueIterator() = elements.entries
            .map { (k, v) -> Pair(k, v) }
            .iterator()

    override fun iterator() = elements.keys.iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HashMultiset<*>) return false
        if (elements != other.elements) return false
        return true
    }

    override fun hashCode(): Int {
        return elements.hashCode()
    }

    override fun toString(): String {
        return "[${elements.entries.joinToString(", ") { "${it.value}*${it.key}" }}]"
    }
}