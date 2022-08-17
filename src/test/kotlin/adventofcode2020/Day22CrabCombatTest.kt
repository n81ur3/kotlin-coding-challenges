package adventofcode2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day22CrabCombatTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day22_input.txt")
    }

    @Test
    fun playSampleGame() {
        val inputPlayer1 = listOf(
            "Player 1:",
            "9",
            "2",
            "6",
            "3",
            "1"
        )

        val inputPlayer2 = listOf(
            "Player 2:",
            "5",
            "8",
            "4",
            "7",
            "10"
        )

        val player1 = Player.fromLines(inputPlayer1)
        val player2 = Player.fromLines(inputPlayer2)

        val combat = Combat(player1, player2)
        combat.play()
    }

    @Test
    fun sampleGame_ResultsIn_Player2_Score_306_Part1() {
        val inputPlayer1 = listOf(
            "Player 1:",
            "9",
            "2",
            "6",
            "3",
            "1"
        )

        val inputPlayer2 = listOf(
            "Player 2:",
            "5",
            "8",
            "4",
            "7",
            "10"
        )

        val player1 = Player.fromLines(inputPlayer1)
        val player2 = Player.fromLines(inputPlayer2)

        val combat = Combat(player1, player2)
        combat.play()

        assertEquals(306, combat.winningPlayerScore())
    }


    @Test
    fun solutionDay22Part01() {
        val lines = file.readLines()
        val player1 = Player.fromLines(lines.subList(0, 26))
        val player2 = Player.fromLines(lines.subList(27, 53))

        val combat = Combat(player1, player2)
        combat.play()

        println("Final score of winning player: ${combat.winningPlayerScore()}")
    }

    @Test
    fun infiniteLoopGuard() {
        val inputPlayer1 = listOf(
            "Player 1:",
            "43",
            "19"
        )

        val inputPlayer2 = listOf(
            "Player 2:",
            "2",
            "29",
            "14"
        )

        val player1 = Player.fromLines(inputPlayer1)
        val player2 = Player.fromLines(inputPlayer2)

        val combat = Combat(player1, player2)
        combat.play()

    }

    @Test
    fun solutionDay22Part02() {
        val lines = file.readLines()
        val player1 = Player.fromLines(lines.subList(0, 26))
        val player2 = Player.fromLines(lines.subList(27, 53))

        val combat = Combat(player1, player2)
        combat.play()
    }

}