package adventofcode2018

import kotlin.math.abs

class Day25FourDimensionalAdventure

data class SpacePoint(
    val a: Int,
    val b: Int,
    val c: Int,
    val d: Int
) {

    fun manhattanDistance(otherPoint: SpacePoint): Int =
        otherPoint.let {
            abs(a - it.a) + abs(b - it.b) + abs(c - it.c) + abs(d - it.d)
        }

    companion object {

        fun fromString(input: String): SpacePoint {
            val parts = input.split(",")
            return SpacePoint(parts[0].toInt(), parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
        }
    }
}

data class Constellation(val spacePoints: MutableList<SpacePoint>)

class SpaceTime(input: List<String>) {
    private val spacePoints: List<SpacePoint>
    private val selectedSpacePoints = mutableListOf<SpacePoint>()
    private val constellations = mutableListOf<Constellation>()
    val constellationsCount: Int
        get() = constellations.size

    init {
        spacePoints = input.map { line -> SpacePoint.fromString(line) }
    }

    fun detectConstellations() {
        while (!selectedSpacePoints.containsAll(spacePoints)) {
            val nextPoint = spacePoints.filterNot { it in selectedSpacePoints }.first()
            val constellation = Constellation(mutableListOf(nextPoint))
            buildConstellation(constellation, nextPoint)
            constellations.add(constellation)
        }
    }

    private fun buildConstellation(constellation: Constellation, spacePoint: SpacePoint) {

        constellation.spacePoints.add(spacePoint)
        selectedSpacePoints.add(spacePoint)
        val neighbors = findNeighbors(spacePoint)
        neighbors.forEach { buildConstellation(constellation, it) }
    }

    private fun findNeighbors(spacePoint: SpacePoint): List<SpacePoint> {
        val neighbors =
            spacePoints.filterNot { it in selectedSpacePoints }.filter { it.manhattanDistance(spacePoint) <= 3 }
        return neighbors.toList()
    }
}