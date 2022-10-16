package adventofcode2017

class Day20ParticleSwarm

class ParticleSwarm(input: List<String>) {
    val particles: List<Particle>

    init {
        particles = input.mapIndexed { index, params -> parseParticle(index, params) }
    }

    private fun parseParticle(index: Int, input: String): Particle {
        val position = input.substringAfter("p=<").substringBefore(">, v").split(",").map { it.trim().toLong() }
        val velocity = input.substringAfter("v=<").substringBefore(">, a").split(",").map { it.trim().toLong() }
        val acceleration = input.substringAfter("a=<").substringBefore(">").split(",").map { it.trim().toLong() }
        return Particle(
            index,
            position[0],
            position[1],
            position[2],
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

    fun getClosestParticle(): Particle = particles.minBy { it.currentManhattanDistance }
}

data class Particle(
    var id: Int,
    var px: Long,
    var py: Long,
    var pz: Long,
    var vx: Long,
    var vy: Long,
    var vz: Long,
    val ax: Long,
    val ay: Long,
    val az: Long
) {
    val currentManhattanDistance: Long
        get() = Math.abs(px) + Math.abs(py) + Math.abs(pz)

    fun tick() {
        vx += ax
        vy += ay
        vz += az
        px += vx
        py += vy
        pz += vz
    }

    fun currentPosition() = println("px=$px,py=$py,pz=$pz")
}