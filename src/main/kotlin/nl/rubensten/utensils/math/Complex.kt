package nl.rubensten.utensils.math

import nl.rubensten.utensils.math.matrix.isZero
import java.io.Serializable
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A complex number `a+bi`.
 *
 * @author Ruben Schellekens
 */
class Complex(val a: Double, val b: Double) : Serializable {

    companion object {

        private const val serialVersionUID = 96062259058757105L

        /**
         * 0
         */
        @JvmField val ZERO = Complex(0.0, 0.0)

        /**
         * 1+0i
         */
        @JvmField val ONE = Complex(1.0, 0.0)

        /**
         * 1+1i
         */
        @JvmField val ONE_EIGHTH = Complex(1.0, 1.0)

        /**
         * 0+1i
         */
        @JvmField val I = Complex(0.0, 1.0)

        /**
         * -1+1i
         */
        @JvmField val THREE_EIGHTS = Complex(-1.0, 1.0)

        /**
         * -1+0i
         */
        @JvmField val HALF = Complex(-1.0, 0.0)

        /**
         * -1-1i
         */
        @JvmField val MINUS_THREE_EIGHTS = Complex(-1.0, -1.0)

        /**
         * 0-i1
         */
        @JvmField val THREE_QUARTS = Complex(0.0, -1.0)

        /**
         * 1-1i
         */
        @JvmField val SEVEN_EIGHTS = Complex(1.0, -1.0)

        /**
         * Parses from the format "a+bi" or "a-bi".
         */
        @JvmStatic
        fun parse(complexString: String): Complex {
            var toSetA: Double
            var toSetB: Double

            try {
                val elements = complexString.replace(" ".toRegex(), "")
                        .split("\\+".toRegex())
                        .dropLastWhile({ it.isEmpty() })
                        .toTypedArray()
                toSetA = java.lang.Double.parseDouble(elements[0])
                toSetB = java.lang.Double.parseDouble(elements[1].replace("i", ""))
            }
            catch (e: NumberFormatException) {
                val startsWithMinus = complexString.trim({ it <= ' ' }).startsWith("-")
                val elements = complexString.replace(" ".toRegex(), "")
                        .split("-".toRegex())
                        .dropLastWhile({ it.isEmpty() })
                        .toTypedArray()

                val real: String
                val imaginary: String
                if (startsWithMinus) {
                    real = "-" + elements[1]
                    imaginary = "-" + elements[2].replace("i", "")
                }
                else {
                    real = elements[0]
                    imaginary = "-" + elements[1].replace("i", "")
                }

                toSetA = java.lang.Double.parseDouble(real)
                toSetB = java.lang.Double.parseDouble(imaginary)
            }

            return Complex(toSetA, toSetB)
        }
    }

    /**
     * Rotates the complex number over a certain angle in radians.
     *
     * @param angle
     *            The angle in radians.
     * @return A new complex number where `z=this*e^θi`.
     */
    infix fun rotate(angle: Double): Complex {
        val a = cos(angle)
        val b = sin(angle)
        val w = Complex(a, b)
        return multiply(w)
    }

    /**
     * Divides this complex number by the given complex number.
     *
     * @return A new complex number `z=this/c`.
     */
    infix fun divide(other: Complex): Complex {
        val re = other.real()
        val im = other.imag()
        val bottom = re * re + im * im
        val newA = (real() * re + imag() * im) / bottom
        val newB = (imag() * re - real() * im) / bottom
        return Complex(newA, newB)
    }

    /**
     * Divides this complex number by a scalar.
     *
     * @return A new complex number `z=this*(1/scalar)`.
     */
    infix fun divide(scalar: Double) = multiply(1.0 / scalar)

    /**
     * Multiplies a complex number to this complex number.
     *
     * @return A new complex number `z=this*c`.
     */
    infix fun multiply(other: Complex): Complex {
        val newA = a * other.real() - b * other.imag()
        val newB = a * other.imag() + b * other.real()
        return Complex(newA, newB)
    }

    /**
     * Multiplies the complex number with a scalar.
     *
     * @return A new complex number `z=this*c`
     */
    infix fun multiply(scalar: Double): Complex {
        return Complex(scalar * a, scalar * b)
    }

