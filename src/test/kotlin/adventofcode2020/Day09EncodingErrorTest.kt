package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day09EncodingErrorTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day09_input.txt")
    }

    @Test
    fun solutionDay09_part1() {
        val codes = file.readLines().map { it.toLong() }

        val codeList = findCodeList(codes)
        println("Resulting list: $codeList")
    }

    private fun findCodeList(codes: List<Long>): List<Long>? {
        return codes.windowed(26, 1, true).find { !checkCode(it) }.also { match ->
            match?.run {
                println("Found invalid code: ${last()}")
            }
        }
    }

    private fun checkCode(codes: List<Long>): Boolean {
        val preamble = codes.subList(0, codes.size)
        val candidate = codes.last()

        preamble.forEach { first ->
            preamble.forEach { second ->
                if (first + second == candidate && first != second) return true
            }
        }

        return false
    }

    @Test
    fun solutionDay09_part2() {
        val codes = file.readLines().map { it.toLong() }
        val errorCode = findCodeList(codes)!!.last()

        val possibleCodes = codes.takeWhile { it < errorCode }

        val codeRange = (2..possibleCodes.size).map { windowSize ->
            possibleCodes.windowed(windowSize, 1, false)
        }.flatten().firstOrNull { it.sum() == errorCode }
        codeRange?.run {
            println("Result: $codeRange sum=${codeRange.sum()}")

            val maxCode = codeRange.maxByOrNull { it }
            val minCode = codeRange.minByOrNull { it }
            println("Max in errorList=$maxCode")
            println("Min in errorList=$minCode")
            maxCode?.let {
                minCode?.let {
                    println("Solution Day09 part2 =${minCode + maxCode}}")
                }
            }
        }
    }


    //println("Result part2: $result")
    //(2..codeList)
}