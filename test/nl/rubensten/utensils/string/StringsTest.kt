package nl.rubensten.utensils.string

import nl.rubensten.utensils.collections.toMultiset
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

/**
 * @author Ruben Schellekens
 */
class StringsTest {

    @Test
    fun `String#count`() {
        // From example:
        val string = "cbabbabababc"
        val countBab = string.count("bab")
        assertEquals(3, countBab)

        // Single letters
        val countA = string.count("a")
        assertEquals(4, countA)

        // Same string
        val countSame = string.count(string)
        assertEquals(1, countSame)

        // Empty string
        val countEmpty= "".count("bab")
        assertEquals(0, countEmpty)

        // Empty fragment
        assertFailsWith(IllegalArgumentException::class) {
            string.count("")
        }

        // Non occuring string
        val countNone = string.count("dof")
        assertEquals(0, countNone)
    }

    @Test
    fun `String#toggleCase`() {
        val testCases = mapOf(
                "" to "",
                "a" to "A",
                "pdfá" to "PDFÁ",
                "K" to "k",
                "LÖSS" to "löss",
                "PaP" to "pAp",
                "AJAF3ojfkaAFJ3 aÑf kj3" to "ajaf3OJFKAafj3 AñF KJ3"
        )

        testCases.entries.forEach {
            val toggled = it.key.toggleCase()
            assertEquals(it.value, toggled, "${it.key} -> ${it.value}")
        }
    }

    @Test
    fun `String#randomCapitals`() {
        val random = Random(0xCAFEBABE)
        val test = "Ad 3 jDkf @kL"
        val capitals = test.randomCapitals(random)

        // Test length.
        assertEquals(test.length, capitals.length, "Same length")

        // Special unchanged characters
        for (i in setOf(2, 3, 4, 9, 10)) {
            assertEquals(test[i], capitals[i], "Unchanged special characters")
        }

        // Capitalised letters.
        for (i in setOf(0, 1, 5, 6, 7, 8, 11, 12)) {
            val testLower = test[i].toLowerCase()
            val generatedLower = capitals[i].toLowerCase()
            assertEquals(testLower, generatedLower, "Capitalised letters")
        }
    }

    @Test
    fun `String#splitInTwo`() {
        val testCases = mapOf(
                ("sauce" to 3) to ("sau" to "ce"),
                ("Kam eel" to 3) to ("Kam" to " eel"),
                ("" to 0) to ("" to ""),
                ("a" to 1) to ("a" to  ""),
                ("a" to 0) to ("" to "a"),
                ("aabbbbb" to 2) to ("aa" to "bbbbb")
        )

        testCases.entries.forEach {
            val (source, length) = it.key
            val expected = it.value
            val actual = source.splitInTwo(length)
            assertEquals(expected, actual)
        }

        // Too large length.
        assertFailsWith(IllegalArgumentException::class) {
            "haha".splitInTwo(5)
        }

        // Negative length
        assertFailsWith(IllegalArgumentException::class) {
            "haha".splitInTwo(-1)
        }
    }

    @Test
    fun `String#splitInHalf`() {
        val testCases = mapOf(
                "sauce" to ("sau" to "ce"),
                "Kam eel" to ("Kam " to "eel"),
                "" to ("" to ""),
                "a" to ("a" to ""),
                "aaaaabbbbb" to ("aaaaa" to "bbbbb")
        )

        testCases.entries.forEach {
            val actual = it.key.splitInHalf()
            assertEquals(it.value, actual)
        }
    }

    @Test
    fun `String#firstLower`() {
        val testCases = mapOf(
                "broodje" to "broodje",
                "Broodje" to "broodje",
                "BROODJE" to "bROODJE",
                " Broodje" to " Broodje",
                "" to "",
                "Á" to "á"
        )

        testCases.entries.forEach {
            assertEquals(it.value, it.key.firstLower())
        }
    }

    @Test
    fun `String#firstUpper`() {
        val testCases = mapOf(
                "bROODJE" to "BROODJE",
                "Broodje" to "Broodje",
                "broodje" to "Broodje",
                " broodje" to " broodje",
                "" to "",
                "á" to "Á"
        )

        testCases.entries.forEach {
            assertEquals(it.value, it.key.firstUpper())
        }
    }

