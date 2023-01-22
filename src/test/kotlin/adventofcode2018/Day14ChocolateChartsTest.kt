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
    fun runSamplesPart1(stepCount: Int, result: String) {
        val chocolateChart = ChocolateChart()

        assertEquals(result, chocolateChart.produce(stepCount))
    }

    @ParameterizedTest
    @CsvSource(
        value = arrayOf(
            "51589,9",
            "01245,5",
            "92510,18",
            "59414,2018"
        )
    )
    fun runSamplesPart2(pattern: String, result: Int) {
        val chocolateChart = ChocolateChart()

        val lastRecipe = chocolateChart.producePattern(pattern)
        assertEquals(result, lastRecipe)
    }

    @Test
    fun solutionPart1() {
        val chocolateChart = ChocolateChart()

        val result = chocolateChart.produce(47801)

        assertEquals("1342316410", result)
        println("Solution for AoC2018-Day14-Part01: $result")
    }

    @Test
    fun solutionPart2() {
        val chocolateChart = ChocolateChart()

        val result = chocolateChart.producePattern("047801")
        assertEquals(20235230, result)
        println("Solution for AoC2018-Day14-Part02: $result")
    }
}