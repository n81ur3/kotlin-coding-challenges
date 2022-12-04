package adventofcode2018

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day04ReposeRecordTest {
    lateinit var file: File

    private val sampleEntries = listOf(
        "[1518-11-01 00:00] Guard #10 begins shift",
        "[1518-11-01 00:05] falls asleep",
        "[1518-11-01 00:25] wakes up",
        "[1518-11-01 00:30] falls asleep",
        "[1518-11-01 00:55] wakes up",
        "[1518-11-01 23:58] Guard #99 begins shift",
        "[1518-11-02 00:40] falls asleep",
        "[1518-11-02 00:50] wakes up",
        "[1518-11-03 00:05] Guard #10 begins shift",
        "[1518-11-03 00:24] falls asleep",
        "[1518-11-03 00:29] wakes up",
        "[1518-11-04 00:02] Guard #99 begins shift",
        "[1518-11-04 00:36] falls asleep",
        "[1518-11-04 00:46] wakes up",
        "[1518-11-05 00:03] Guard #99 begins shift",
        "[1518-11-05 00:45] falls asleep",
        "[1518-11-05 00:55] wakes up "
    )

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2018/aoc2018_day04_input.txt")
    }

    @Test
    fun shiftParsing() {
        var input = "[1518-11-01 00:00] Guard #10 begins shift"
        var shiftEntry = ShiftEntry.fromString(input)
        var expected = "ShiftEntry(dateTime=1518-11-01T00:00, guard=Guard(id=10), awake=true, asleep=false)"
        assertEquals(expected, shiftEntry.toString())

        input = "[1518-11-01 00:05] falls asleep"
        shiftEntry = ShiftEntry.fromString(input)
        expected = "ShiftEntry(dateTime=1518-11-01T00:05, guard=Guard(id=-1), awake=false, asleep=true)"
        assertEquals(expected, shiftEntry.toString())

        input = "[1518-11-01 00:25] wakes up"
        shiftEntry = ShiftEntry.fromString(input)
        expected = "ShiftEntry(dateTime=1518-11-01T00:25, guard=Guard(id=-1), awake=true, asleep=false)"
        assertEquals(expected, shiftEntry.toString())
    }

    @Test
    fun runSamplePart1() {
        val shiftPlan = ShiftPlan(sampleEntries)
        shiftPlan.scheduleShifts()
        val (bestGuard, bestMinute) = shiftPlan.findBestGuardAndMinute()
        assertEquals(10, bestGuard.id)
        assertEquals(24, bestMinute)
        assertEquals(240, bestGuard.id * bestMinute)
        println("Best guard: $bestGuard - best Minute: $bestMinute -> result: ${bestGuard.id * bestMinute}")
    }

    @Test
    fun runSamplePart2() {
        val shiftPlan = ShiftPlan(sampleEntries)
        shiftPlan.scheduleShifts()
        val (bestGuard, bestMinute) = shiftPlan.findBestGuardMostFrequentMinute()
        assertEquals(4455, bestGuard.id * bestMinute)
        println("Solution for sample part 2: bestGuard= $bestGuard - bestMinute=$bestMinute - result=${bestGuard.id * bestMinute}")
    }

    @Test
    fun solutionPart1() {
        val input = file.readLines()
        val shiftPlan = ShiftPlan(input)

        shiftPlan.scheduleShifts()
        val (bestGuard, bestMinute) = shiftPlan.findBestGuardAndMinute()
        val solution = bestGuard.id * bestMinute
        assertEquals(101194, solution)
        println("Solution for AoC2018-Day04-Part01: $solution")
    }

    @Test
    fun solutionPart2() {
        val input = file.readLines()
        val shiftPlan = ShiftPlan(input)

        shiftPlan.scheduleShifts()
        val (bestGuard, bestMinute) = shiftPlan.findBestGuardMostFrequentMinute()
        val solution = bestGuard.id * bestMinute

        assertEquals(102095, solution)
        println("Solution for AoC2018-Day04-Part02: $solution")
    }
}