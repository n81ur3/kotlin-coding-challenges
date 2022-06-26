package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day18OperationOrderTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day18_input.txt")
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
    fun evaluateSamples() {
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
        val lines = file.readLines().map { line -> line.replace(" ", "")}

        val evaluator = OperationEvaluator()
        var result = 0L
        lines.forEach { result += evaluator.evaluate(it)}

        println("Solution day18 part 1: Total sum = $result")
    }
}