package adventofcode2017

class Day13PacketScanners

data class Scanner(val depth: Int, val range: Int) {
    val severity: Int
        get() = depth * (range)

    fun caught(step: Int): Boolean {
        return (positionAtStep(step) == 0)
    }

    private fun positionAtStep(step: Int): Int {
        var moveDown = true
        var result = 0
        (0 until step).forEach {
            if (moveDown) result++
            else result--
            if (result == range - 1) moveDown = false
            if (result == 0) moveDown = true
        }
        return result
    }
}

class Firewall(config: List<String>) {
    val scanners: List<Scanner>
    val maxDepth: Int
        get() = scanners.maxOf { it.depth }

    init {
        scanners = config.map { parseConfigLine(it) }
    }

    private fun parseConfigLine(input: String): Scanner {
        val depth = input.substringBefore(":").toInt()
        val range = input.substringAfter(": ").toInt()
        return Scanner(depth, range)
    }

    fun calculateTotalSeverity() = (0..maxDepth).sumBy { severityAtStep(it) }

    private fun severityAtStep(step: Int): Int {
        val scanner = scanners.firstOrNull { it.depth == step } ?: return 0
        return if (scanner.caught(step)) scanner.severity else 0
    }
}