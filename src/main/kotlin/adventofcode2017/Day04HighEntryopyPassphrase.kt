package adventofcode2017

class Day04HighEntryopyPassphrase

data class Passphrase(val passphrase: String) {
    val words: List<String> = passphrase.split(" ")

    fun isValid() = words.groupBy { it }.none { it.value.size > 1 }

    fun isSecure(): Boolean {
        val transformedWords = words.map { word -> word.split("").sorted().joinToString("") }
        return transformedWords.groupBy { it }.none { it.value.size > 1 }
    }
}