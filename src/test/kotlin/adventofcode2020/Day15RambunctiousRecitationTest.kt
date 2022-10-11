package adventofcode2020

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day15RambunctiousRecitationTest {

    @Test
    fun appendTenRecitations() {
        val rambunctions = mutableListOf(0, 3, 6)
        repeat(7) {
            rambunctions.appendRecitation()
        }

        println(rambunctions)
    }

    @Test
    fun appendSamples() {
        val rambunctions = listOf(
            mutableListOf(1, 3, 2),
            mutableListOf(2, 1, 3),
            mutableListOf(1, 2, 3),
            mutableListOf(2, 3, 1),
            mutableListOf(3, 2, 1),
            mutableListOf(3, 1, 2)
        )
        val expectedResults = listOf(1, 10, 27, 78, 438, 1836)
        for ((index, list) in rambunctions.mapIndexed { index, ints -> index to ints }) {

            repeat(2017) {
                list.appendRecitation()
            }
            assertEquals(expectedResults[index], list.last())
        }
    }

    @Test
    fun solutionDay15part1() {
        val rambunctions = mutableListOf(7, 14, 0, 17, 11, 1, 2)
        repeat(2020 - rambunctions.size) {
            rambunctions.appendRecitation()
        }
        println("Solution day15 part 1: Turn 2020 result = ${rambunctions.last()}")
    }

    @Test
    fun solutionDay15part2() {
        val rambunctions = mutableListOf(7, 14, 0, 17, 11, 1, 2)
        val steps = 30000000
        val result = calculateRecitationAtStep(rambunctions, steps)
        println("Solution day15 part 2: Turn 30000000 result = $result")
    }

    @Test
    fun appendTenRecitationsPart2() {
        //val rambunctions = listOf(0, 3, 6)
        val rambunctions = listOf(1, 3, 2)
        val result = calculateRecitationAtStep(rambunctions, 2017)

        println("Result after seven steps: $result")
    }

    @Test
    fun appendSamplesPart2() {
        val rambunctions = listOf(
            mutableListOf(1, 3, 2),
            mutableListOf(2, 1, 3),
            mutableListOf(1, 2, 3),
            mutableListOf(2, 3, 1),
            mutableListOf(3, 2, 1),
            mutableListOf(3, 1, 2)
        )
        val expectedResults = listOf(1, 10, 27, 78, 438, 1836)
        for ((index, list) in rambunctions.mapIndexed { index, ints -> index to ints }) {
            val result = calculateRecitationAtStep(list, 2020)
            assertEquals(expectedResults[index], result)
        }
    }

    private fun calculateRecitationAtStep(initList: List<Int>, steps: Int): Int {
        val cache = mutableMapOf<Int, Int>()
        initList.dropLast(1).forEachIndexed { index, value -> cache.put(value, index) }
        var lastIndex = -1
        var runningIndex = initList.size
        var currentValue = initList.last()
        repeat(steps - initList.size) {
            lastIndex = cache[currentValue] ?: -1
            cache.put(currentValue, runningIndex - 1)
            currentValue = if (lastIndex != -1) runningIndex - lastIndex - 1 else 0
            runningIndex++
        }

        return currentValue
    }
}