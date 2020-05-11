package nl.hannahsten.utensils.string

import kotlin.math.sqrt

/**
 * The Greek alphabet.
 *
 * @author Hannah Schellekens
 */
enum class Greek(val letterName: String, private val lower: String, private val upper: String) {

    ALPHA("alpha", "α", "Α"),
    BETA("beta", "β", "Β"),
    GAMMA("gamma", "γ", "Γ"),
    DELTA("delta", "δ", "Δ"),
    EPSILON("epsilon", "ε", "Ε"),
    ZETA("zeta", "ζ", "Ζ"),
    ETA("eta", "η", "Η"),
    THETA("theta", "θ", "Θ"),
    IOTA("iota", "ι", "Ι"),
    KAPPA("kappa", "κ", "Κ"),
    LAMBDA("lambda", "λ", "Λ"),
    MU("mu", "μ", "Μ"),
    NU("nu", "ν", "Ν"),
    XI("xi", "ξ", "Ξ"),
    OMICRON("omicron", "ο", "Ο"),
    PI("pi", "π", "Π"),
    RHO("rho", "ρ", "Ρ"),
    SIGMA("sigma", "σ", "Σ"),
    TAU("tau", "τ", "Τ"),
    UPSILON("upsilon", "υ", "Υ"),
    PHI("phi", "φ", "Φ"),
    CHI("chi", "χ", "Χ"),
    PSI("psi", "ψ", "Ψ"),
    OMEGA("omega", "ω", "Ω");

    companion object {

        /**
         * Returns one long string of all lowercase letters.
         */
        @JvmField
        val ALL_LOWER = values().joinToString("")

        /**
         * Returns one long string of all uppercase letters.
         */
        @JvmField
        val ALL_UPPER = ALL_LOWER.toUpperCase()
    }

    /**
     * Gets the numerical value of mathematical constants.
     *
     * @return The value of the constant, or 1 if there is no value.
     */
    fun value() = when (this) {
        PI -> Math.PI
        PHI -> (1 + sqrt(5.0)) / 2.0
        TAU -> Math.PI * 2
        else -> 1.0
    }

    /**
     * Get the alternative ς in case of sigma.
     *
     * For other letters, returns the same lowercase letter.
     */
    fun alt(): String {
        return if (this == SIGMA) {
            "ς"
        } else lower
    }

    /**
     * The uppercase variant of the letter.
     */
    fun upper() = this.upper

    /**
     * The lowercase variant of the letter.
     */
    fun lower() = this.lower

    /**
     * The lowercase letter.
     */
    override fun toString() = this.lower
}