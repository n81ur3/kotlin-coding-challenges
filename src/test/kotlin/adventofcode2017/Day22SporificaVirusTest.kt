package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day22SporificaVirusTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day22_input.txt")
    }

    @Test
    fun printInitialCluster() {
        val initialState = file.readLines()
        val cluster = Cluster(initialState)
        cluster.printCluster()
        cluster.burst(part = 1)
    }

    @Test
    fun runSamplePart1() {
        val initialState = listOf(
            "..#",
            "#..",
            "..."
        )
        val cluster = Cluster(initialState)
        cluster.printCluster()
        println("\nStarting infections: \n")
        repeat(10000) {
            cluster.burst(part = 1)
        }

        assertEquals(5587, cluster.infectionCount)
    }

    @Test
    fun runSamplePart2() {
        val initialState = listOf(
            "..#",
            "#..",
            "..."
        )
        val cluster = Cluster(initialState)
        repeat(100) {
            cluster.burst(part = 2)
        }
        assertEquals(26, cluster.infectionCount)
    }

    @Test
    fun solution_part1() {
        val initialState = file.readLines()
        val cluster = Cluster(initialState)

        repeat(10_000) {
            cluster.burst(part = 1)
        }

        assertEquals(5433, cluster.infectionCount)
        println("Solution for day 20 part 1: ${cluster.infectionCount}")
    }
    @Test
    fun solution_part2() {
        val initialState = file.readLines()
        val cluster = Cluster(initialState)

        repeat(10_000_000) {
            cluster.burst(part = 2)
        }

        assertEquals(2512599, cluster.infectionCount)
        println("Solution for day 20 part 1: ${cluster.infectionCount}")
    }
}