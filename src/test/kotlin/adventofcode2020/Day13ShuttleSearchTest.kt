package adventofcode2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day13ShuttleSearchTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day13_input.txt")
    }


    @Test
    fun busTripTime13_generateSequence() {
        val bus = Bus(13)
        println("Resulting List: ${bus.intervals.take(3).toList()}")
        val result = bus.intervals.dropWhile { it < 500 }.take(1).first()
        println("Single result: $result")
    }

    @Test
    fun getWaitTimeAfterPeriod() {
        val roundtripTime = 13L
        val bus = Bus(roundtripTime)
        var waittime = bus.getWaitTimeAfter(1000)
        println("waittime after 1000 for interval 13: $waittime")
        assertEquals(roundtripTime * 77, 1001)
        assertEquals(1, waittime)

        waittime = bus.getWaitTimeAfter(10000)
        assertEquals(roundtripTime * 770, 10010)
        assertEquals(10, waittime)
    }

    @Test
    fun findRequiredTimeStampBus19() {
        val bus19 = Bus(19, 7)
        var timestampFound = false
        var counter = 0L
        val stepsize = 7
        while (!timestampFound) {
            counter += stepsize
            timestampFound = if (bus19.findIntervall(counter) != null) true else false
        }
        println("timestamp found: $counter")
    }

    @Test
    fun findRequiredTimeStamp5Busses() {
        val busses = listOf(
            Bus(13, 1),
            Bus(59, 4),
            Bus(31, 6),
            Bus(19, 7)
        )

        var counter = 0L
        val stepsize = 7
        var timestampFound = false
        while (!timestampFound) {
            counter += stepsize
            timestampFound = busses.all { it.matchesBusTimestamp(counter) }
        }
        println("timestamp found: $counter")
    }

    @Test
    fun solutionDay13Part1() {
        val lines = file.readLines()
        val earliestDeparture = lines.get(0).toLong()
        val busIntervalls = lines.get(1).split(",").filterNot { it == "x" }
        val busses = busIntervalls.map { interval -> Bus(interval.toLong()) }
        val nextAvailableBus = busses.minByOrNull { bus -> bus.getWaitTimeAfter(earliestDeparture) }
        nextAvailableBus?.let {
            val waitingTime = it.getWaitTimeAfter(earliestDeparture)
            println("Next available bus line: $it")
            println("Waiting time: $waitingTime")
            println("Solution day13 part 1: ${nextAvailableBus.tripTime * waitingTime}")
        }
    }

    @Test
    fun solutionDay13Part2() {
        val lines = file.readLines()
        val symbols = lines[1].split(",")
        var timestamp = symbols[0].toLong()
        val busses = symbols.drop(1)
            .mapIndexedNotNull { index, busId -> if (busId != "x") Bus(busId.toLong(), index + 1L) else null }

        var multiple = timestamp
        busses.forEachIndexed { index, bus ->
            while ( ((timestamp + bus.offset) % bus.tripTime) != 0L) {
                timestamp += multiple
            }
            multiple *= bus.tripTime
        }

        println("Solution day13 part 2: timestamp = $timestamp")
    }
}