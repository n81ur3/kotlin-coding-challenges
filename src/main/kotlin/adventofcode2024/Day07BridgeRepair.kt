package adventofcode2024


data class BridgeEquation(
    val target: Long,
    val numbers: List<Long>
) {
    fun isValid(): Boolean {
        return getResults()
    }

    fun getResults(): Boolean {
        val results = mutableSetOf<Long>()
        val currentResults = mutableSetOf(numbers[0])
        numbers.drop(1).forEachIndexed { index, number ->
            results.addAll(currentResults)
            currentResults.clear()
            results.forEach {
                val addition = it + number
                val multiplication = it * number
                if (multiplication <= target) currentResults.add(multiplication)
                if (addition <= target) currentResults.add(addition)
            }
            results.clear()
        }
        if (currentResults.contains(target)) {
            return true
        }
        return false
    }
}

class BridgeRepairer(
    var input: List<String>
) {
    val equations: List<BridgeEquation>

    init {
        equations = input.map { parseEquation(it) }
    }

    fun numberOfValidEquations(): Long {
        return equations.filter { it.isValid() }.sumOf { it.target }
    }

    private fun parseEquation(equation: String): BridgeEquation {
        val parts = equation.split(" ")
        val result = parts[0].substringBefore(":").toLong()
        val numbers = parts.drop(1).map { it.toLong() }
        return BridgeEquation(result, numbers)
    }
}