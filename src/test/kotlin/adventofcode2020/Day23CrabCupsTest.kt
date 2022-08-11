package adventofcode2020

import org.junit.jupiter.api.Test

class Day23CrabCupsTest {

    @Test
    fun playSampleGame() {
        val crabCupGame = Day23CrabCups(mutableListOf(3, 8, 9, 1, 2, 5, 4, 6, 7))
        repeat(100) {
            crabCupGame.move()
        }
        crabCupGame.getResult()
    }

    @Test
    fun solutionDay23Part01() {
        val crabCupGame = Day23CrabCups(mutableListOf(9, 5, 2, 3, 1, 6, 4, 8, 7))
        repeat(100) {
            crabCupGame.move()
        }
        crabCupGame.getResult()
    }

    @Test
    fun solutionDay23Part02() {
        val cups = Day23CrabCups.Cups("952316487", 1_000_000)
        val firstCup = cups.playRounds(10_000_000)
        val nextTwo = firstCup.nextAsList(2).map { it.value.toLong() }
        val result = nextTwo.reduce { acc, next -> acc * next }
        println("Result: $result")
    }
}