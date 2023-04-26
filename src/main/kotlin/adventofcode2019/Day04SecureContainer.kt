package adventofcode2019

class Day04SecureContainer

class PasswordEvaluator {

    fun containsDouble(value: Int) = value.toString().windowed(2, 1).any { it[0] == it[1] }

    fun isSorted(value: Int) = value.toString().windowed(2, 1).all { it[0] <= it[1] }

    fun isValidPassword(value: Int) = containsDouble(value) && isSorted(value)
}