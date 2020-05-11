package nl.hannahsten.utensils.collections

/**
 * @author Hannah Schellekens
 */
class HashMultisetTest : MultisetTest() {

    override val emptySet
        get() = HashMultiset<Int>()

    override val testSet: HashMultiset<Int>
        get() = HashMultiset<Int>().apply {
            add(2)
            add(2)
            add(4)
            add(-1)
            add(3)
            add(3)
            add(2)
        }
}