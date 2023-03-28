package adventofcode2018

import kotlin.math.abs

class Day23ExperimentalEmergencyTeleportation

data class Nanobot(val x: Int, val y: Int, val z: Int, val radius: Int) {

    fun inRange(otherBot: Nanobot): Boolean {
        val manhattanDistance = abs(otherBot.x - x) + abs(otherBot.y - y) + abs(otherBot.z - z)
        return manhattanDistance <= otherBot.radius
    }
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
}