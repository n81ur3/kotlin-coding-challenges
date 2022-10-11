package adventofcode2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day05BinaryBoardingTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day05_input.txt")
    }

    @Test
    fun calculateSeatIdTest() {
        val encodedString = "FBFBBFFRLR"
        val boardingPass = BinaryBoardingPass(encodedString)
        assertEquals(357, boardingPass.seatId)

        assertEquals(567, BinaryBoardingPass("BFFFBBFRRR").seatId)
        assertEquals(119, BinaryBoardingPass("FFFBBBFRRR").seatId)
        assertEquals(820, BinaryBoardingPass("BBFFBBFRLL").seatId)
    }

    @Test
    fun solution05_1() {
        val lines = file.readLines()
        val highestCode = lines.maxByOrNull { BinaryBoardingPass(it).seatId }
        println("Solution for day 05 part 1: ${BinaryBoardingPass(highestCode!!).seatId}")
    }

    @Test
    fun solution05_2() {
        val result = SeatCalculator.calculateFreeSeat(file.readLines())
        println("Solution for day 05 part 2: $result")
    }
}