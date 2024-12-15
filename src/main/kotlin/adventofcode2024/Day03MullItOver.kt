package adventofcode2024

class RegexCalculator(
    val input: List<String>
) {
    val mulRegex = Regex("mul\\(\\d+,\\d+\\)")
    val mulRegexOnOff = Regex("mul\\(\\d+,\\d+\\)|do[^n]|don")
    var mulActive = true

    fun getMulTotal(): Int {
        return input.fold(0) { acc, line -> acc + getMulResult(line) }
    }

    fun getMulTotalOnOff(): Int {
        var result = 0
        input.forEach { line ->
            val parts = mulRegexOnOff.findAll(line)
            parts.forEach { instruction ->
                when {
                    instruction.value.startsWith("don") -> mulActive = false
                    instruction.value.startsWith("do") -> mulActive = true
                    mulActive -> result += multiply(instruction.value)
                }
            }
        }
        return result
    }

    fun getMulResult(input: String): Int {
        return mulRegex.findAll(input).fold(0) { acc, matchResult -> acc + multiply(matchResult.value) }
    }

    private fun multiply(instruction: String): Int {
        val op1 = instruction.substring(4).substringBefore(',').toInt()
        val op2 = instruction.substringAfter(',').substringBefore(')').toInt()
        return op1 * op2
    }
}