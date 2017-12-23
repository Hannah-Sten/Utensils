package nl.rubensten.utensils.general

/** `print()` with `this` as parameter. **/
fun Any?.print() = print(this)

/** `println()` with `this` as parameter. **/
fun Any?.println() = println(this)

/** [println] but then with an extra newline. **/
fun Any?.printlnln() {
    println()
    kotlin.io.println()
}

/** Prints `this` in a formatted string (`%s` to represent `this`). **/
fun Any?.printf(format: String) = format.format(this)