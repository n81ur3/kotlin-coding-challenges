package adventofcode2015

class Day05InternElves

class StringChecker {

    fun checkStrings(input: List<String>) = input.count { isNiceString(it) }

    private fun isNiceString(candidate: String): Boolean {
        return with(candidate) { contains3Vowels(this) && containsDuplicateLetter(this) && doesNotContainPairs(this) }
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

    companion object {
        val vowels = listOf('a', 'e', 'i', 'o', 'u')
        val notAllowedPairs = listOf("ab", "cd", "pq", "xy")
    }
}