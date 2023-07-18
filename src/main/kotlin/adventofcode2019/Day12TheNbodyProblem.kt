package adventofcode2019

import kotlin.math.abs

class Day12TheNbodyProblem

data class MoonVelocity(var x: Int, var y: Int, var z: Int) {
    val kineticEnergy: Int
        get() = abs(x) + abs(y) + abs(z)
}

data class JupiterMoon(val id: Int, var x: Int, var y: Int, var z: Int, val velocity: MoonVelocity) {
    val totalEnergy: Int
        get() = (abs(x) + abs(y) + abs(z)) * velocity.kineticEnergy

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