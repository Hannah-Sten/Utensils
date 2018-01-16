package nl.rubensten.utensils.string

/**
 * Set of characters.
 *
 * @author Ruben Schellekens
 */
open class Charset(

        /**
         * All the characters in the character set.
         */
        val characters: List<Char>
) {

    companion object {

        /**
         * No characters.
         */
        @JvmField
        val EMPTY = Charset("")

        /**
         * All lowercase letters (a-z).
         */
        @JvmField
        val LETTERS_LOWER = Charset("abcdefghijklmnopqrstuvwxyz")

        /**
         * All uppercase letters (A-Z).
         */
        @JvmField
        val LETTERS_UPPER = Charset("ABCDEFGHIJKLMNOPQRSTUVWXYZ")

        /**
         * All letters, both uppercase and lowercase (a-z & A-Z).
         */
        @JvmField
        val LETTERS = LETTERS_LOWER + LETTERS_UPPER

        /**
         * All numbers (0-9).
         */
        @JvmField
        val NUMBERS = Charset("0123456789")

        /**
         * All letters, uppercase and lowercase, and all numbers (a-z & A-Z & 0-9).
         */
        @JvmField
        val NUMBERS_LETTERS = NUMBERS + LETTERS

        /**
         * A few special characters, mostly interpunction: `!@#$%^&*()/\><.;:"'€`|[]{}=+_` and space.
         */
        @JvmField
        val SPECIAL = Charset("!@#\$%^&*()/\\><.;:\"'€`|[]{}=+_ ")

        /**
         * All letters, lowercase and uppercase, numbers, and some special characters (a-z & A-Z & 0-9 & [SPECIAL]).
         */
        @JvmField
        val LETTERS_NUMBERS_SPECIAL = NUMBERS_LETTERS + SPECIAL

        /**
         * All Greek lowercase letters (alpha-omega).
         */
        @JvmField
        val GREEK_LOWER = Charset(Greek.ALL_LOWER)

        /**
         * All Greek uppercase letters (Alpha-Omega).
         */
        @JvmField
        val GREEK_UPPER = Charset(Greek.ALL_UPPER)

        /**
         * All Greek letters, both lowercase and uppercase, (alpha-omega & Alpha-Omega).
         */
        @JvmField
        val GREEK = GREEK_LOWER + GREEK_UPPER

        /**
         * All characters in the pre-defined character sets in [Charset].
         */
        @JvmField
        val ALL = LETTERS_NUMBERS_SPECIAL + GREEK

        /**
         * All numbers and letters mapped to their digit values.
         */
        @JvmField
        val DIGITS = NUMBERS + LETTERS_LOWER
    }

    /**
     * New charset with all the characters in the given string.
     */
    constructor(characterString: String) : this(characterString.toCharArray().asList())

    /**
     * String containing all the characters in the character set.
     */
    val string: String by lazy { characters.joinToString() }

    /**
     * The amount of characters (including duplicates) in the charset.
     */
    val size: Int by lazy { string.length }

    /**
     * Set containing all the characters in the character set.
     */
    val charSet: Set<Char> by lazy { characters.toSet() }

    /**
     * The amount of distinct characters in the charset.
     */
    val distinctSize: Int by lazy { charSet.size }

    /**
     * Joins all characters in both charsets, allows duplicates.
     */
    fun join(other: Charset) = Charset(string + other.string)

    /**
     * Checks if the given character is in the charset.
     */
    operator fun contains(char: Char) = charSet.contains(char)

    /** See [join]. **/
    operator fun plus(other: Charset) = join(other)
}