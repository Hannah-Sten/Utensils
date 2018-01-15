package nl.rubensten.utensils.string

import java.util.*

/**
 * Counts how many times the given fragment occurs in the given string without overlaps.
 *
 * E.g. `"cbabbabababc".count("bab")` returns `3`.
 *
 * @param fragment
 *          The fragment to count in the source string.
 * @return The amount of times the fragment occurs in the given string without overlapping letters.
 */
fun String.count(fragment: String): Int {
    TODO("Implement count")
}

/**
 * Turns all lowercase letters to uppercase, and turns all uppercase letters to lowercase.
 * Characters without a case are ignored.
 */
fun String.toggleCase(): String {
    TODO("Toggle case")
}

/**
 * Create a string where every letter will either be lowercase or uppercase. The case is
 * determined by fate.
 */
fun String.randomCapitals(random: Random = Random()): String {
    TODO("Random capitals")
}

/**
 * Cuts a string in half and puts the two parts in a [Pair].
 *
 * For example:
 * <code>"sauce".splitHalf(3)</code> will result in a Couple containing the String "sau" and the
 * string "ce".
 *
 * @param lengthFirstWord
 *         The length the first part of the string has to be. Has to be smaller or equal to the
 *         length of the string, otherwise an [IndexOutOfBoundsException] will be thrown.
 * @return A [Pair] containing the split string.
 * @throws IndexOutOfBoundsException
 *         when the length of the first word is greater than the actual length of the string.
 */
fun String.splitInTwo(lengthFirstWord: Int): Pair<String, String> {
    TODO("Split half")
}

/**
 * [splitInTwo] but then splits at the middle of the string.
 */
fun String.splitInHalf() = splitInTwo(length / 2)

/**
 * Decapitalises the first character of a string.
 */
fun String.firstLower(): String {
    TODO("First lower")
}

/**
 * Capitalises the first character of a string.
 */
fun String.firstUpper(): String {
    TODO("First upper")
}

/**
 * Removes spaces and underscores and capitalises every single word except the first one.
 */
fun String.toCamelCase(): String {
    TODO("Camel case")
}


/**
 * Turns a camelCase or PascalCase string into snake case with words in lowercase separated by underscores.
 */
@Suppress("FunctionName")
fun String.to_snake_case(): String {
    TODO("Snake case")
}

/**
 * Removes spaces and underscores and capitalises every single word.
 */
@Suppress("FunctionName")
fun String.ToPascalCase() = toCamelCase().firstUpper()

/**
 * Generate a random string of a certain length from a given charset (default charsets can be found in [Charset]).
 *
 * @param length
 *         The amount of characters in the random string.
 * @param charset
 *         The characters the string can contain. Defaults to [Charset.LETTERS_LOWER].
 * @return A random string of length `length` consisting of only characters in `charset`.
 */
fun String.Companion.random(length: Int, charset: Charset = Charset.LETTERS_LOWER, random: Random = Random()): String {
    TODO("Random string")
}