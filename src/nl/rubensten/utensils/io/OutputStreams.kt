package nl.rubensten.utensils.io

import java.io.OutputStream
import java.io.PrintStream

/**
 * Creates a [PrintStream] from the given [OutputStream].
 */
fun OutputStream.printStream(autoFlush: Boolean = false) = PrintStream(this, autoFlush)

/**
 * Creates a [PrintStream] from the given [OutputStream].
 */
fun OutputStream.printStream(encoding: String, autoFlush: Boolean = false) = PrintStream(this, autoFlush, encoding)