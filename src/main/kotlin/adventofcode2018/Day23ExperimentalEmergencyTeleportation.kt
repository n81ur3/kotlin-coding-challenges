package adventofcode2018

import kotlin.math.abs

class Day23ExperimentalEmergencyTeleportation

data class TeleportCoordinate(val x: Int, val y: Int, val z: Int)

data class Nanobot(val x: Int, val y: Int, val z: Int, val radius: Int) {

    fun inRange(otherBot: Nanobot): Boolean {
        val manhattanDistance = distanceTo(otherBot.x, otherBot.y, otherBot.z)
        return manhattanDistance <= otherBot.radius
    }

    fun withinRangeOfSharedPoint(otherBot: Nanobot) =
        distanceTo(otherBot.x, otherBot.y, otherBot.z) <= radius + otherBot.radius

    fun inRange(xDist: Int, yDist: Int, zDist: Int) = distanceTo(xDist, yDist, zDist) <= radius

    internal fun distanceTo(xDist: Int, yDist: Int, zDist: Int) =
        abs(x - xDist) + abs(y - yDist) + abs(z - zDist)
}

class TeleportationDevice(val input: List<String>) {
    val bots: List<Nanobot>
    val strongestBot: Nanobot
        get() = bots.maxBy { it.radius }

    init {
        bots = input.map { parseBot(it) }
    }

    private fun parseBot(input: String): Nanobot {
        val coordinates = input.substringAfter("<").substringBefore(">")
        val (x, y, z) = coordinates.split(",").map { it.toInt() }
        return Nanobot(x, y, z, input.substringAfterLast("=").toInt())
    }

    fun numberOfBotsInRangeOfStrongest() = bots.count { it.inRange(strongestBot) }

    fun strongestCoordinate(): TeleportCoordinate {
        var strongest = TeleportCoordinate(0, 0, 0)
        var strongestCount = 0

        val minX = bots.minOf { it.x }
        val maxX = bots.maxOf { it.x }
        val minY = bots.minOf { it.y }
        val maxY = bots.maxOf { it.y }
        val minZ = bots.minOf { it.z }
        val maxZ = bots.maxOf { it.z }

        var botsCount: Int
        (minX..maxX).forEach { x ->
            (minY..maxY).forEach { y ->
                (minZ..maxZ).forEach { z ->
                    botsCount = bots.count { it.inRange(x, y, z) }
                    if (botsCount > strongestCount) {
                        strongestCount = botsCount
                        strongest = TeleportCoordinate(x, y, z)
                    }
                }
            }
        }
        return strongest
    }

    fun strongestDistance(): Int {
        val neighbors: Map<Nanobot, Set<Nanobot>> = bots.map { bot ->
            Pair(bot, bots.filterNot { it == bot }.filter { bot.withinRangeOfSharedPoint(it) }.toSet())
        }.toMap()

        val clique: Set<Nanobot> = BronKerbosch(neighbors).largestClique()
        return clique.map { it.distanceTo(0, 0, 0) - it.radius }.max()
    }
}

class BronKerbosch<T>(val neighbors: Map<T, Set<T>>) {

    private var bestR: Set<T> = emptySet()

    fun largestClique(): Set<T> {
        execute(neighbors.keys)
        return bestR
    }

    private fun execute(p: Set<T>, r: Set<T> = emptySet(), x: Set<T> = emptySet()) {
        if (p.isEmpty() && x.isEmpty()) {
            if (r.size > bestR.size) bestR = r
        } else {
            val mostNeighborsOfPandX: T = (p + x).maxBy { neighbors.getValue(it).size }!!
            val pWithoutNeighbors = p.minus(neighbors[mostNeighborsOfPandX]!!)
            pWithoutNeighbors.forEach { v ->
                val neighborsOfV = neighbors[v]!!
                execute(p.intersect(neighborsOfV), r + v, x.intersect(neighborsOfV))
            }
        }
    }
}