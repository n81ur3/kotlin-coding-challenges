package adventofcode2020

import org.junit.Assert.*
import org.junit.jupiter.api.Test

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
}