package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17SpinlockTest {

    @Test
    fun runSamplePart1() {
        val spinlock = Spinlock(3)

        spinlock.step(2017)

        val result = spinlock.getResult()
        assertEquals(638, result)
    }

    @Test
    fun solution_part1() {
        val spinlock = Spinlock(343)

        spinlock.step(2017)
        val result = spinlock.getResult()

        assertEquals(1914, result)
        println("Solution for day 17 part 1: $result")
    }
}