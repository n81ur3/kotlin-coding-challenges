package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day20ParticleSwarmTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day20_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>",
            "p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>"
        )
        val particleSwarm = ParticleSwarm(input)
        particleSwarm.tick(3)

        val closestParticle = particleSwarm.getClosestParticle()
        println("Closest particle sample part 1: ${closestParticle.id} with distance ${closestParticle.currentManhattanDistance}")
        assertEquals(0, closestParticle.id)
    }

    @Test
    fun runSamplePart2() {
        val input = listOf(
            "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
            "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
            "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
            "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"
        )
        val particleSwarm = ParticleSwarm(input)
        particleSwarm.tickWithCollisions(3)
        val remainingParticles = particleSwarm.particles.size

        assertEquals(1, remainingParticles)
        println("Remaining particles after running sample part 2: ${particleSwarm.particles}")
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val particleSwarm = ParticleSwarm(input)

        particleSwarm.tick(100000)
        val closestParticle = particleSwarm.getClosestParticle()

        assertEquals(161, closestParticle.id)
        println("Solution for day 20 part 1: ${closestParticle.id}")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()
        val particleSwarm = ParticleSwarm(input)

        particleSwarm.tickWithCollisions(10000)
        val remainingParticles = particleSwarm.particles.size

        assertEquals(438, remainingParticles)
        println("Solution for day 20 part 2: $remainingParticles")
    }
}