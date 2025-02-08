package adventofcode2024

data class ClawMachine(
    val aX: Int,
    val aY: Int,
    val bX: Int,
    val bY: Int,
    val prizeX: Int,
    val prizeY: Int
) {

    fun findPrize(): Int {
        var result = Int.MAX_VALUE

        (0..100).forEach { a ->
            (0..100).forEach { b ->
                if (a * aX + b * bX == prizeX && a * aY + b * bY == prizeY) {
                    val costs = 3 * a + b
                    if (costs < result) result = costs
                }
            }
        }

        return if (result < Int.MAX_VALUE) result else 0
    }

    fun findAdvancedPrize(): Long {
        val prizeXY = (prizeX + 10000000000000) * aY
        val prizeYX = (prizeY + 10000000000000) * aX
        val prizeDiff = Math.abs(prizeXY - prizeYX)
        val diff = Math.abs((bX * aY) - (bY * aX))
        val b = (1.0 * prizeDiff) / diff
        val a = ((prizeX + 10000000000000.0) - (bX * b)) / aX

        if (a % 1.0 == 0.0 && b % 1.0 == 0.0) {
            return (3 * a + b).toLong()
        } else {
            return 0L
        }
    }
}

class ClawArcade(val input: List<String>) {
    val clawMachines: List<ClawMachine>

    init {
        clawMachines = input.windowed(4, 4, true).map { buildClawMachine(it.take(3)) }
    }

    private fun buildClawMachine(input: List<String>): ClawMachine {
        val aX = input[0].substringAfter('+').substringBefore(',').toInt()
        val aY = input[0].substringAfterLast('+').toInt()
        val bX = input[1].substringAfter('+').substringBefore(',').toInt()
        val bY = input[1].substringAfterLast('+').toInt()
        val prizeX = input[2].substringAfter('=').substringBefore(',').toInt()
        val prizeY = input[2].substringAfterLast('=').toInt()
        return ClawMachine(aX, aY, bX, bY, prizeX, prizeY)
    }

    fun calculateFewestTokens(): Int {
        return clawMachines.sumOf { it.findPrize() }
    }

    fun calculateFewestTokensAdvanced(): Long {
        return clawMachines.sumOf { it.findAdvancedPrize() }
    }
}