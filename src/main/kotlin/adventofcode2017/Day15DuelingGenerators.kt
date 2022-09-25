package adventofcode2017

class Day15DuelingGenerators

data class Generator(
    val initValue: Long,
    val factor: Long,
    val filter: Boolean = false,
    val filterModulus: Long = 0L
) {
    var currentValue = initValue
    val modulus = 2147483647

    fun next(): Long {
        if (filter) {
            while (true) {
                currentValue = (currentValue * factor) % modulus
                if ((currentValue % filterModulus) == 0L) return currentValue
            }
        } else {
            currentValue = (currentValue * factor) % modulus
            return currentValue
        }
    }
}

object Judge {
    fun equals(valueA: Long, valueB: Long): Boolean {
        val bitsA = valueA.toString(2).padStart(32).drop(16)
        val bitsB = valueB.toString(2).padStart(32).drop(16)
        return bitsA == bitsB
    }
}