package adventofcode2019

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04SecureContainerTest {

    @Test
    fun runSamplesPart1() {
        val passwordEvaluator = PasswordEvaluator()
        val candidates = listOf(111111, 223450, 123789)
        candidates.forEach { println("$it -> contains double: ${passwordEvaluator.containsDouble(it)}") }
        candidates.forEach { println("$it -> is sorted: ${passwordEvaluator.isSorted(it)}") }

        assertEquals(true, passwordEvaluator.isValidPassword(candidates[0]))
        assertEquals(false, passwordEvaluator.isValidPassword(candidates[1]))
        assertEquals(false, passwordEvaluator.isValidPassword(candidates[2]))
    }

    @Test
    fun solutionPart1() {
        val passwordEvaluator = PasswordEvaluator()

        val validPasswordsCount = (138241..674034).filter { passwordEvaluator.isValidPassword(it) }.count()

        assertEquals(1890, validPasswordsCount)
        println("Solution for AoC2019-Day04-Part01: $validPasswordsCount")
    }

    @Test
    fun solutionPart2() {
        val passwordEvaluator = PasswordEvaluator()

        val advancedPasswordCount = (138241..674034).filter { passwordEvaluator.isAdvancedPassword(it) }.count()

        assertEquals(1277, advancedPasswordCount)
        println("Solution for AoC2019-Day04-Part02: $advancedPasswordCount")
    }
}