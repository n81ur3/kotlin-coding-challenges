package adventofcode2017

class Day13PacketScanners

data class Scanner(val depth: Int, val range: Int) {
    val severity: Int
        get() = depth * (range)

    fun caught(step: Int) = (step % ((range - 1) * 2)) == 0
}

class Firewall(config: List<String>) {
    private val scanners: List<Scanner>
    private val maxDepth: Int
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

    fun findBreakthroughDelay(): Int {
        var delay = 0
        while (true) {
            if (checkScannersWithDelay(delay)) return delay
            delay++
        }
    }

    private fun checkScannersWithDelay(delay: Int): Boolean {
        return (0..maxDepth).none { caughtAtStep(it, it + delay) }
    }

    private fun caughtAtStep(depth: Int, delay: Int): Boolean {
        val scanner = scanners.firstOrNull { it.depth == depth } ?: return false
        return scanner.caught(delay)
    }
}