package adventofcode2017

class Day15DuelingGenerators

data class Generator(val initValue: Long, val factor: Long) {
    var currentValue = initValue
    val modulus = 2147483647

    fun next(): Long {
        currentValue = (currentValue * factor) % modulus
        return currentValue
    }
}

object Judge {
    fun equals(valueA: Long, valueB: Long): Boolean {
        val bitsA = valueA.toString(2).padStart(32).drop(16)
        val bitsB = valueB.toString(2).padStart(32).drop(16)
        return bitsA == bitsB
    }
}