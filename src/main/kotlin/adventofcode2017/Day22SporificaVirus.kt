package adventofcode2017

class Day22SporificaVirus

enum class CarrierDirection(val signifier: Char) {
    UP('^'), RIGHT('>'), DOWN('⌄'), LEFT('<')
}

data class Carrier(var posX: Int = 0, var posY: Int = 0, var direction: CarrierDirection = CarrierDirection.UP) {
    val pos: Pair<Int, Int>
        get() = posX to posY

    fun turnRight() {
        direction = when (direction) {
            CarrierDirection.UP -> CarrierDirection.RIGHT
            CarrierDirection.RIGHT -> CarrierDirection.DOWN
            CarrierDirection.DOWN -> CarrierDirection.LEFT
            CarrierDirection.LEFT -> CarrierDirection.UP
        }
    }

    fun turnLeft() {
        direction = when(direction) {
            CarrierDirection.UP -> CarrierDirection.LEFT
            CarrierDirection.LEFT -> CarrierDirection.DOWN
            CarrierDirection.DOWN -> CarrierDirection.RIGHT
            CarrierDirection.RIGHT -> CarrierDirection.UP
        }
    }

    fun move() {
        when (direction) {
            CarrierDirection.UP -> posY--
            CarrierDirection.RIGHT -> posX++
            CarrierDirection.DOWN -> posY++
            CarrierDirection.LEFT -> posX--
        }
    }
}

class Cluster(initialState: List<String>, initialSize: Int = initialState[0].length) {
    val infectedNodes = mutableListOf<Pair<Int, Int>>()
    val minInfectedX: Int
        get() = infectedNodes.minBy { it.first }.first
    val maxInfectedX: Int
        get() = infectedNodes.maxBy { it.first }.first
    val minInfectedY: Int
        get() = infectedNodes.minBy { it.second }.second
    val maxInfectedY: Int
        get() = infectedNodes.maxBy { it.second }.second
    val carrier = Carrier()
    var infectionCount = 0

    init {
        (0 until initialSize).forEach { x ->
            (0 until initialSize).forEach { y ->
                if (initialState[y][x] == '#') infectedNodes.add ( (x - (initialSize / 2)) to (y - (initialSize / 2)))
            }
        }
    }

    fun burst() {
        if (carrier.pos in infectedNodes) {
            infectedNodes.remove(carrier.pos)
            carrier.turnRight()
        } else {
            infectedNodes.add(carrier.pos)
            carrier.turnLeft()
            infectionCount++
        }
        carrier.move()
    }

    fun printCluster() {
        (minInfectedY..maxInfectedY).forEach { y ->
            (minInfectedX..maxInfectedX).forEach { x ->
                if (x to y == carrier.pos) {
                    print(carrier.direction.signifier)
                } else {
                    print(if (x to y in infectedNodes) "#" else ".")
                }
            }
            println()
        }
    }
}