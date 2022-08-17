package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day16TicketTranslationTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day16_input.txt")
    }

    @Test
    fun solutionDay16Part1() {
        val lines = file.readLines()

        val ranges = lines.takeWhile { it.isNotEmpty() }
        val nearbyTicketHeadlineIndex = lines.indexOf("nearby tickets:")
        val nearbyTickets = lines.drop(nearbyTicketHeadlineIndex + 1)

        val ticketReader = TicketReader()
        ranges.forEach { range -> ticketReader.readRangeInput(range) }

        println("Ranges: ${ticketReader.validRanges}")

        val numbersToValidate = mutableListOf<Int>()
        nearbyTickets.forEach { it.split(",").map { it.toInt() }.forEach { numbersToValidate.add(it) } }

        var totalErrorRate = 0
        numbersToValidate.forEach {
            if (!ticketReader.validateNumber(it)) {
                totalErrorRate += it; println("invalid number: $it")
            }
        }

        println("Solution day16 part 1: Total error rate = $totalErrorRate")
    }

    @Test
    fun solutionDay16Part2() {
        val lines = file.readLines()
        val ticketReader = TicketReader()

        val ranges = lines.takeWhile { it.isNotEmpty() }
        val nearbyTicketHeadlineIndex = lines.indexOf("nearby tickets:")
        val nearbyTickets = lines.drop(nearbyTicketHeadlineIndex + 1)
        val myTicketHeadlineIndex = lines.indexOf("your ticket:")
        val myTicket = lines.get(myTicketHeadlineIndex + 1)

        ranges.forEach { range -> ticketReader.readRangeInput(range) }
        val validTickets = nearbyTickets.filter { ticketReader.validateTicket(it) }
        validTickets.forEach { ticket -> ticketReader.readValidTicket(ticket) }

        val ticketFields = ticketReader.determineValidRangeAtIndex()

        val myTicketFields = myTicket.split(",").map(String::toInt)

        for ((index, rangeName) in ticketFields) {
            println("index[$index] = $rangeName")
        }

        val departureFieldIndexes = ticketFields.filter { entry -> entry.second.contains("departure") }

        var result = 1L
        departureFieldIndexes.forEach { entry -> result *= myTicketFields[entry.first] }

        //426362917709
        println("Solution day16 part 2: $result")
    }
}