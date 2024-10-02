package adventofcode2016

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File


class Day05NiceGameOfChessTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2016/aoc2016_day04_input.txt")
    }

    @Test
    fun runSamplePart1() {
        val md5Station = Md5Station()
        val password = md5Station.findPassword("abc")
        assertEquals("18f47a30", password)
    }

    @Test
    fun runSamplePart2() {
        val md5Station = Md5Station()
        val password = md5Station.findBetterPassword("abc")
        assertEquals("05ace8e3", password)
    }

    @Test
    fun solutionPart1() {
        val md5Station = Md5Station()
        val password = md5Station.findPassword("wtnhxymk")
        assertEquals("2414bc77", password)
        println("Solution for AoC2016-Day05-Part01: $password")
    }

    @Test
    fun solutionPart2() {
        val md5Station = Md5Station()
        val password = md5Station.findBetterPassword("wtnhxymk")
        println(password)
        assertEquals("437e60fc", password)
        println("Solution for AoC2016-Day05-Part02: $password")
    }
}