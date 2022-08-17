package adventofcode2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day02PasswordPhilosophyTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day02_input.txt")
    }

    @Test
    fun testDataClass() {
        val passwordEntry = PasswordEntry("6-7 q: tqqqqvqqq")
        println(passwordEntry)
    }

    @Test
    fun checkValidPassword() {
        var passwordEntry = PasswordEntry("5-6 n: nnnnnn")
        Assertions.assertTrue(passwordEntry.isValid())

        passwordEntry = PasswordEntry("1-7 d: ffdfffffff")
        Assertions.assertTrue(passwordEntry.isValid())
    }

    @Test
    fun checkInvalidPassword() {
        var passwordEntry = PasswordEntry("3-4 n: nnnnnn")
        Assertions.assertFalse(passwordEntry.isValid())

        passwordEntry = PasswordEntry("5-7 d: ffdfddd")
        Assertions.assertFalse(passwordEntry.isValid())
    }

    @Test
    fun solution02_1() {
        val lines = file.readLines().map { PasswordEntry(it) }
        val result = lines.filter { entry -> entry.isValid() }.count()
        println("Number of valid entries: $result")
    }

    @Test
    fun solution02_2() {
        val lines = file.readLines().map { PasswordEntry(it) }
        val result = lines.filter { entry -> entry.isValidPartTwo() }.count()
        println("Number of valid entries for part two: $result")
    }
}