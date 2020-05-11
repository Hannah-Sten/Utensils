package nl.hannahsten.utensils.io

import java.io.InputStream
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

/**
 * Transfers all contents of a certain [InputStream] to another [OutputStream].
 * Does close the [InputStream], does **not** close the [OutputStream].
 */
fun InputStream.transferTo(outputStream: OutputStream, bufferSize: Int = 1024) = use {
    val buffer = ByteArray(bufferSize)
    var bytesRead = 0
    while (bytesRead >= 0) {
        bytesRead = it.read(buffer)

        if (bytesRead >= 0) {
            outputStream.write(buffer, 0, bytesRead)
        }
    }
}