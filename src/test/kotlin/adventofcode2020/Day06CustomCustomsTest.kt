package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day06CustomCustomsTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day06_input.txt")
    }

    @Test
    fun createCustomCustomsTest() {
        val input = "abcxabcyabcz"
        val cc = CustomCustoms(input)
        println("Number of answers: ${cc.distinctAnswers.size}")
    }

    @Test
    fun createCustomCustomsGroupTest() {
        val input = listOf("abc", "badc", "efacds")

        val group = CustomCustomsGroup(input)
        println("Result group: ${group.commonAnswers()}")
    }

    @Test
    fun solution06_1() {
        val lines = file.readLines()
        val counts = mutableListOf<Int>()
        val sb = StringBuilder()

        lines.forEach { line ->
            sb.append(line)
            if (line.isBlank()) {
                val cc = CustomCustoms(sb.toString())
                counts.add(cc.answersCount)
                sb.clear()
            }
        }

        counts.add(CustomCustoms(sb.toString()).answersCount)

        println("Solution for day06 part 1: ${counts.sum()}")
    }

    @Test
    fun solution06_2() {
        val lines = file.readLines()
        val sb = StringBuilder()

        val groups = mutableListOf<CustomCustomsGroup>()
        lines.forEach { line ->
            sb.append(line)
            sb.append(" ")
            if (line.isBlank()) {
                groups.add(CustomCustomsGroup(sb.toString().split(" ")))
                sb.clear()
            }
        }

        groups.add(CustomCustomsGroup(sb.toString().split(" ")))

        val result = groups.sumBy { it.commonAnswers().size }

        println("Solution for day06 part 2: $result ")
    }
}