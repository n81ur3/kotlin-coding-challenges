package adventofcode2020

class Day02PasswordPhilosophy

data class PasswordEntry(val line: String) {
    val lowerLimit: Int
    val upperLimit: Int
    val character: Char
    val entry: String

    init {
        val parts = line.split(" ")
        require(parts.size == 3)
        lowerLimit = parts[0].split("-")[0].toInt()
        upperLimit = parts[0].split("-")[1].toInt()
        character = parts[1].split(":")[0][0]
        entry = parts[2]
    }

    fun isValid(): Boolean {
        val characterCount = entry.filter { it == character }.count()
        return characterCount in lowerLimit..upperLimit
    }

    fun isValidPartTwo(): Boolean = (entry[lowerLimit - 1] == character) xor (entry[upperLimit - 1] == character)

    override fun toString(): String {
        return "PasswordEntry(lowerLimit=$lowerLimit, upperLimit=$upperLimit, character=$character, entry='$entry')"
    }
}