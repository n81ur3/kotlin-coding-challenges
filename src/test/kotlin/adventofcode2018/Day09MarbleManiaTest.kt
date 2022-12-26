package adventofcode2018

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import utils.ResourceLoader
import java.io.File

class Day09MarbleManiaTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day09_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val marbleMania = MarbleMania(9)

        marbleMania.play(25)

        assertEquals(5 to 32L, marbleMania.getWinner())
        println("Sample part 1 winner: ${marbleMania.getWinner()}")
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "10, 1618, 8317",
            "13, 7999, 146373",
            "17, 1104, 2764",
            "21, 6111, 54718",
            "30, 5807, 37305"
        )
    )
    fun runSamplesPart1(numberOfPlayers: Int, numberOfRounds: Int, winnerScore: Long) {
        val marbleMania = MarbleMania(numberOfPlayers)

        marbleMania.play(numberOfRounds)

        assertEquals(winnerScore, marbleMania.getWinner().second)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()[0]
        val numberOfPlayers = input.substringBefore(" players").toInt()
        val numberOfRounds = input.substringBefore(" points").substringAfter("worth ").toInt()
        val marbleMania = MarbleMania(numberOfPlayers)

        marbleMania.play(numberOfRounds)

        assertEquals(227 to 398242L, marbleMania.getWinner())
        println("Solution for AoC2018-Day09-Part01: ${marbleMania.getWinner().second}")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()[0]
        val numberOfPlayers = input.substringBefore(" players").toInt()
        val numberOfRounds = input.substringBefore(" points").substringAfter("worth ").toInt()
        val marbleMania = MarbleMania(numberOfPlayers)

        marbleMania.play(numberOfRounds * 100)

        assertEquals(3273842452, marbleMania.getWinner().second)
        println("Solution for AoC2018-Day09-Part02: ${marbleMania.getWinner().second}")
    }
}