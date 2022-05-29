package adventofcode2020

import adventofcode2020.ShipDirection.*
import adventofcode2020.WaypointDirection.*
import java.lang.Math.abs

class Day12RainRisk

enum class ShipDirection {
    FacingNorth,
    FacingSouth,
    FacingEast,
    FacingWest
}

enum class WaypointDirection {
    NorthEast,
    SouthEast,
    SouthWest,
    NorthWest,
    North,
    South,
    East,
    West
}

infix fun Int.mod(modulus: Int) = Math.floorMod(this, modulus)

class Waypoint(initEastWest: Int = 10, initNorthSouth: Int = 1, initDirection: WaypointDirection = NorthEast) {
    var eastWest = initEastWest
    var northSouth = initNorthSouth
    private val directions = listOf(NorthEast, SouthEast, SouthWest, NorthWest)
    private var direction: Int

    init {
        this.direction = directions.indexOf(initDirection)
    }

    fun rotate(degree: Int) {
        val indexShift = degree / 90
        val newDirection = (direction + indexShift) mod 4

        if (indexShift < 0) {
            rotateLeft(indexShift * -1)
        } else {
            rotateRight(indexShift)
        }
        direction = newDirection
    }

    private fun rotateLeft(steps: Int) {
        repeat(steps) {
            swapCoordinates()
            eastWest *= -1
        }
    }

    private fun rotateRight(steps: Int) {
        repeat(steps) {
            swapCoordinates()
            northSouth *= -1
        }
    }

    private fun adjustCoordinates(swapCoordinates: Boolean, invertNorthSouth: Boolean, invertEastWest: Boolean) {
        if (swapCoordinates) swapCoordinates()
        if (invertNorthSouth) northSouth *= -1
        if (invertEastWest) eastWest *= -1
    }

    fun move(direction: WaypointDirection, units: Int) {
        when (direction) {
            North -> northSouth += units
            South -> northSouth -= units
            East -> eastWest += units
            West -> eastWest -= units
            else -> throw IllegalArgumentException("Direction must be North, South, East or West")
        }
    }

    val position: Pair<Int, Int>
        get() = eastWest to northSouth

    private fun swapCoordinates() {
        eastWest = northSouth.also { northSouth = eastWest }
    }

    fun currentDirection() = directions[direction]

    override fun toString(): String {
        return "Waypoint(eastWest=$eastWest, northSouth=$northSouth, direction=${directions[direction]})"
    }
}

class Ship(var eastWestPos: Int = 0, var northSouthPos: Int = 0, var direction: ShipDirection = FacingEast) {

    fun executeInstruction(instruction: String) {
        val type = instruction.first()
        val value = instruction.substring(1)
        when (type) {
            'L', 'R' -> turnShip(value.toInt(), type)
            else -> moveShip(type, value.toInt())
        }
    }

    private fun turnShip(value: Int, rotation: Char) {
        when (direction) {
            FacingEast -> {
                when (rotation) {
                    'L' -> when (value) {
                        90 -> direction = FacingNorth
                        270 -> direction = FacingSouth
                        else -> direction = FacingWest
                    }
                    else -> when (value) {
                        90 -> direction = FacingSouth
                        270 -> direction = FacingNorth
                        else -> direction = FacingWest
                    }
                }
            }
            FacingWest -> {
                when (rotation) {
                    'L' -> when (value) {
                        90 -> direction = FacingSouth
                        270 -> direction = FacingNorth
                        else -> direction = FacingEast
                    }
                    else -> when (value) {
                        90 -> direction = FacingNorth
                        270 -> direction = FacingSouth
                        else -> direction = FacingEast
                    }
                }
            }
            FacingNorth -> {
                when (rotation) {
                    'L' -> when (value) {
                        90 -> direction = FacingWest
                        270 -> direction = FacingEast
                        else -> direction = FacingSouth
                    }
                    else -> when (value) {
                        90 -> direction = FacingEast
                        270 -> direction = FacingWest
                        else -> direction = FacingSouth
                    }
                }
            }
            FacingSouth -> {
                when (rotation) {
                    'L' -> when (value) {
                        90 -> direction = FacingEast
                        270 -> direction = FacingWest
                        else -> direction = FacingNorth
                    }
                    else -> when (value) {
                        90 -> direction = FacingWest
                        270 -> direction = FacingEast
                        else -> direction = FacingNorth
                    }
                }
            }
        }
    }

    private fun moveShip(type: Char, value: Int) {
        when (type) {
            'F' -> moveForward(value)
            else -> moveInDirection(type, value)
        }
    }

    private fun moveForward(value: Int) {
        when (direction) {
            FacingNorth -> northSouthPos += value
            FacingSouth -> northSouthPos -= value
            FacingEast -> eastWestPos += value
            FacingWest -> eastWestPos -= value
        }
    }

    private fun moveInDirection(type: Char, value: Int) {
        when (type) {
            'N' -> northSouthPos += value
            'S' -> northSouthPos -= value
            'E' -> eastWestPos += value
            'W' -> eastWestPos -= value
            else -> throw IllegalArgumentException("Wrong direction command: $type")
        }
    }

    val currentPosition: String
        get() {
            val eastWest = if (eastWestPos < 0) "west" else "east"
            val northSouth = if (northSouthPos < 0) "south" else "north"
            return "Ship at: ${abs(eastWestPos)} $eastWest - ${abs(northSouthPos)} $northSouth facing $direction"
        }

    val manhattenDistance: Int
        get() = abs(eastWestPos) + abs(northSouthPos)
}

class Captain {
    val ship = Ship()
    val waypoint = Waypoint()

    fun executeCommand(command: String) {
        val type = command.first()
        val value = command.substring(1)

        parseCommand(type, value.toInt())
    }

    private fun parseCommand(type: Char, value: Int) {
        when (type) {
            'N' -> waypoint.northSouth += value
            'S' -> waypoint.northSouth -= value
            'E' -> waypoint.eastWest += value
            'W' -> waypoint.eastWest -= value
            'L' -> waypoint.rotate(-value)
            'R' -> waypoint.rotate(value)
            'F' -> moveShip(value)
            else -> throw IllegalArgumentException("Not a valid command type:$type value:$value")
        }
    }

    private fun moveShip(steps: Int) {
        val (eastWest, northSouth) = waypoint.position
        val northSouthDistance = northSouth * steps
        val eastWestDistance = eastWest * steps

        ship.northSouthPos += northSouthDistance
        ship.eastWestPos += eastWestDistance
    }

    fun currentShipPosition() = ship.currentPosition

    fun currentWaypoint() = waypoint

    fun manhattenDistance() = ship.manhattenDistance
}
