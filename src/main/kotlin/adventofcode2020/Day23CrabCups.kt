package adventofcode2020

class Day23CrabCups(val cups: MutableList<Int>) {

    companion object {
        const val NUMBER_OF_CUPS = 9
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
        var destinationValue = cups[currentCupIndex] - 1

        while (destinationValue in pickedUp || destinationValue == 0) {
            destinationValue--
            if (destinationValue < 1) {
                destinationValue = NUMBER_OF_CUPS
            }
        }

        return destinationValue
    }

    private fun insertPickedUpAtDestination(pickedUp: List<Int>, destination: Int) {
        if (destination <= 6) {
            cups.addAll(destination, pickedUp)
        } else if (destination == 7) {
            cups.addAll(destination, pickedUp.subList(0, 2))
            cups.add(0, pickedUp[2])
        } else if (destination == 8) {
            cups.add(destination, pickedUp[0])
            cups.addAll(0, pickedUp.subList(1, 3))
        }
    }

    private fun printMove() {
        println("-- move $moveCounter --")
        printCupsWithCurrentPosition()
        println("pick up: ${pickedUp}")
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
}
