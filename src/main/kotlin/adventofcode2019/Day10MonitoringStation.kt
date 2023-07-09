package adventofcode2019

import java.lang.Math.PI
import kotlin.math.abs
import kotlin.math.tan

class Day10MonitoringStation

data class Asteroid(
    val x: Int,
    val y: Int
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
        val (bestX, bestY) = asteroids.minBy { asteroid -> hiddenAsteroidsFrom(asteroid.x, asteroid.y)}
        println("Best location: ${bestX},${bestY}")
        return asteroids.size - 1 - (hiddenAsteroidsFrom(bestX, bestY))
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
            .groupBy { asteroid -> calculateAngle(x, y, asteroid.x, asteroid.y) }
        var totalCount = 0

        matches.forEach { group ->
            totalCount += if (group.value.size > 1) group.value.size - 1 else 0
        }

        return totalCount
    }

    fun calculateAngle(referenceX: Int, referenceY: Int, asteroidX: Int, asteroidY: Int): Double {
        if (asteroidX > referenceX && asteroidY < referenceY) return tan((referenceY - asteroidY).toDouble() / (asteroidX - referenceX))
        if (asteroidX < referenceX && asteroidY < referenceY) return PI - tan((referenceY - asteroidY).toDouble() / (referenceX - asteroidX))
        if (asteroidX < referenceX && asteroidY > referenceY) return PI + tan((asteroidY - referenceY).toDouble() / (referenceX - asteroidX))
        else return 2 * PI - tan((asteroidY - referenceY).toDouble() / (asteroidX - referenceX))
    }
}
