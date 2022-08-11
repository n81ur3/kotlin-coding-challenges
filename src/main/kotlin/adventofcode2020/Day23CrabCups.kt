package adventofcode2020

import java.util.*

class Day23CrabCups(val cups: MutableList<Int>) {

    companion object {
        const val NUMBER_OF_CUPS = 1000000
    }

    init {
        (10..1000000).forEach { cups.add(it) }
    }

    var currentCupIndex = 0
    var currentCupValue = cups.first()
    var moveCounter = 0
    var pickedUp = listOf<Int>()

    fun move() {
        moveCounter++
        pickedUp = pickUpCups()
//        printMove()
        val destination = getDestinationCup()
        cups.removeIf { it in pickedUp }
//        println("destination: $destination\n")
        insertPickedUpAtDestination(pickedUp, cups.indexOf(destination) + 1)
        currentCupIndex = nextCupIndex()
        currentCupValue = cups[currentCupIndex]

        if (moveCounter % 1000 == 0) {
            println("moveCounter: $moveCounter")
        }
    }

    fun getResult() {
        println("-- final --")
        println("cups: $cups")
    }

    private fun pickUpCups(): List<Int> {
        val firstIndex = (currentCupIndex + 1) % (NUMBER_OF_CUPS)
        val secondIndex = (currentCupIndex + 2) % (NUMBER_OF_CUPS)
        val thirdIndex = (currentCupIndex + 3) % (NUMBER_OF_CUPS)

        return listOf(cups[firstIndex], cups[secondIndex], cups[thirdIndex])
    }

    private fun nextCupIndex(): Int {
        return (cups.indexOf(currentCupValue) + 1) % NUMBER_OF_CUPS
    }

    private fun getDestinationCup(): Int {
        var destinationValue = (cups[currentCupIndex] - 1)

        while (destinationValue in pickedUp || destinationValue == 0) {
            destinationValue--
            if (destinationValue < 1) {
                destinationValue = NUMBER_OF_CUPS
            }
        }

        return destinationValue
    }

    private fun insertPickedUpAtDestination(pickedUp: List<Int>, destination: Int) {
        if (destination <= NUMBER_OF_CUPS - 3) {
            cups.addAll(destination, pickedUp)
        } else if (destination == NUMBER_OF_CUPS - 2) {
            cups.addAll(destination, pickedUp.subList(0, 2))
            cups.add(0, pickedUp[2])
        } else if (destination == NUMBER_OF_CUPS - 1) {
            cups.add(destination, pickedUp[0])
            cups.addAll(0, pickedUp.subList(1, 3))
        }
    }

    private fun printMove() {
        println("-- move $moveCounter --")
        printCupsWithCurrentPosition()
        println("pick up: $pickedUp")
    }

    private fun printCupsWithCurrentPosition() {
        val sb = StringBuilder("cups:")
        for ((index, cup) in cups.withIndex()) {
            if (index == currentCupIndex) {
                sb.append(" ($cup)")
            } else {
                sb.append(" $cup")
            }
        }
        println(sb.toString())
    }

    class Cup(val value: Int) {
        lateinit var next: Cup

        fun nextAsList(n: Int): List<Cup> =
            (1..n).runningFold(this) { cur, _ -> cur.next }.drop(1)

        override fun toString(): String = buildString {
            var current = this@Cup.next
            while (current != this@Cup) {
                append(current.value.toString())
                current = current.next
            }
        }
    }

    class Cups(order: String, numberOfCups: Int = order.length) {
        val cups: List<Cup> = List(numberOfCups+1) { Cup(it) }
        var currentCup: Cup = cups[order.first().asInt()]

        init {
            val cupIdsInOrder = order.map { it.asInt() } + (order.length + 1 .. numberOfCups)
            cupIdsInOrder
                .map { cups[it] }
                .fold(cups[order.last().asInt()]) { previous, cup ->
                    cup.also { previous.next = cup }
                }
            cups[cupIdsInOrder.last()].next = cups[cupIdsInOrder.first()]
        }

        private fun playRound() {
            val next3: List<Cup> = currentCup.nextAsList(3)
            val destination = calculateDestination(next3.map { it.value }.toSet())
            moveCups(next3, destination)
            currentCup = currentCup.next
        }

        private fun calculateDestination(exempt: Set<Int>): Cup {
            var dest = currentCup.value - 1
            while (dest in exempt || dest == 0) {
                dest = if (dest == 0) cups.size - 1 else dest - 1
            }
            return cups[dest]
        }

        private fun moveCups(cupsToInsert: List<Cup>, destination: Cup) {
            val prevDest = destination.next
            currentCup.next = cupsToInsert.last().next
            destination.next = cupsToInsert.first()
            cupsToInsert.last().next = prevDest
        }

        fun playRounds(numRounds: Int): Cup {
            repeat(numRounds) {
                playRound()
            }
            return cups[1]
        }
    }
}

fun Char.asInt(): Int = this.toString().toInt()
