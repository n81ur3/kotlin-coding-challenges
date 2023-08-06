package adventofcode2019

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day13CarePackageTest {

    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2019/aoc2019_day13_input.txt")
    }

    @Test
    fun solutionPart1() {
        val program = file.readLines()[0]
        val arcadeCabinet = ArcadeCabinet(program)

        arcadeCabinet.play()
        val blockCount = arcadeCabinet.tilesCountForType(2L)

        Assertions.assertEquals(335, blockCount)
        println("Solution for AoC2019-Day13-Part01: ${blockCount}")
    }

    @Test
    fun solutionPart2() {
        val program = file.readLines()[0]
        val arcadeCabinet = ArcadeCabinet(program)

        arcadeCabinet.enableFreePlay()
        arcadeCabinet.play()

        val finalScore = arcadeCabinet.currentScore

        Assertions.assertEquals(15706, finalScore)
        println("Solution for AoC2019-Day13-Part02: ${finalScore}")
    }
}