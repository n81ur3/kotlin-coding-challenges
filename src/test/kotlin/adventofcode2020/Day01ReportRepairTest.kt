package adventofcode2020

import org.junit.jupiter.api.Test
import java.io.File

class Day01ReportRepairTest {

    @Test
    fun solution01_1() {
        val file = ResourceLoader.getFile("day01_input.txt")
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
    fun solution01_2() {
        val file = ResourceLoader.getFile("day01_input.txt")
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