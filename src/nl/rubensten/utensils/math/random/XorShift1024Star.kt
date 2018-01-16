package nl.rubensten.utensils.math.random

import java.util.*

/**
 * XOR Shift 1024* Pseudo Random Number Generator implementation with period `2^1024-1`.
 *
 * When not given a seed, it will use the current time in nanoseconds as seed.
 *
 * @author Ruben Schellekens
 */
open class XorShift1024Star(seed: Long = System.nanoTime()) : Random(seed) {

    private val seed = LongArray(16) { seed }
    private var pointer = 0

    override fun next(bits: Int): Int {
        val s0 = seed[pointer]
        pointer = (pointer + 1) and 15
        var s1 = seed[pointer]

        s1 = s1 xor (s1 shl 31)
        seed[pointer] = s1 xor s0 xor (s1 ushr 11) xor (s0 ushr 30)
        return ((s1 * 1181783497276652981L) ushr (64 - bits)).toInt()
    }
}