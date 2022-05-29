package adventofcode2020

import adventofcode2020.WaypointDirection.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day12RainRiskTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day12_input.txt")
    }

    @Test
    fun sampleInput_movesShipTo_17_8() {
        val sampleLines = listOf(
            "F10",
            "N3",
            "F7",
            "R90",
            "F11"
        )
        val ship = Ship()
        println("Journey Start: ${ship.currentPosition}")
        sampleLines.forEach { ship.executeInstruction(it) }
        println("Journey End: ${ship.currentPosition}")

        assertEquals(25, ship.manhattenDistance)
    }

    @Test
    fun solutionDay12Part1() {
        val lines = file.readLines()
        val ship = Ship()
        println("Journey Start: ${ship.currentPosition}")
        lines.forEach { ship.executeInstruction(it) }
        println("Journey End: ${ship.currentPosition}")

        println("Manhatten Distance: ${ship.manhattenDistance}")
    }

    @Test
    fun rotateWaypointRight() {
        val waypoint = Waypoint()
        assertEquals(10 to 1, waypoint.position)
        assertEquals(NorthEast, waypoint.currentDirection())
        waypoint.rotate(90)
        assertEquals(SouthEast, waypoint.currentDirection())
        waypoint.rotate(90)
        assertEquals(SouthWest, waypoint.currentDirection())
        waypoint.rotate(90)
        assertEquals(NorthWest, waypoint.currentDirection())
        waypoint.rotate(90)
        assertEquals(NorthEast, waypoint.currentDirection())

        waypoint.rotate(180)
        assertEquals(SouthWest, waypoint.currentDirection())
        waypoint.rotate(180)
        assertEquals(NorthEast, waypoint.currentDirection())

        waypoint.rotate(270)
        assertEquals(NorthWest, waypoint.currentDirection())
        waypoint.rotate(270)
        assertEquals(SouthWest, waypoint.currentDirection())
        waypoint.rotate(180)
        assertEquals(NorthEast, waypoint.currentDirection())
    }

    @Test
    fun rotateWaypointLeft() {
        val waypoint = Waypoint()
        assertEquals(NorthEast, waypoint.currentDirection())
        waypoint.rotate(-90)
        assertEquals(NorthWest, waypoint.currentDirection())
        waypoint.rotate(-90)
        assertEquals(SouthWest, waypoint.currentDirection())
        waypoint.rotate(-90)
        assertEquals(SouthEast, waypoint.currentDirection())
        waypoint.rotate(-90)
        assertEquals(NorthEast, waypoint.currentDirection())

        waypoint.rotate(-180)
        assertEquals(SouthWest, waypoint.currentDirection())
        waypoint.rotate(-180)
        assertEquals(NorthEast, waypoint.currentDirection())

        waypoint.rotate(-270)
        assertEquals(SouthEast, waypoint.currentDirection())
        waypoint.rotate(-270)
        assertEquals(SouthWest, waypoint.currentDirection())
        waypoint.rotate(-180)
        assertEquals(NorthEast, waypoint.currentDirection())
    }

    @Test
    fun moveWaypoint() {
        val waypoint = Waypoint()
        assertEquals(10 to 1, waypoint.position)
        waypoint.move(North, 3)
        assertEquals(10 to 4, waypoint.position)
        waypoint.move(South, 32)
        assertEquals(10 to -28, waypoint.position)
        waypoint.move(West, 38)
        assertEquals(-28 to -28, waypoint.position)
        waypoint.move(East, 52)
        assertEquals(24 to -28, waypoint.position)
    }

    @Test
    fun rotateAndMoveWaypoint() {
        val waypoint = Waypoint()
        assertEquals(10 to 1, waypoint.position)
        waypoint.move(North, 3)
        assertEquals(10 to 4, waypoint.position)
        waypoint.rotate(90)
        assertEquals(4 to -10, waypoint.position)
    }

    @Test
    fun multipleRotateAndMoveWaypoint() {
        val waypoint = Waypoint()
        assertEquals(10 to 1, waypoint.position)
        waypoint.move(North, 3)
        assertEquals(10 to 4, waypoint.position)
        waypoint.rotate(90)
        assertEquals(4 to -10, waypoint.position)
        println(waypoint)
        waypoint.rotate(-90)
        println(waypoint)
        assertEquals(10 to 4, waypoint.position)
        waypoint.rotate(180)
        assertEquals(-10 to -4, waypoint.position)
        waypoint.rotate(-180)
        assertEquals(10 to 4, waypoint.position)
        waypoint.rotate(-180)
        assertEquals(-10 to -4, waypoint.position)
        waypoint.rotate(-180)
        assertEquals(10 to 4, waypoint.position)
        waypoint.rotate(270)
        assertEquals(-4 to 10, waypoint.position)
        waypoint.rotate(180)
        assertEquals(4 to -10, waypoint.position)
        waypoint.rotate(270)
        assertEquals(10 to 4, waypoint.position)
        waypoint.rotate(-270)
        assertEquals(4 to -10, waypoint.position)
    }

    @Test
    fun captainExecuteSampleCommands() {
        val commands = listOf(
            "F10",
            "N3",
            "F7",
            "R90",
            "F11"
        )

        val captain = Captain()
        commands.forEach { captain.executeCommand(it)
            println()
            println("after command $it - ship at ${captain.currentShipPosition()}")
            println("waypoint: ${captain.currentWaypoint()}")
            println()
        }

        println(captain.currentShipPosition())
        println(captain.manhattenDistance())
    }

    @Test
    fun solutionDay12Part2() {
        val commands = file.readLines()

        val captain = Captain()
        commands.forEach { captain.executeCommand(it) }

        println("Ship position after command execution: ${captain.currentShipPosition()}")

        println("Solution for Day12 Part2: Manhatten Distance = ${captain.manhattenDistance()}")
    }
}