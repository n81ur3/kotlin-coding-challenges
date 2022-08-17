package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day10AdapterArrayTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day10_input.txt")
    }

    @Test
    fun solutionDay10_part1() {
        val lines = file.readLines().map { it.toInt() }.sorted()
        val diffMap = mutableMapOf<Int, Int>()
        (1..3).forEach { diffMap.put(it, 0) }

        //lines.forEach { println(it) }

        lines.fold(lines[0]) { acc, current ->
            run {
                val diff = current - acc
                val currentCount = diffMap.get(diff) ?: 0
                diffMap.put(diff, currentCount + 1)
                current
            }
        }

        println("resulting map: $diffMap")

        println("Solution day 10 part 1: ${(diffMap.get(1)!! + 1) * (diffMap.get(3)!! + 1)}")
    }

    @Test
    fun solutionDay10Part2() {
        val lines = file.readLines().map { it.toInt() }.sorted().toMutableList()

        val waysPerAdapter = mutableMapOf(0 to 1L)

        lines.forEach {index ->
            waysPerAdapter[index] = waysPerAdapter.getOrDefault(index - 3, 0) + waysPerAdapter.getOrDefault(index - 2, 0) +
                    waysPerAdapter.getOrDefault(index - 1, 0)
        }

        println("Solution Day 10 Part2: ${waysPerAdapter[waysPerAdapter.toSortedMap().lastKey()]}")
    }

    @Test
    fun two_power_three_returnsEight() {
        println(2L pow 10)
    }
}


private infix fun Long.pow(exponent: Int): Long {
    var result = this
    repeat(exponent - 1) {
        result += result
    }
    return result
}

