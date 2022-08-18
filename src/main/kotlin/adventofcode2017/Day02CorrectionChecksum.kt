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

    init {
        values = input.split(Regex("\\s+")).map { Integer.parseInt(it) }
    }
}

class ChecksumCalculator(val inputLines: List<String>) {
    var spreadsheetRows = inputLines.map { SpreadsheetRow(it) }

    fun calculateChecksum(): Int {
        return spreadsheetRows.sumBy { it.checksum }
    }
}