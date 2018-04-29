package nl.rubensten.utensils.general

/**
 * `print()` with `this` as parameter.
 *
 * @param prefix
 *          What to print before the object, empty string by default.
 */
fun Any?.print(prefix: Any? = "") = kotlin.io.print("$prefix$this")

/**
 * `println()` with `this` as parameter.
 *
 * @param prefix
 *          What to print before the object, empty string by default.
 */
fun Any?.println(prefix: Any? = "") = kotlin.io.println("$prefix$this")

/**
 * [println] but then with an extra newline.
 *
 * @param prefix
 *          What to print before the object, empty string by default.
 */
fun Any?.printlnln(prefix: Any? = "") {
    println(prefix)
    kotlin.io.println()
}

/**
 * Prints `this` in a formatted string (`%s` to represent `this`).
 */
fun Any?.printf(format: String) = format.format(this).print()