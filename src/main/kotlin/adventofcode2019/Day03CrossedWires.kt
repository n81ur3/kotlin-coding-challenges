package adventofcode2019

import kotlin.math.abs

class Day03CrossedWires

data class DistanceCoordinate(
    val distance: Int,
    val coordinate: Pair<Int, Int>
)

data class GravityWire(val coordinates: List<Pair<Int, Int>> = emptyList()) {
    val distanceCoordinates = mutableListOf<DistanceCoordinate>()

    fun buildPathFromString(input: String) {
        var currentX = 0
        var currentY = 0
        var distance = 0
        val parts = input.split(",")

        parts.forEach { part ->
            val dist = part.substring(1).toInt()
            when (part.first()) {
                'R' -> {
                    repeat(dist) {
                        distanceCoordinates.add(DistanceCoordinate(++distance, ++currentX to currentY))
                    }
                }

                'D' -> {
                    repeat(dist) {
                        distanceCoordinates.add(DistanceCoordinate(++distance, currentX to ++currentY))
                    }
                }

                'L' -> {
                    repeat(dist) {
                        distanceCoordinates.add(DistanceCoordinate(++distance, --currentX to currentY))
                    }
                }

                else -> {
                    repeat(dist) {
                        distanceCoordinates.add(DistanceCoordinate(++distance, currentX to --currentY))
                    }
                }
            }
        }
    }

    companion object {
        var currentX = 0
        var currentY = 0
        var path = mutableListOf<Pair<Int, Int>>()

        fun fromString(input: String): GravityWire {
            if (input.isBlank()) return GravityWire()
            currentX = 0
            currentY = 0
            path = mutableListOf()
            val parts = input.split(",")

            parts.forEach { direction ->
                move(direction.first(), direction.substring(1))
            }

            return GravityWire(path.toList())
        }

        private fun move(direction: Char, distance: String) {
            val dist = distance.toInt()
            when (direction) {
                'R' -> {
                    repeat(dist) {
                        path.add(++currentX to currentY)
                    }
                }

                'D' -> {
                    repeat(dist) {
                        path.add(currentX to ++currentY)
                    }
                }

                'L' -> {
                    repeat(dist) {
                        path.add(--currentX to currentY)
                    }
                }

                else -> {
                    repeat(dist) {
                        path.add(currentX to --currentY)
                    }
                }
            }
        }
    }
}

class FuelManagementSystem(firstWireDirections: String = "", secondWireDirections: String = "") {
    var firstWire: GravityWire
    var secondWire: GravityWire

    init {
        firstWire = GravityWire.fromString(firstWireDirections)
        secondWire = GravityWire.fromString(secondWireDirections)
    }

    fun findClosestTwistDistance(): Int {
        val intersections = mutableListOf<Pair<Int, Int>>()
        firstWire.coordinates.forEach { firstCoordinate ->
            secondWire.coordinates.forEach { secondCoordinate ->
                if (firstCoordinate == secondCoordinate) intersections.add(firstCoordinate)
            }
        }
        val closestTwist = intersections.sortedBy { abs(it.first) + abs(it.second) }.first()
        return abs(closestTwist.first) + abs(closestTwist.second)
    }

    fun findClosestTwistDistanceSteps(): Int {
        val intersections = mutableListOf<Int>()
        firstWire.distanceCoordinates.forEach { firstCoordinate ->
            secondWire.distanceCoordinates.forEach { secondCoordinate ->
                if (firstCoordinate.coordinate == secondCoordinate.coordinate) intersections.add(firstCoordinate.distance + secondCoordinate.distance)
            }
        }
        return intersections.min()
    }
}