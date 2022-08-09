package adventofcode2020

import org.junit.Assert.*
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
}