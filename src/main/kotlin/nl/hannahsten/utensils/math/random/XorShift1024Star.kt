package nl.hannahsten.utensils.math.random

import java.util.*

/**
 * XOR Shift 1024* Pseudo Random Number Generator implementation with period `2^1024-1`.
 *
 * @author Hannah Schellekens
 */
open class XorShift1024Star(seed: Long = System.nanoTime()) : Random(seed) {

    private val seed: LongArray
    private var pointer = 0

    init {
        val random = Random(seed)
        this.seed = LongArray(16) { random.nextLong() }
    }

    override fun next(bits: Int): Int {
        val s0 = seed[pointer]
        pointer = (pointer + 1) and 15
        var s1 = seed[pointer]

        s1 = s1 xor (s1 shl 31)
        seed[pointer] = s1 xor s0 xor (s1 ushr 11) xor (s0 ushr 30)
        return ((s1 * 1181783497276652981L) ushr (64 - bits)).toInt()
    }
}