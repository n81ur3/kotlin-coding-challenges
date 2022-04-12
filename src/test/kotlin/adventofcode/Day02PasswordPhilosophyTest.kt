package adventofcode

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.File

class Day02PasswordPhilosophyTest {

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
    fun solution03() {
        val file = File("src/test/kotlin/resources/day02_input.txt")
        val lines = file.readLines().map { PasswordEntry(it) }
        val result = lines.filter { entry -> entry.isValid() }.count()
        println("Number of valid entries: $result")
    }
}