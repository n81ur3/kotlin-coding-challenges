package adventofcode2018

class Day09MarbleMania

class MarbleMania(val numberOfPlayers: Int) {
    val marbles = mutableListOf<Int>(0)
    var currentMarble = 0
    var currentIndex = 0
    var currentElf = 0
    val elfScores =mutableMapOf<Int, Int>()

    init {
        repeat(numberOfPlayers) { elfScores[it] = 0 }
    }

    private fun takeTurn() {
        currentMarble++
        currentElf = Math.floorMod(currentElf + 1, numberOfPlayers)
        if (Math.floorMod(currentMarble, 23) == 0) {
            val removeIndex = Math.floorMod((currentIndex - 7), marbles.size)
            val marbleRemoved = marbles.removeAt(removeIndex)
            elfScores[currentElf] = elfScores[currentElf]!! + currentMarble + marbleRemoved
            currentIndex = removeIndex
        } else {
            val pre = Math.floorMod((currentIndex + 1), marbles.size)
            marbles.add(pre + 1, currentMarble)
            currentIndex = pre + 1
        }
    }

    fun play(numberOfTurns: Int) {
        repeat(numberOfTurns) { takeTurn() }
    }

    fun getWinner(): Pair<Int, Int> {
        val winner = elfScores.maxBy { it.value }.toPair()
        return (winner.first) to winner.second
    }
}