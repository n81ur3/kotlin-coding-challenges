package adventofcode2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

internal class Day04PasswordProcessingTest {

    lateinit private var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day04_input.txt")
    }

    @Test
    fun parseStringTest() {
        val entry = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
             byr:1937 iyr:2017 cid:147 hgt:183cm
        """.trimIndent()

        val concated = entry.replace("\n", "")

        println("String: $concated")

        val passport = NorthPolePassport()
        for (token in concated.split(" ")) {
            val key = token.split(":")[0]
            val value = token.split(":")[1]
            //println("key=$key - value=$value")
            passport.addToken(key, value)
        }
        println("Password Map: ${passport.tokens}")
        assertTrue(passport.containsAllRequiredTokens())
    }

    @Test
    fun solution04_1() {
        var numberOfValidPassports = 0
        val currentEntry: StringBuilder = StringBuilder()

        val lines = file.readLines()

        for (line in lines) {
            currentEntry.append(" ")
            currentEntry.append(line)
            if (line.isEmpty()) {
                if (checkPassportContainsAllTokens(currentEntry.toString())) {
                    numberOfValidPassports++
                }
                currentEntry.clear()
            }
        }

        if (checkPassportContainsAllTokens(currentEntry.toString())) {
            numberOfValidPassports++
        }

        println("Solution 04_1: number of valid entries: $numberOfValidPassports")
    }

    @Test
    fun solution04_2() {
        var numberOfValidPassports = 0
        val currentEntry: StringBuilder = StringBuilder()

        val lines = file.readLines()

        for (line in lines) {
            currentEntry.append(" ")
            currentEntry.append(line)
            if (line.isEmpty()) {
                if (checkPassportValid(currentEntry.toString())) {
                    numberOfValidPassports++
                }
                currentEntry.clear()
            }
        }

        if (checkPassportValid(currentEntry.toString())) {
            numberOfValidPassports++
        }

        println("Solution 04_2: number of valid entries: $numberOfValidPassports")
    }

    @Test
    fun regexTest() {
        val value = "#ab12af"
        val regex = Regex("#[0-9a-f]{6}")
        assertTrue(regex.matches(value))
    }
}

private fun checkPassportContainsAllTokens(entry: String): Boolean {
    val passport = NorthPolePassport()
    for (token in entry.trim().split(" ")) {
        val key = token.split(":")[0]
        val value = token.split(":")[1]
        passport.addToken(key, value)
    }
    return passport.containsAllRequiredTokens()
}

private fun checkPassportValid(entry: String): Boolean {
    val passport = NorthPolePassport()
    for (token in entry.trim().split(" ")) {
        val key = token.split(":")[0]
        val value = token.split(":")[1]
        passport.addToken(key, value)
    }
    return passport.isValid()
}
