package adventofcode2019

import java.lang.Math.toDegrees
import kotlin.math.abs
import kotlin.math.atan2

class Day10MonitoringStation

data class Asteroid(
    val x: Int,
    val y: Int,
    var isVaborized: Boolean = false
)

class Ceres(map: List<String>) {
    val asteroids: List<Asteroid>

    init {
        asteroids = map.flatMapIndexed { y, line ->
            line.mapIndexed { x, element ->
                if (element == '#') Asteroid(x, y) else null
            }
        }.filterNotNull()
    }

    fun detectionsFromBestLocation(): Int {
        val (bestX, bestY) = detectBestLocation()
        return asteroids.size - 1 - (hiddenAsteroidsFrom(bestX, bestY))
    }

    fun detectBestLocation(): Pair<Int, Int> {
        val (bestX, bestY) = asteroids.minBy { asteroid -> hiddenAsteroidsFrom(asteroid.x, asteroid.y) }
        return bestX to bestY
    }


    fun vaborize(): Int {
        val (bestX, bestY) = detectBestLocation()

        val groupsByAngle = asteroids
            .filterNot { asteroid -> (asteroid.x == bestX && asteroid.y == bestY) }
            .groupBy { asteroid -> calculateAngleFromNorth(bestX, bestY, asteroid.x, asteroid.y) }.toSortedMap()

        var counter = 0
        var nextAsteroidToVaborize: Asteroid?

        while (true) {
            groupsByAngle.forEach { group ->
                nextAsteroidToVaborize = group.value
                    .filter { asteroid -> asteroid.isVaborized == false }
                    .sortedBy { asteroid -> manhattanDistance(bestX, bestY, asteroid.x, asteroid.y) }.firstOrNull()
                nextAsteroidToVaborize?.let {
                    it.isVaborized = true
                    if (counter == 199) {
                        return (it.x * 100) + it.y
                    }
                    counter++
                }
            }
        }
    }

    fun hiddenAsteroidsFrom(x: Int, y: Int): Int {
        val u = if (upNeighborsCount(x, y) > 0) upNeighborsCount(x, y) - 1 else 0
        val d = if (downNeighborsCount(x, y) > 0) downNeighborsCount(x, y) - 1 else 0
        val l = if (leftNeighborsCount(x, y) > 0) leftNeighborsCount(x, y) - 1 else 0
        val r = if (rightNeighborsCount(x, y) > 0) rightNeighborsCount(x, y) - 1 else 0
        val a = hiddenBySameAngleCount(x, y)

        return u + d + l + r + a
    }

    fun upNeighborsCount(x: Int, y: Int) = asteroids.count { asteroid -> asteroid.x == x && asteroid.y < y }

    fun downNeighborsCount(x: Int, y: Int) = asteroids.count { asteroid -> asteroid.x == x && asteroid.y > y }

    fun leftNeighborsCount(x: Int, y: Int) = asteroids.count { asteroid -> asteroid.x < x && asteroid.y == y }

    fun rightNeighborsCount(x: Int, y: Int) = asteroids.count { asteroid -> asteroid.x > x && asteroid.y == y }

    fun hiddenBySameAngleCount(x: Int, y: Int): Int {
        val matches = asteroids.filterNot { asteroid -> asteroid.x == x || asteroid.y == y }
            .groupBy { asteroid -> calculateAngleFromNorth(x, y, asteroid.x, asteroid.y) }
        var totalCount = 0

        matches.forEach { group ->
            totalCount += if (group.value.size > 1) group.value.size - 1 else 0
        }

        return totalCount
    }

    fun calculateAngleFromNorth(referenceX: Int, referenceY: Int, asteroidX: Int, asteroidY: Int): Double {
        val angle = toDegrees(
            atan2(
                (asteroidY - referenceY).toDouble(),
                (asteroidX - referenceX).toDouble()
            )
        ) + 90

        return if (angle < 0) angle + 360 else angle
    }

    fun manhattanDistance(referenceX: Int, referenceY: Int, asteroidX: Int, asteroidY: Int) =
        abs(referenceX - asteroidX) + abs(referenceY - asteroidY)
}
