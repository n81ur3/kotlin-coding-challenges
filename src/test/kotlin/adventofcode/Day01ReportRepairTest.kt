package adventofcode

import org.junit.jupiter.api.Test
import java.io.File

class Day01ReportRepairTest {

    @Test
    fun solution01() {
        val file = File("src/test/kotlin/resources/day01_input.txt")
        val numbers = file.readLines().map { it.toInt() }
        numbers.forEach { first ->
            numbers.forEach { second ->
                if (first + second == 2020) {
                    println("Found: first=$first + second=$second == ${first + second}")
                    println("Solution: first * second = ${first * second}")
                    return
                }
            }
        }
    }


    @Test
    fun solution02() {
        val file = File("src/test/kotlin/resources/day01_input.txt")
        val numbers = file.readLines().map { it.toInt() }
        numbers.forEach { first ->
            numbers.forEach { second ->
                numbers.forEach { third ->
                    if (first + second + third == 2020) {
                        println("Found: first=$first + second=$second + third=$third == ${first + second + third}")
                        println("Solution: first * second * third = ${first * second * third}")
                        return
                    }
                }
            }
        }
    }
}