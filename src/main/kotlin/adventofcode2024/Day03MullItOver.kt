package adventofcode2024

class RegexCalculator(
    val input: List<String>
) {
    val mulRegex = Regex("mul\\(\\d+,\\d+\\)")

    fun getMulTotal(): Int {
        return input.fold(0) { acc, line -> acc + getMulResult(line) }
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