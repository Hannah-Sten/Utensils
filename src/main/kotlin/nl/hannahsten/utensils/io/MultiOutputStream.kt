package nl.hannahsten.utensils.io

import java.io.OutputStream

/**
 * @author Hannah Schellekens
 */
open class MultiOutputStream(private val outputStreams: Iterable<OutputStream>) : OutputStream() {

    constructor(vararg outputStreams: OutputStream) : this(outputStreams.toList())

    override fun write(b: Int) = outputStreams.forEach {
        it.write(b)
    }
}