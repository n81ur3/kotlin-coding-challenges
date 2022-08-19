package adventofcode2017

class Day02CorrectionChecksum

data class SpreadsheetRow(val input: String) {
    val values: List<Int>

    val minValue: Int
        get() = values.minOrNull() ?: 0
    val maxValue: Int
        get() = values.maxOrNull() ?: 0
    val checksum: Int
        get() = maxValue - minValue
    val checksum2: Int
        get() = findMatchingPair().run { first / second }

    init {
        values = input.split(Regex("\\s+")).map { Integer.parseInt(it) }.sortedDescending()
    }

    private fun findMatchingPair(): Pair<Int, Int> {
        var result = 0 to 0
        (0 until (values.size / 2)).forEach { firstIndex ->
            ((firstIndex + 1) until values.size).forEach { secondIndex ->
                (checkForMatch(values[firstIndex], values[secondIndex]))?.let {
                    result = values[firstIndex] to values[secondIndex]
                }
            }
        }
        return result
    }

    private fun checkForMatch(first: Int, second: Int): Pair<Int, Int>? {
        return if (first % second == 0) first to second else null
    }
}

class ChecksumCalculator(val inputLines: List<String>) {
    var spreadsheetRows = inputLines.map { SpreadsheetRow(it) }

    fun calculateChecksum() = spreadsheetRows.sumBy { it.checksum }

    fun calculateChecksum2() = spreadsheetRows.sumBy { it.checksum2 }
}