    /**
     * Subtracts a complex number from this complex number.
     *
     * @return A new complex number `z=this-c`.
     */
    infix fun subtract(other: Complex): Complex {
        val newA = real() - other.real()
        val newB = imag() - other.imag()
        return Complex(newA, newB)
    }

    /**
     * Subtracts a number to the complex number.
     *
     * @return A new complex number `z=this-number`.
     */
    infix fun subtract(number: Double): Complex {
        return Complex(a - number, b)
    }

    /**
     * Adds a complex number to this complex number.
     *
     * @return A new complex number `z=this+c`.
     */
    infix fun add(other: Complex): Complex {
        val newA = real() + other.real()
        val newB = imag() + other.imag()
        return Complex(newA, newB)
    }

    /**
     * Adds a number to the complex number.
     *
     * @return A new complex number `z=this+number`.
     */
    infix fun add(number: Double): Complex {
        return Complex(a + number, b)
    }

    /**
     * Calculates the inverse of the complex number (`1/z`).
     */
    fun inverse(): Complex {
        val squared = a * a + b * b
        return Complex(a / squared, -b / squared)
    }

    /**
     * Get the conjugated Complex number (a-bi).
     */
    fun conj() = Complex(a, -b)

    /**
     * Returns the real part of the Complex number (a).
     */
    fun real() = a

    /**
     * Returns the imaginary part of the Complex number (b).
     */
    fun imag() = b

    /**
     * Returns the argument (`arg(z)`) of the Complex number.
     */
    fun arg() = atan2(b, a)

    /**
     * Returns the modulus (`|z|`) of the Complex number.
     */
    fun modulus() = sqrt(a * a + b * b)

    /** See [add] **/
    operator fun plus(other: Complex) = add(other)

    /** See [add] **/
    operator fun plus(number: Number) = add(number.toDouble())

    /** See [subtract] **/
    operator fun minus(other: Complex) = subtract(other)

    /** See [subtract] **/
    operator fun minus(number: Number) = subtract(number.toDouble())

    /** See [multiply] **/
    operator fun times(other: Complex) = multiply(other)

    /** See [multiply] **/
    operator fun times(number: Number) = multiply(number.toDouble())

    /** See [divide] **/
    operator fun div(other: Complex) = divide(other)

    /** See [divide] **/
    operator fun div(number: Number) = divide(number.toDouble())

    /** Multiplies by `-1` **/
    operator fun unaryMinus() = multiply(-1.0)

    /** Does nothing **/
    operator fun unaryPlus() = this

    /**
     * Index 0: a (real part), Index 1: b (imaginary part).
     *
     * @throws IndexOutOfBoundsException When index != 1 or 0
     */
    operator fun get(index: Int) = when (index) {
        0 -> a
        1 -> b
        else -> throw IndexOutOfBoundsException("Only 0 and 1 allowed, got $index")
    }

    /**
     * `a+b*i*`
     */
    override fun toString(): String {
        if (a.isZero() && b.isZero()) {
            return "0"
        }
        else if (a.isZero()) {
            return b.toString() + "i"
        }
        else if (b.isZero()) {
            return a.toString() + ""
        }

        val sign = if (b < 0) "" else "+"
        return a.toString() + sign + b + "i"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Complex) return false

        if (a != other.a) return false
        if (b != other.b) return false

        return true
    }

    override fun hashCode(): Int {
        var result = a.hashCode()
        result = 31 * result + b.hashCode()
        return result
    }
}

/**
 * See [Complex.parse].
 */
fun String.toComplex() = Complex.parse(this)

/**
 * Complex number `this*i`.
 */
val Number.i
    get() = Complex(0.0, this.toDouble())

/**
 * Creates imaginary number `this+other*i`
 */
infix fun Number.i(imaginary: Number) = Complex(this.toDouble(), imaginary.toDouble())

/**
 * See [Complex.add]
 */
operator fun Number.plus(complex: Complex) = complex.add(toDouble())

/**
 * See [Complex.subtract]
 */
operator fun Number.minus(complex: Complex) = complex.subtract(toDouble())

/**
 * See [Complex.multiply]
 */
operator fun Number.times(complex: Complex) = complex.multiply(toDouble())

/**
 * See [Complex.divide]
 */
operator fun Number.div(complex: Complex) = Complex(toDouble(), 0.0) / complex