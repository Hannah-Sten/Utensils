package nl.rubensten.utensils.io

import java.io.OutputStream

/**
 * @author Ruben Schellekens
 */
open class MultiOutputStream(private val outputStreams: Collection<OutputStream>) : OutputStream() {

    constructor(vararg outputStreams: OutputStream) : this(outputStreams.toList())

    override fun write(b: Int) = outputStreams.forEach {
        it.write(b)
    }
}