package adventofcode2019

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

class Day12TheNbodyProblem

data class MoonVelocity(var x: Int, var y: Int, var z: Int) {
    val kineticEnergy: Int
        get() = abs(x) + abs(y) + abs(z)
}

data class JupiterMoon(val id: Int, var x: Int, var y: Int, var z: Int, val velocity: MoonVelocity) {
    val totalEnergy: Int
        get() = (abs(x) + abs(y) + abs(z)) * velocity.kineticEnergy
    val currentState: List<Int>
        get() = listOf(x, y, z, velocity.x, velocity.y, velocity.z)
    val currentCoordinates: List<Int>
        get() = listOf(x, y, z)

    fun applyGravityFromPeer(peer: JupiterMoon) {
        if (x < peer.x) velocity.x++
        else if (x > peer.x) velocity.x--
        if (y < peer.y) velocity.y++
        else if (y > peer.y) velocity.y--
        if (z < peer.z) velocity.z++
        else if (z > peer.z) velocity.z--
    }

    fun adjustCoordinatesByVelocity() {
        x += velocity.x
        y += velocity.y
        z += velocity.z
    }
}

class JupiterOrbit(moonCoordinates: List<String>) {
    val moons: List<JupiterMoon>

    init {
        moons = moonCoordinates.mapIndexed { index, coordinates -> parseMoon(index, coordinates) }
    }

    private fun parseMoon(id: Int, coordinates: String): JupiterMoon {
        val x = coordinates.substringAfter("x=").substringBefore(",").toInt()
        val y = coordinates.substringAfter("y=").substringBefore(",").toInt()
        val z = coordinates.substringAfter("z=").substringBefore(">").toInt()
        return JupiterMoon(id, x, y, z, MoonVelocity(0, 0, 0))
    }

    fun step(stepCount: Int) {
        repeat(stepCount) {
            applyGravity()
            applyVelocity()
        }
    }

    fun stepsUntilCircle(): Long {
        val startingX: List<Pair<Int, Int>> = moons.map { it.x to it.velocity.x }
        val startingY: List<Pair<Int, Int>> = moons.map { it.y to it.velocity.y }
        val startingZ: List<Pair<Int, Int>> = moons.map { it.z to it.velocity.z }
        var foundX: Long? = null
        var foundY: Long? = null
        var foundZ: Long? = null

        var stepCount = 0L
        do {
            stepCount++
            step(1)
            foundX = if (foundX == null && startingX == moons.map { it.x to it.velocity.x }) stepCount else foundX
            foundY = if (foundY == null && startingY == moons.map { it.y to it.velocity.y }) stepCount else foundY
            foundZ = if (foundZ == null && startingZ == moons.map { it.z to it.velocity.z }) stepCount else foundZ
        } while (foundX == null || foundY == null || foundZ == null)

        return lcm(foundX, foundY, foundZ)
    }

    fun lcm(vararg numbers: Long): Long {

        val groups = mutableListOf<List<Pair<Long, Long>>>()

        for (n in numbers) {
            groups.add(groupNumbers(factorize(n)))
        }

        val numberGroups = groups.flatMap { it }

        val orderedGroups =
            numberGroups.map { g -> g.first to numberGroups.filter { it.first == g.first }.maxOf { it.second } }.toSet()

        return orderedGroups.fold(1) { acc, pair -> acc * (pair.first.pow(pair.second)) }
    }

    private fun Long.pow(exponent: Long) = Math.pow(this.toDouble(), exponent.toDouble()).toInt()

    fun factorize(number: Long): List<Long> {
        val result: ArrayList<Long> = arrayListOf()

        var n = number

        while (n % 2L == 0L) {
            result.add(2)
            n /= 2
        }

        val squareRoot = sqrt(number.toDouble()).toInt()

        for (i in 3..squareRoot step 2) {
            while (n % i == 0L) {
                result.add(i.toLong())
                n /= i
            }
        }

        if (n > 2) {
            result.add(n)
        }

        return result
    }

    fun groupNumbers(nums: List<Long>): List<Pair<Long, Long>> {
        val groups = nums.groupBy { it }
        val groupCounts = groups.map { it.key to it.value.size.toLong() }
        return groupCounts
    }

    private fun applyGravity() {
        val oldState = moons.toList()

        moons.forEach { moon ->
            oldState.filter { it.id != moon.id }.forEach { moon.applyGravityFromPeer(it) }
        }
    }

    private fun applyVelocity() {
        moons.forEach { it.adjustCoordinatesByVelocity() }
    }

    fun totalEnergy() = moons.sumOf { moon -> moon.totalEnergy }
}