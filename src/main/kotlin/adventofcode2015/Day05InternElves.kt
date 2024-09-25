package adventofcode2015

class Day05InternElves

class StringChecker {

    fun checkStrings(input: List<String>) = input.count(::isNiceString)

    fun checkStringsPartTwo(input: List<String>) = input.count(::isNiceStringPartTwo)

    private fun isNiceString(candidate: String): Boolean {
        return with(candidate) { contains3Vowels(this) && containsDuplicateLetter(this) && doesNotContainPairs(this) }
    }

    private fun isNiceStringPartTwo(candidate: String): Boolean {
        return containsPairs(candidate) && containsRepeating(candidate)
    }

    private fun contains3Vowels(candidate: String) = candidate.count { it in vowels } > 2

    private fun containsDuplicateLetter(candidate: String): Boolean {
        candidate.windowed(2, 1).forEach { pair -> if (pair[0] == pair[1]) return true }
        return false
    }

    private fun doesNotContainPairs(candidate: String): Boolean {
        candidate.windowed(2, 1).forEach { pair -> if (pair in notAllowedPairs) return false }
        return true
    }

    private fun containsPairs(candidate: String): Boolean {
        return candidate.windowed(2, 1).groupBy { it }.filter { it.value.size > 1 }.isNotEmpty()
    }

    private fun containsRepeating(candidate: String): Boolean {
        val parts = candidate.windowed(3, 1)
        return parts.any { it[0] == it[2] && (it[1] != it[2])}
    }

    companion object {
        val vowels = listOf('a', 'e', 'i', 'o', 'u')
        val notAllowedPairs = listOf("ab", "cd", "pq", "xy")
    }
}