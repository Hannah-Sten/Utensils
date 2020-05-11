package nl.hannahsten.utensils.io

import java.io.Closeable
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Packs given files into a `.zip` archive.
 *
 * [Zipper] is a [Closable] resource that must be closed after using.
 *
 * @author Hannah Schellekens
 */
open class Zipper(

        /**
         * The file to which the `.zip` archive must be written.
         */
        outputFile: File,

        /**
         * The buffer size in bytes that is being used when reading the input file.
         */
        private val bufferSize: Int = 1024

) : Closeable {

    /**
     * The output stream to which the `.zip` archive is written.
     */
    private val zipOutput: ZipOutputStream = ZipOutputStream(outputFile.outputStream())

    /**
     * Creates a new zipper that writes to a file with a given name and reads that file with a given buffer size in
     * bytes.
     */
    constructor(fileName: String, bufferSize: Int = 1024) : this(File(fileName), bufferSize)

    /**
     * Adds the given file to the `.zip` archive.
     * Immediately writes when this method is called.
     */
    infix fun zip(file: File) {
        val entry = ZipEntry(file.name)
        zipOutput.putNextEntry(entry)
        file.inputStream().transferTo(zipOutput, bufferSize = bufferSize)
        zipOutput.closeEntry()
    }

    /**
     * Adds the file with the given name to the `.zip` archive.
     * Immediately writes when this method is called.
     */
    infix fun zip(fileName: String) = zip(File(fileName))

    /**
     * Adds all given files to the `.zip` archive.
     * Immediately writes when this method is called.
     */
    infix fun zip(files: Iterable<File>) = files.forEach { zip(it) }

    /**
     * Adds all given files to the `.zip` archive.
     * Immediately writes when this method is called.
     */
    fun zip(vararg files: File) = files.forEach { zip(it) }

    /**
     * Adds the files with the given names to the `.zip` archive.
     * Immediately writes when this method is called.
     */
    infix fun zipNames(fileNames: Iterable<String>) = fileNames.forEach { zip(it) }

    /**
     * Adds the files with the given names to the `.zip` archive.
     * Immediately writes when this method is called.
     */
    fun zipNames(vararg fileNames: String) = fileNames.forEach { zip(it) }

    override fun close() = zipOutput.close()
}