package adventofcode2018

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

class Day11ChronalChargeTest {

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "3, 5, 8, 4",
            "122, 79, 57, -5",
            "217, 196, 39, 0",
            "101, 153, 71, 4"
        )
    )
    fun testPowerLevelComputation(x: Int, y: Int, serialNumber: Int, powerLevel: Int) {
        val fuelCell = FuelCell.forSerialNumber(x, y, serialNumber)

        assertEquals(powerLevel, fuelCell.powerLevel)
    }

    @Test
    fun runSamplePart1() {
        val powerGrid = PowerGrid(18)

        powerGrid.calculateScores()

        val highestPowerLevelCoordinates = powerGrid.getHighestPowerLevelCoordinates()

        assertEquals("33,45", highestPowerLevelCoordinates)
    }

    @Test
    fun solutionPart1() {
        val powerGrid = PowerGrid(9110)

        powerGrid.calculateScores()

        val highestPowerLevelCoordinates = powerGrid.getHighestPowerLevelCoordinates()

        assertEquals("21,13", highestPowerLevelCoordinates)
        println("Solution for AoC2018-Day11-Part01: $highestPowerLevelCoordinates")
    }
}