    @Test
    fun `String#toCamelCase`() {
        val testCases = mapOf(
                "hihi haha" to "hihiHaha",
                "some_snake_Case" to "someSnakeCase",
                "LoveForPascalCase" to "loveForPascalCase",
                "regularCamelCase" to "regularCamelCase",
                "HIHI HAHA" to "hihiHaha",
                "" to "",
                "A" to "a",
                "AndA_mixed Bag of Stuffs" to "andAMixedBagOfStuffs"
        )

        testCases.entries.forEach {
            assertEquals(it.value, it.key.toCamelCase())
        }
    }

    @Test
    fun `String#to_snake_case`() {
        val testCases = mapOf(
                "hihi haha" to "hihi_haha",
                "some_snake_Case" to "some_snake_case",
                "also_with_only_lower" to "also_with_only_lower",
                "LoveForPascalCase" to "love_for_pascal_case",
                "regularCamelCase" to "regular_camel_case",
                "HIHI HAHA" to "hihi_haha",
                "" to "",
                "A" to "a",
                "AndA_mixed Bag of Stuffs" to "and_a_mixed_bag_of_stuffs"
        )

        testCases.entries.forEach {
            assertEquals(it.value, it.key.to_snake_case())
        }
    }

    @Test
    fun `String#TO_SCREAMING_SNAKE_CASE`() {
        val testCases = mapOf(
                "hihi haha" to "HIHI_HAHA",
                "some_snake_Case" to "SOME_SNAKE_CASE",
                "also_with_only_lower" to "ALSO_WITH_ONLY_LOWER",
                "LoveForPascalCase" to "LOVE_FOR_PASCAL_CASE",
                "regularCamelCase" to "REGULAR_CAMEL_CASE",
                "HIHI HAHA" to "HIHI_HAHA",
                "" to "",
                "A" to "A",
                "AndA_mixed Bag of Stuffs" to "AND_A_MIXED_BAG_OF_STUFFS"
        )

        testCases.entries.forEach {
            assertEquals(it.value, it.key.TO_SCREAMING_SNAKE_CASE())
        }
    }

    @Test
    fun `String#ToPascalCase`() {
        val testCases = mapOf(
                "hihi haha" to "HihiHaha",
                "some_snake_Case" to "SomeSnakeCase",
                "LoveForPascalCase" to "LoveForPascalCase",
                "regularCamelCase" to "RegularCamelCase",
                "HIHI HAHA" to "HihiHaha",
                "" to "",
                "A" to "A",
                "AndA_mixed Bag of Stuffs" to "AndAMixedBagOfStuffs"
        )

        testCases.entries.forEach {
            assertEquals(it.value, it.key.ToPascalCase())
        }
    }

    @Test
    fun `String#Companion#random`() {
        val random = Random(0xCAFEBABE)

        for (i in 1..100 step 5) {
            val randomString = String.random(i, Charset.NUMBERS_LETTERS)

            // Check correct length
            assertEquals(i, randomString.length, "Random string length")

            // Check characters in charset.
            for (char in randomString) {
                assertTrue(char in Charset.NUMBERS_LETTERS, "Correct charset")
            }
        }

        // Check nonnegative length.
        assertFailsWith(IllegalArgumentException::class) {
            String.random(-1, random = random)
        }

        // Check empty charset.
        assertFailsWith(IllegalArgumentException::class) {
            String.random(10, Charset.EMPTY, random)
        }
    }

    @Test
    fun `String#shuffle`() {
        val random = Random(0xCAFEBABE)
        val string = String.random(16, Charset.LETTERS_NUMBERS_SPECIAL, random)
        val expected = string.toCharArray().toTypedArray().toMultiset()

        for (i in 1..5) {
            val shuffled = string.shuffle(random)
            val chars = shuffled.toCharArray().toTypedArray().toMultiset()
            assertEquals(expected, chars)
        }
    }
}