package adventofcode2020

class Day22CrabCombat

data class GameDeck(val gameNumber: Int, val deckPlayer1: List<Int>, val deckPlayer2: List<Int>)

data class Player(val name: String, val deck: MutableList<Int>) {

    fun playCard(): Int = deck.removeAt(0)

    fun addToDeck(winningCard: Int, losingCard: Int) {
        deck.add(winningCard)
        deck.add(losingCard)
    }

    companion object {
        fun fromLines(lines: List<String>): Player {
            val name = lines[0].dropLast(1).replace(" ", "")
            val deck = mutableListOf<Int>()
            lines.drop(1).forEach { deck.add(it.toInt()) }
            return Player(name, deck)
        }
    }
}

class Combat(val player1: Player, val player2: Player) {
    var gameCounter = 1
    val alreadyPlayedDecks = mutableSetOf<GameDeck>()

    fun play() {
        playRoundPart2(gameCounter, 1, player1.deck, player2.deck)
    }

    private fun printResult() {
        println("== Post-game result ==")
        printDecks(player1.deck, player2.deck)
    }

    fun playRoundPart2(
        gameNumber: Int,
        roundNumber: Int,
        deckPlayer1: List<Int>,
        deckPlayer2: List<Int>
    ): Int {
        val currentDeck = GameDeck(gameNumber, deckPlayer1, deckPlayer2)
        if (alreadyPlayedDecks.contains(currentDeck)) {
            print("Player 1 wins game $gameNumber because of similar game in previous round: $deckPlayer1) - ")
            printWinnerScore(deckPlayer1)
            return 1
        }
        if (deckPlayer2.size == 0) {
            print("Player 1 wins game $gameNumber: $deckPlayer1) - ")
            printWinnerScore(deckPlayer1)
            return 1
        } else if (deckPlayer1.size == 0) {
            print("Player 2 wins game $gameNumber: $deckPlayer2 - ")
            printWinnerScore(deckPlayer2)
            return 2
        }
        println("-- Round $roundNumber (Game $gameNumber) --")
        printDecks(deckPlayer1, deckPlayer2)

        val cardPlayer1 = deckPlayer1.first()
        val cardPlayer2 = deckPlayer2.first()

        val newDeckPlayer1 = deckPlayer1.drop(1)
        val newDeckPlayer2 = deckPlayer2.drop(1)

        println("Player 1 plays: $cardPlayer1")
        println("Player 2 plays: $cardPlayer2")

        alreadyPlayedDecks.add(currentDeck)
        var roundWinner = 0
        if (isEligbleSubGame(cardPlayer1, newDeckPlayer1, cardPlayer2, newDeckPlayer2)) {
            println("\n == Starting SubGame == \n")
            roundWinner = playRoundPart2(
                ++gameCounter,
                1,
                newDeckPlayer1.take(cardPlayer1),
                newDeckPlayer2.take(cardPlayer2),
            )
        }

        if (roundWinner == 1) {
            println("Player 1 wins the round\n")
            roundWinner = playRoundPart2(
                gameNumber,
                roundNumber + 1,
                newDeckPlayer1 + listOf(cardPlayer1, cardPlayer2),
                newDeckPlayer2
            )
        } else if (roundWinner == 2) {
            println("Player 2 wins the round\n")
            roundWinner = playRoundPart2(
                gameNumber,
                roundNumber + 1,
                newDeckPlayer1,
                newDeckPlayer2 + listOf(cardPlayer2, cardPlayer1)
            )
        } else if (cardPlayer1 > cardPlayer2) {
            println("Player 1 wins the round\n")
            roundWinner = playRoundPart2(
                gameNumber,
                roundNumber + 1,
                newDeckPlayer1 + listOf(cardPlayer1, cardPlayer2),
                newDeckPlayer2
            )
        } else {
            println("Player 2 wins the round\n")
            roundWinner = playRoundPart2(
                gameNumber,
                roundNumber + 1,
                newDeckPlayer1,
                newDeckPlayer2 + listOf(cardPlayer2, cardPlayer1)
            )
        }

        alreadyPlayedDecks.add(currentDeck)

        return roundWinner
    }

    private fun printWinnerScore(winnerDeck: List<Int>) {
        val finalScore = winnerDeck.reversed().mapIndexed { index, card -> (index + 1) * card }.sum()
        println("final score: $finalScore\n")
    }

    private fun isEligbleSubGame(
        cardPlayer1: Int,
        deckPlayer1: List<Int>,
        cardPlayer2: Int,
        deckPlayer2: List<Int>
    ): Boolean {
        return deckPlayer1.size >= cardPlayer1 && deckPlayer2.size >= cardPlayer2
    }

    fun playRoundPart1(roundNumber: Int) {
        println("-- Round $roundNumber --")
        printDecks(player1.deck, player2.deck)

        val cardPlayer1 = player1.playCard()
        val cardPlayer2 = player2.playCard()
        evaluateWinner(cardPlayer1, cardPlayer2)
    }

    private fun evaluateWinner(cardPlayer1: Int, cardPlayer2: Int): Int {
        println("Player 1 plays: $cardPlayer1")
        println("Player 2 plays: $cardPlayer2")

        return if (cardPlayer1 > cardPlayer2) {
            player1.addToDeck(cardPlayer1, cardPlayer2)
            println("Player 1 wins the round: $player1 - $player2\n")
            1
        } else {
            player2.addToDeck(cardPlayer2, cardPlayer1)
            println("Player 2 wins the round: $player1 - $player2\n")
            2
        }
    }

    private fun printDecks(deckPlayer1: List<Int>, deckPlayer2: List<Int>) {
        println("Player 1's deck: ${deckPlayer1}")
        println("Player 2's deck: ${deckPlayer2}")
    }

    fun gameOver(): Boolean = player1.deck.size == 0 || player2.deck.size == 0

    fun getWinner(): Player? {
        if (player1.deck.size == 0) {
            return player2
        } else if (player2.deck.size == 0) {
            return player1
        } else {
            return null
        }
    }

    fun winningPlayerScore(): Long {
        return when (getWinner()) {
            player1 -> calculateScore(player1)
            player2 -> calculateScore(player2)
            else -> -1
        }
    }

    private fun calculateScore(player: Player) =
        player.deck.reversed().mapIndexed { index, card -> card.toLong() * (index + 1).toLong() }.sum()
}