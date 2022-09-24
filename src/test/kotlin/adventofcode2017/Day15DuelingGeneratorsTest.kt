package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15DuelingGeneratorsTest {

    @Test
    fun basicSequences() {
        val genNumbers = generateSequence(2) { num ->
            (num * 2) % 21
        }

        println("result: ${genNumbers.take(8).toList()}")
        println("last: ${genNumbers.take(40_000000).last()}")
    }

    @Test
    fun runSamplePart1() {
        val genA = Generator(65, 16807)
        val genB = Generator(8921, 48271)

        var equalsCount = 0
        repeat(5) {
            val valueA = genA.next()
            val valueB = genB.next()
            print(String.format("%12d  %12d  -  ", valueA, valueB))
            if (Judge.equals(valueA, valueB)) {
                println("Equal")
                equalsCount++
            } else {
                println("Not Equals")
            }
        }

        assertEquals(1, equalsCount)
    }

    @Test
    fun solution_part1() {
        val genA = Generator(783, 16807)
        val genB = Generator(325, 48271)

        var equalsCount = 0
        repeat(40_000_000) {
            if (Judge.equals(genA.next(), genB.next())) equalsCount++
        }

        assertEquals(650, equalsCount)
        println("Solution for day 15 part 1: $equalsCount")
    }
}