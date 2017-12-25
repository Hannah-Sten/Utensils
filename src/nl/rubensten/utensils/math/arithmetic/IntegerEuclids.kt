package nl.rubensten.utensils.math.arithmetic

/**
 * The extended euclidian algorithm for two integers.
 *
 * Looks for `ax + by = gcd(a, b)` where `a` and `b` are the input variables.
 * [gcd] contains the gcd, [x] and [y] contain `x` and `y` as seen in the previous expression.
 *
 * @author Ruben Schellekens
 */
open class IntegerEuclids(val a: Long, val b: Long) {

    var x: Long? = null
        private set(value) {
            field = value
        }

    var y: Long? = null
        private set(value) {
            field = value
        }

    var gcd: Long? = null
        private set(value) {
            field = value
        }

    /**
     * Executes the extended euclidean algorithm.
     *
     * @return A triple of form `(x, y, gcd(a,b))` (see class documentation of [IntegerEuclids]).
     */
    fun execute(): Triple<Long, Long, Long> {
        // Implementation of Algorithm 2.2 of the lecture notes.

        // Step 1: Init
        var aPrime = Math.abs(a)
        var bPrime = Math.abs(b)
        var x1 = 1L
        var x2 = 0L
        var y1 = 0L
        var y2 = 1L

        // Step 2: Loop
        while (bPrime > 0) {
            val q = Math.floor(aPrime.toDouble() / bPrime.toDouble()).toLong()
            val r = aPrime - q * bPrime
            aPrime = bPrime
            bPrime = r
            val x3 = x1 - q * x2
            val y3 = y1 - q * y2
            x1 = x2
            y1 = y2
            x2 = x3
            y2 = y3
        }

        // Step 3: Finalise
        gcd = aPrime
        x = if (a >= 0) x1 else -x1
        y = if (b >= 0) y1 else -y1

        return Triple(x!!, y!!, gcd!!)
    }
}