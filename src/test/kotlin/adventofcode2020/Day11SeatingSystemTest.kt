package adventofcode2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day11SeatingSystemTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day11_input.txt")
    }

    @Test
    fun sampleData_returnsExpectedOccupiedSeatCount() {
        val lines = listOf(
            "L.LL.LL.LL",
            "LLLLLLL.LL",
            "L.L.L..L..",
            "LLLL.LL.LL",
            "L.LL.LL.LL",
            "L.LLLLL.LL",
            "..L.L.....",
            "LLLLLLLLLL",
            "L.LLLLLL.L",
            "L.LLLLL.LL"
        )

        val seatPlan = SeatPlan.from(lines)
        var occupiedSeatsCount = seatPlan.numberOfOccupiedSeats
        var previousSeatsCount = Int.MAX_VALUE
        println("Number of occupied seats round 0: ${seatPlan.numberOfOccupiedSeats}")

        seatPlan.displayPlan()
        println()
        println()

        var roundCounter = 1
        while (previousSeatsCount != occupiedSeatsCount) {
            previousSeatsCount = occupiedSeatsCount
            seatPlan.resettle()
            occupiedSeatsCount = seatPlan.numberOfOccupiedSeats
            println("Round ${roundCounter++}")
            seatPlan.displayPlan()
            println()
            println()
        }

        println("After resettlement: ${seatPlan.numberOfOccupiedSeats}")

        assertEquals(37, seatPlan.numberOfOccupiedSeats)
    }

    @Test
    fun solutionDay11Part1() {
        val lines = file.readLines()
        val seatPlan = SeatPlan.from(lines)
        var occupiedSeatsCount = seatPlan.numberOfOccupiedSeats
        var previousSeatsCount = Int.MAX_VALUE

        while (previousSeatsCount != occupiedSeatsCount) {
            previousSeatsCount = occupiedSeatsCount
            seatPlan.resettle()
            occupiedSeatsCount = seatPlan.numberOfOccupiedSeats
            //seatPlan.displayPlan()
        }

        println("Solution Day 11 Part 01: ${seatPlan.numberOfOccupiedSeats}")
    }

    @Test
    fun solutionDay11Part2() {
        val lines = file.readLines()
        val seatPlan = SeatPlan.from(lines)
        var occupiedSeatsCount = seatPlan.numberOfOccupiedSeats
        var previousSeatsCount = Int.MAX_VALUE

        while (previousSeatsCount != occupiedSeatsCount) {
            previousSeatsCount = occupiedSeatsCount
            seatPlan.resettleWithSight()
            occupiedSeatsCount = seatPlan.numberOfOccupiedSeats
        }

        println("Solution Day 11 Part 02: ${seatPlan.numberOfOccupiedSeats}")
    }

    @Test
    fun checkSightCount() {
        val lines1 = listOf(
            ".......#.",
            "...#.....",
            ".#.......",
            ".........",
            "..#L....#",
            "....#....",
            ".........",
            "#........",
            "...#.....",
        )
        val seatPlan1 = SeatPlan.from(lines1)
        seatPlan1.displayPlan()
        assertEquals(8, seatPlan1.getSightCountAtPosition(4 to 3))

        val lines2 = listOf(
            ".............",
            ".L.L.#.#.#.#.",
            "............."
        )
        val seatPlan2 = SeatPlan.from(lines2)
        seatPlan2.displayPlan()
        assertEquals(0, seatPlan2.getSightCountAtPosition(1 to 1))
        assertEquals(0, seatPlan2.getSightCountAtPosition(1 to 2))
        assertEquals(1, seatPlan2.getSightCountAtPosition(1 to 3))

        val lines3 = listOf(
            ".##.##.",
            "#.#.#.#",
            "##...##",
            "...L...",
            "##...##",
            "#.#.#.#",
            ".##.##."
        )
        val seatPlan3 = SeatPlan.from(lines3)
        assertEquals(0, seatPlan3.getSightCountAtPosition(3 to 3))
        assertEquals(4, seatPlan3.getSightCountAtPosition(0 to 3))
        assertEquals(6, seatPlan3.getSightCountAtPosition(2 to 2))
    }
}