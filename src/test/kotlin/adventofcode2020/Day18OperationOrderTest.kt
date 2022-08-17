package adventofcode2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day18OperationOrderTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day18_input.txt")
    }


    @Test
    fun simpleEvaluation() {
        val operation = "1+2*3+4*5+6"
        val evaluator = OperationEvaluator()
        println(evaluator.evaluateOperation(operation))
    }

    @Test
    fun findMatchingParantheses() {
        val operation = "1+(2*3)+(4*(5+6))"
        val evaluator = OperationEvaluator()
        println(evaluator.findMatchingParantheses(operation))
    }

    @Test
    fun replaceParantheses() {
        val operation = "1+(2*3)+(4*(5+6))"
        val evaluator = OperationEvaluator()
        println(evaluator.evaluate(operation))
    }

    @Test
    fun evaluateSamplesPart1() {
        val operations = listOf(
            "2*3+(4*5)",
            "5+(8*3+9+3*4*3)",
            "5*9*(7*3*3+9*3+(8+6*4))",
            "((2+4*9)*(6+9*8+6)+6)+2+4*2"
        )

        val evaluator = OperationEvaluator()
        operations.forEach { println(evaluator.evaluate(it)) }
    }

    @Test
    fun solutionDay18Part01() {
        val lines = file.readLines().map { line -> line.replace(" ", "") }

        val evaluator = OperationEvaluator()
        var result = 0L
        lines.forEach { result += evaluator.evaluate(it) }

        println("Solution day18 part 1: Total sum = $result")
    }

    @Test
    fun additionBeforeMultiplication() {
        val operation = "2*22+(300*400)+333"
        val evaluator = OperationEvaluator()
        println(evaluator.evaluate(operation))
    }

    @Test
    fun evaluateSamplesPart2() {
        val operations = listOf(
            "1+(2*3)+(4*(5+6))",
            "2*3+(4*5)",
            "5+(8*3+9+3*4*3)",
            "5*9*(7*3*3+9*3+(8+6*4))",
            "((2+4*9)*(6+9*8+6)+6)+2+4*2"
        )
        val result = listOf(51L, 46L, 1445L, 669060L, 23340L)

        val evaluator = OperationEvaluator()
        operations.forEachIndexed { index, operation ->
            Assertions.assertEquals(result[index], evaluator.evaluate(operation))
        }
    }

    @Test
    fun solutionDay18Part02() {
        val lines = file.readLines().map { line -> line.replace(" ", "") }

        val evaluator = OperationEvaluator()
        var result = 0L
        lines.forEach { result += evaluator.evaluate(it) }

        println("Solution day18 part 2: Total sum = $result")
    }
}