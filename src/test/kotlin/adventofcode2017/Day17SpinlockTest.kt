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
    fun runSamplePart2() {
        val spinlock = Spinlock(3)

        spinlock.spin(10)

    }

    @Test
    fun solution_part1() {
        val spinlock = Spinlock(343)

        spinlock.step(2017)
        val result = spinlock.getResult()

        assertEquals(1914, result)
        println("Solution for day 17 part 1: $result")
    }

    @Test
    fun solution_part2() {
        val spinlock = Spinlock(343)

        spinlock.spin(50_000_000)
        val result = spinlock.target

        assertEquals(41797835, result)
        println("Solution for day 17 part 2: $result")
    }
}