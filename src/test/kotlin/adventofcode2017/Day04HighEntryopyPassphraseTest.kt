package adventofcode2017

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.ResourceLoader
import java.io.File

class Day04HighEntryopyPassphraseTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day04_input.txt")
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "aa bb cc dd ee: true",
            "aa bb cc dd aa: false",
            "aa bb cc dd aaa: true",
            "aa bb aa dd bb: false",
            "aa baab bb vv aab: true"
        ),
    delimiter = ':'
    )
    fun checkSamplesValidity1(input: String, validity: Boolean) {
        val passphrase = Passphrase(input)

        assertEquals(validity, passphrase.isValid())
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "abcde fghij: true",
            "abcde xyz ecdab: false",
            "a ab abc abd abf abj: true",
            "iiii oiii ooii oooi oooo: true",
            "oiii ioii iioi iiio: false"
        ),
        delimiter = ':'
    )
    fun checkSamplesSecurity(input: String, isSecure: Boolean) {
        val passphrase = Passphrase(input)

        assertEquals(isSecure, passphrase.isSecure())
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val passphrases = input.map { Passphrase(it) }
        val solution = passphrases.filter { it.isValid() }.size

        assertEquals(451, solution)
        println("Solution AoC2017-Day04-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val passphrases = input.map { Passphrase(it) }
        val solution = passphrases.filter { it.isSecure() }.size

        println("Solution AoC2017-Day04-Part02: $solution")
    }
}

