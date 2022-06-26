package adventofcode2020

class Day18OperationOrder

class OperationEvaluator {

    private fun String.charAsInt(index: Int): Int = Integer.parseInt(this[index].toString())

    fun evaluate(operation: String): Long {
        val purgedOperation = evaluateAndReplaceParantheses(operation)
        return evaluateOperation(purgedOperation)
    }

    private fun evaluateAndReplaceParantheses(operation: String): String {
        var purgedOperation = operation
        val stringBuilder = StringBuilder()
        while (purgedOperation.contains("(")) {
            val matchingParantheses = findMatchingParantheses(purgedOperation)
            val firstPair = matchingParantheses[0]
            stringBuilder.clear()
            stringBuilder.append(purgedOperation.substring(0, firstPair.first))
            stringBuilder.append(evaluateOperation(purgedOperation.substring(firstPair.first + 1, firstPair.second)))
            stringBuilder.append(purgedOperation.substring(firstPair.second + 1))
            purgedOperation = stringBuilder.toString()
        }
        return purgedOperation
    }

    fun evaluateOperation(operation: String): Long {
        var result: Long = operation.takeWhile { it.isDigit() }.toLong()
        (1 until operation.length).forEach { index ->
            when (operation[index]) {
                '+' -> result += operation.substring(index + 1).takeWhile { it.isDigit() }.toLong()
                '*' -> result *= operation.substring(index + 1).takeWhile { it.isDigit() }.toLong()
            }
        }
        return result
    }

    fun findMatchingParantheses(operation: String): List<Pair<Int, Int>> {
        val paranthesesPairs = mutableListOf<Pair<Int, Int>>()
        val openParanthesesIndexes = mutableListOf<Int>()
        operation.forEachIndexed { index, char ->
            when(char) {
                '(' -> openParanthesesIndexes.add(index)
                ')' -> {
                    val openIndex = openParanthesesIndexes.removeLast()
                    paranthesesPairs.add(openIndex to index)
                }
            }
        }
        return paranthesesPairs.sortedBy{ it.second - it.first }
    }
}