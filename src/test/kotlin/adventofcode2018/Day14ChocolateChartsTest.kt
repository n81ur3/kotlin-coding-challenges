package adventofcode2018

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14ChocolateChartsTest {

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "5,01245158916",
            "18,9251071085",
            "2018,5941429882"
        )
    )
    fun runSamplePart1(stepCount: Int, result: String) {
        val chocolateChart = ChocolateChart()

        assertEquals(result, chocolateChart.produce(stepCount))
    }

    @Test
    fun solutionPart1() {
        val chocolateChart = ChocolateChart()

        val result = chocolateChart.produce(47801)

        assertEquals("1342316410", result)
        println("Solution for AoC2018-Day14-Part01: $result")
    }
}