package adventofcode2017

class Day20ParticleSwarm

class ParticleSwarm(input: List<String>) {
    val particles: MutableList<Particle>

    init {
        particles = input.mapIndexed { index, params -> parseParticle(index, params) }.toMutableList()
    }

    private fun parseParticle(index: Int, input: String): Particle {
        val position = input.substringAfter("p=<").substringBefore(">, v").split(",").map { it.trim().toLong() }
        val velocity = input.substringAfter("v=<").substringBefore(">, a").split(",").map { it.trim().toLong() }
        val acceleration = input.substringAfter("a=<").substringBefore(">").split(",").map { it.trim().toLong() }
        return Particle(
            index,
            PPosition(position[0], position[1], position[2]),
            velocity[0],
            velocity[1],
            velocity[2],
            acceleration[0],
            acceleration[1],
            acceleration[2]
        )
    }

    fun tick(counter: Int = 1) {
        repeat(counter) {
            particles.onEach { it.tick() }
        }
    }

    fun tickWithCollisions(counter: Int = 1) {
        repeat(counter) {
            particles.onEach { it.tick() }
            val collidingParticlesPositions = particles.groupBy { it.position }.filterValues { it.size > 1 }.keys
            particles.removeAll { it.position in collidingParticlesPositions }
        }
    }

    fun getClosestParticle(): Particle = particles.minBy { it.currentManhattanDistance }
}

data class Particle(
    var id: Int,
    var position: PPosition,
    var vx: Long,
    var vy: Long,
    var vz: Long,
    val ax: Long,
    val ay: Long,
    val az: Long
) {
    val currentManhattanDistance: Long
        get() = Math.abs(position.px) + Math.abs(position.py) + Math.abs(position.pz)

    fun tick() {
        vx += ax
        vy += ay
        vz += az
        position.px += vx
        position.py += vy
        position.pz += vz
    }
}

data class PPosition(var px: Long, var py: Long, var pz: Long)