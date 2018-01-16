package nl.rubensten.utensils.string

import java.util.*

/**
 * Counts how many times the given fragment occurs in the given string without overlaps.
 *
 * E.g. `"cbabbabababc".count("bab")` returns `3`.
 *
 * @param fragment
 *          The fragment to count in the source string, must not be empty.
 * @return The amount of times the fragment occurs in the given string without overlapping letters.
 * @throws IllegalArgumentException When the fragment is the empty string.
 */
@Throws(IllegalArgumentException::class)
fun String.count(fragment: String): Int {
    require(fragment.isNotEmpty()) { "Fragment must not be the empty string. " }

    val targetLength = fragment.length
    var count = 0

    // When fragment is larger than whatever of the string is left: quit.
    var i = 0
    while (i + targetLength <= this.length) {
        // Scans all characters for the characters in the fragment.
        val allCharactersMatch = fragment.indices.none { this[i + it] != fragment[it] }

        // When all are found, advance 1 extra.
        if (allCharactersMatch) {
            count++
            i += fragment.length
        } else {
            i++
        }
    }

    return count
}

/**
 * Turns all lowercase letters to uppercase, and turns all uppercase letters to lowercase.
 * Characters without a case are ignored.
 */
fun String.toggleCase(): String = buildString(length) {
    for (char in this@toggleCase) {
        append(char.toggleCase())
    }
}

/**
 * Turns the character to lowercase if it's uppercase and vice versa.
 * Ignores characters without case.
 */
fun Char.toggleCase(): Char = when (this) {
    this.toUpperCase() -> this.toLowerCase()
    this.toLowerCase() -> this.toUpperCase()
    else -> this
}

/**
 * Create a string where every letter will either be lowercase or uppercase. The case is
 * determined by fate.
 */
@JvmOverloads
fun String.randomCapitals(random: Random = Random()): String = buildString {
    for (char in this@randomCapitals) {
        val result = if (random.nextBoolean()) char.toUpperCase() else char.toLowerCase()
        append(result)
    }
}

/**
 * Cuts a string in half and puts the two parts in a [Pair].
 *
 * For example:
 * `"sauce".splitInTwo(3)` will result in a Couple containing the String "sau" and the
 * string "ce".
 *
 * @param lengthFirstWord
 *         The length the first part of the string has to be. Has to be smaller or equal to the
 *         length of the string, otherwise an [IndexOutOfBoundsException] will be thrown.
 * @return A [Pair] containing the split string.
 * @throws IndexOutOfBoundsException
 *         when the length of the first word is greater than the actual length of the string,
 *         or when the length is smaller than zero.
 */
@Throws(IndexOutOfBoundsException::class)
fun String.splitInTwo(lengthFirstWord: Int): Pair<String, String> {
    require(lengthFirstWord <= length) { "length must be <= string length, got $lengthFirstWord" }
    require(lengthFirstWord >= 0) { "length must be nonnegative, got $lengthFirstWord" }

    val part0 = substring(0, lengthFirstWord)
    val part1 = substring(lengthFirstWord)
    return Pair(part0, part1)
}

/**
 * [splitInTwo] but then splits at the middle of the string.
 */
fun String.splitInHalf(): Pair<String, String> {
    val index = length / 2 + length % 2
    return splitInTwo(index)
}

/**
 * Decapitalises the first character of a string.
 */
fun String.firstLower(): String {
    if (this == "") return ""
    val first = substring(0, 1)
    return first.toLowerCase() + substring(1)
}

/**
 * Capitalises the first character of a string.
 */
fun String.firstUpper(): String {
    if (this == "") return ""
    val first = substring(0, 1)
    return first.toUpperCase() + substring(1)
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
 * Shuffles the letters, i.e. results in a string with a random permutation of the original characters.
 */
@JvmOverloads
fun String.shuffle(random: Random = Random()): String {
    TODO("Shuffle")
}

/**
 * Generate a random string of a certain length from a given charset (default charsets can be found in [Charset]).
 *
 * @param length
 *         The amount of characters in the random string, nonnegative.
 * @param charset
 *         The characters the string can contain. Defaults to [Charset.LETTERS_LOWER]. Must not be empty.
 * @return A random string of length `length` consisting of only characters in `charset`.
 * @throws IllegalArgumentException When the length is negative or when the charset is empty.
 */
@JvmOverloads
@Throws(IllegalArgumentException::class)
fun String.Companion.random(length: Int, charset: Charset = Charset.LETTERS_LOWER, random: Random = Random()): String {
    require(length >= 0) { "Length must be nonnegative, got $length" }
    require(charset.size > 0) { "Charset must not be empty!" }

    return buildString(length) {
        for (i in 1..length) {
            val index = random.nextInt(charset.size)
            append(charset.characters[index])
        }
    }
}