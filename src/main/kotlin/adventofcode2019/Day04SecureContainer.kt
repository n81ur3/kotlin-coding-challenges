package adventofcode2019

class Day04SecureContainer

class PasswordEvaluator {

    fun containsDouble(value: Int) = value.toString().windowed(2, 1).any { it[0] == it[1] }

    fun containsDoubleStrict(value: Int): Boolean {
        val valueString = value.toString() + "-"
        var former = '-'
        val parts = valueString.windowed(3, 1)

        parts.forEach { part ->
            if ((former != part[0]) && part[0] == part[1] && part[1] != part[2]) {
                return true
            }
            former = part[0]
        }

        return false
    }

    fun isSorted(value: Int) = value.toString().windowed(2, 1).all { it[0] <= it[1] }

    fun isValidPassword(value: Int) = containsDouble(value) && isSorted(value)

    fun isAdvancedPassword(value: Int) = containsDoubleStrict(value) && isSorted(value)
}