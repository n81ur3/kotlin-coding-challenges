package adventofcode2020

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day19MonsterMessageTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day19_input.txt")
    }

    @Test
    fun buildRulesFromInputFile() {
        val lines = file.readLines()

        val inputRules = lines.takeWhile { it.isNotBlank() }

        val rulesChecker = RulesChecker()
        rulesChecker.buildRulesFromLines(inputRules)

        println(rulesChecker.rules.get(0))
        println(rulesChecker.rules.get(5))
        println(rulesChecker.rules.get(72))
    }

    @Test
    fun testSampleInputs() {
        val lines = listOf(
            "0: 4 1 5",
            "1: 2 3 | 3 2",
            "2: 4 4 | 5 5",
            "3: 4 5 | 5 4",
            "4: a",
            "5: b"
        )

        val matches = listOf("ababbb", "abbbab")
        val notMatches = listOf("bababa", "aaabbb")

        val rulesChecker = RulesChecker()
        rulesChecker.buildRulesFromLines(lines)
        println("Rule 0: ${rulesChecker.rules[0]}")

        matches.forEach { assertTrue { rulesChecker.rules[0]?.matches(it) ?: false } }
        notMatches.forEach { assertFalse { rulesChecker.rules[0]?.matches(it) ?: false } }
    }

    @Test
    fun solutionDay19Part01() {
        val lines = file.readLines()
        val ruleLines = lines.takeWhile { it.isNotBlank() }
        val rulesChecker = RulesChecker()
        rulesChecker.buildRulesFromLines(ruleLines)

        val messagesToCheck = lines.dropWhile { it.isBlank() }
        println("Rule 0: ${rulesChecker.rules[0]}")
        val result = messagesToCheck.filter { rulesChecker.evaluateMessage(it) }.count()
        println("Solution day19 part 1: Number of matching Messages = $result")
    }

    @Test
    fun solutionDay19Part02() {
        val lines = file.readLines()
        val ruleLines = lines.takeWhile { it.isNotBlank() }.toMutableList()
//        ruleLines[87] = "8: 42 | 42 8"
//        ruleLines[76] = "11: 42 31 | 42 11 31"
        ruleLines[87] = "8: 42 | 42"
        ruleLines[76] = "11: 42 31 | 42 31"
        val rulesChecker = RulesChecker()
        rulesChecker.buildRulesFromLines(ruleLines)

        val messagesToCheck = lines.dropWhile { it.isBlank() }
        println("Rule 0: ${rulesChecker.rules[0]}")
        val result = messagesToCheck.filter { rulesChecker.evaluateMessage(it) }.count()
        println("Solution day19 part 2: Number of matching Messages = $result")
    }
}