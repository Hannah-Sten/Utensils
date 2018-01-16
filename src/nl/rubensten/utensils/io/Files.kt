package nl.rubensten.utensils.io

import nl.rubensten.utensils.string.toHexString
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Compares the checksums of both files.
 *
 * @param other
 *          The file to compare the checksum of.
 * @param algorithm
 *          The hashing algorithm to use, defaults to SHA-256.
 * @return `true` if the files have the same checksum, `false` if not.
 * @throws IOException When files couldn't be found.
 * @throws NoSuchAlgorithmException When the algorithm is not available.
 */
@Throws(IOException::class)
fun File.checksumEquals(other: File, algorithm: String = "SHA-256"): Boolean {
    val checksum0 = checksum(algorithm)
    val checksum1 = other.checksum(algorithm)
    return checksum0 == checksum1
}

/**
 * Generates the checksum of the file.
 *
 * @param algorithm
 *          The hashing algorithm to use, defaults to SHA-256.
 * @return The checksum of the file.
 * @throws IOException When there is a problem with the file I/O.
 * @throws NoSuchAlgorithmException When the algorithm is not available.
 */
@Throws(IOException::class)
fun File.checksum(algorithm: String = "SHA-256"): String {
    val messageDigest = MessageDigest.getInstance(algorithm)
    val inputStream = FileInputStream(this)
    val dataBytes = ByteArray(1024)

    var b = inputStream.read(dataBytes)
    while (b != -1) {
        messageDigest.update(dataBytes, 0, b)
        b = inputStream.read(dataBytes)
    }

    val mdbytes = messageDigest.digest()
    inputStream.close()
    return mdbytes.toHexString()
}