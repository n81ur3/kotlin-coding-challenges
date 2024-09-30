package adventofcode2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import utils.ResourceLoader
import java.io.File

class Day04SecurityThroughObscurityTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day04_input.txt")
    }

    @ParameterizedTest
    @MethodSource("sampleTestData1")
    fun runSamplesPart1(description: String, expectedResult: Boolean) {
        val room = Room.fromString(description)
        assertEquals(expectedResult, room.verifyRoom())
    }

    @Test
    fun runSamplePart1() {
        val descriptions = listOf(
            "aaaaa-bbb-z-y-x-123[abxyz]",
            "a-b-c-d-e-f-g-h-987[abcde]",
            "not-a-real-room-404[oarel]",
            "totally-real-room-200[decoy]"
        )

        val infoKiosk = InfoKiosk()
        val result = infoKiosk.checkRooms(descriptions)
        assertEquals(1514, result)
    }

    @Test
    fun runSamplePart2() {
        val room = Room("qzmt-zixmtkozy-ivhz", 343, "checksum")
        val result = room.decrypt()
        assertEquals("very encrypted name", result)
    }

    @Test
    fun solutionPart1() {
        val descriptions = file.readLines()
        val infoKiosk = InfoKiosk()
        val result = infoKiosk.checkRooms(descriptions)
        println(result)
        assertEquals(409147, result)
        println("Solution for AoC2016-Day04-Part01: $result")
    }

    @Test
    fun solutionPart2() {
        val descriptions = file.readLines()
        val infoKiosk = InfoKiosk()
        val storageID = infoKiosk.getNorthPoleObjectStorageID(descriptions)
        assertEquals(991, storageID)
        println("Solution for AoC2016-Day04-Part02: $storageID")
    }

    companion object {

        @JvmStatic
        fun sampleTestData1() = listOf(
            Arguments.of("aaaaa-bbb-z-y-x-123[abxyz]", true),
            Arguments.of("a-b-c-d-e-f-g-h-987[abcde]", true),
            Arguments.of("not-a-real-room-404[oarel]", true),
            Arguments.of("totally-real-room-200[decoy]", false),
        )
    }
}