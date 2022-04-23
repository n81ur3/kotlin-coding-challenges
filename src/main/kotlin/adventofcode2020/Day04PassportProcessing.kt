package adventofcode2020

class Day04PassportProcessing {
}

data class NorthPolePassport(
    val tokens: MutableMap<String, String> = mutableMapOf(),
    val requiredEntries: List<String> = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
) {
    fun addToken(key: String, value: String) {
        tokens.put(key, value)
    }

    fun containsAllRequiredTokens(): Boolean = tokens.keys.containsAll(requiredEntries)

    fun isValidDebug(): Boolean {
        val valid = containsAllRequiredTokens()
        if (!valid) {
            val missingTokens = requiredEntries - tokens.keys
            println("Missing tokens: ${missingTokens.sorted()}")
            println("Invalid entry: ${tokens.keys.sorted()}")
        }
        return valid
    }

    fun isValid(): Boolean {
        val results = mutableListOf<Boolean>()
        results.add(containsAllRequiredTokens())
        results.add(isValidInt("byr", 1920, 2002))
        results.add(isValidInt("iyr", 2010, 2020))
        results.add(isValidInt("eyr", 2020, 2030))
        results.add(isHeightValid())
        results.add(isHairColorValid())
        results.add(isEyeColorValid())
        results.add(isPassportIdValid())
        return results.all { it == true }
    }

    private fun isHeightValid(): Boolean {
        val value = tokens.get("hgt")
        value ?: return false
        if (!(value.endsWith("in") || value.endsWith("cm"))) return false
        if (value.endsWith("cm")) {
            if (!(isValidHeightInt(value.split("cm")[0], 150, 193))) return false
        } else {
            if (!(isValidHeightInt(value.split("in")[0], 59, 76))) return false
        }
        return true
    }

    private fun isValidHeightInt(value: String, minValue: Int, maxValue: Int): Boolean {
        val intValue = value.toIntOrNull() ?: return false
        return intValue in minValue..maxValue
    }

    private fun isHairColorValid(): Boolean {
        val value = tokens.get("hcl") ?: return false
        val regex = Regex("#[0-9a-f]{6}")
        return regex.matches(value)
    }

    private fun isEyeColorValid(): Boolean {
        val value = tokens.get("ecl") ?: return false
        return value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    }

    private fun isPassportIdValid(): Boolean {
        val value = tokens.get("pid") ?: return false
        val regex = Regex("[0-9]{9}")
        return regex.matches(value)
    }

    private fun isValidInt(key: String, minValue: Int, maxValue: Int): Boolean {
        val intValue = tokens.get(key)?.toIntOrNull() ?: return false
        return intValue in minValue..maxValue
    }
}
