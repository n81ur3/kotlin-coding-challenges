package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day02InventoryManagmentSystemTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day02_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val boxIds = listOf(
            "abcdef",
            "bababc",
            "abbcde",
            "abcccd",
            "aabcdd",
            "abcdee",
            "ababab"
        )

        val inventorySystem = InventorySystem(boxIds)
        assertEquals(12, inventorySystem.checkSum())
    }

    @Test
    fun runSamplePart2() {
        val boxIds = listOf(
            "abcde",
            "fghij",
            "klmno",
            "pqrst",
            "fguij",
            "axcye",
            "wvxyz"
        )

        val inventorySystem = InventorySystem(boxIds)
        val commonChars = inventorySystem.findPrototypes()
        assertEquals("fgij", commonChars)
    }

    @Test
    fun solutionPart1() {
        val boxIds = file.readLines()
        val inventorySystem = InventorySystem(boxIds)
        val checksum = inventorySystem.checkSum()

        assertEquals(7221, checksum)
        println("Solution for AoC2018-Day02-Part01: $checksum")
    }

    @Test
    fun solutionPart2() {
        val boxIds = file.readLines()
        val inventorySystem = InventorySystem(boxIds)

        val commonChars = inventorySystem.findPrototypes()
        assertEquals("mkcdflathzwsvjxrevymbdpoq", commonChars)
        println("Solution for AoC2018-Day02-Part02: $commonChars")
    }
}