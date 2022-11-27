package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day03NoMatterHowYouSliceItTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day03_input.txt")
    }

    @Test
    fun parseSampleInput() {
        val input1 = "#123 @ 3,2: 5x4"
        val input2 = "#113 @ 346,519: 28x27"

        val claim1 = Claim.fromString(input1)
        println(claim1)
        val claim2 = Claim.fromString(input2)
        println(claim2)
    }

    @Test
    fun runSamplePart1() {
        val input = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        )
        val santaFabric = SantaFabric(input)
        santaFabric.putClaims()
        assertEquals(4, santaFabric.numberOfMultiClaims)
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val santaFabric = SantaFabric(input)

        santaFabric.putClaims()

        assertEquals(98005, santaFabric.numberOfMultiClaims)
        println("Solution for AoC2018-Day03-Part01: ${santaFabric.numberOfMultiClaims}")
    }
}