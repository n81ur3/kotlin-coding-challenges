package adventofcode2018

class Day11ChronalCharge

data class FuelCell(val x: Int, val y: Int, val powerLevel: Int) {

    companion object {
        fun forSerialNumber(x: Int, y: Int, serialNumber: Int): FuelCell =
            FuelCell(x, y, computePowerLevel(x, y, serialNumber))

        private fun computePowerLevel(x: Int, y: Int, serialNumber: Int): Int {
            var powerLevel = ((x + 10) * y + serialNumber) * (x + 10)
            if (powerLevel > 99) {
                powerLevel = (powerLevel / 100) % 10
            } else {
                powerLevel = 0
            }
            powerLevel -= 5
            return powerLevel
        }
    }
}

class PowerGrid(val serialNumber: Int) {
    val fuelCells: List<FuelCell>
    val squareScores = mutableMapOf<FuelCell, Int>()

    init {
        fuelCells = (1..300).flatMap { x ->
            (1..300).map { y ->
                FuelCell.forSerialNumber(x, y, serialNumber)
            }
        }
    }

    fun calculateScores(maxGridSize: Int) {
        (1..89397).forEach { index ->
            squareScores[fuelCells[index]] = computeScoreAt(index, maxGridSize)
        }
    }

    fun findLargestGrid(): String {
        var largestGrid = ""
        var maxScore = 0
        (1 .. 300).forEach { gridSize ->
            (0 until (300 - gridSize)).forEach { x ->
                (0 until (300 - gridSize)).forEach { y ->
                    val currentScore = computeScoreAt(y + (300 * x), gridSize)
                    if (currentScore > maxScore) {
                        maxScore = currentScore
                        largestGrid = "${x + 1},${y + 1},$gridSize"
                    }
                }
            }
        }
        return largestGrid
    }

    private fun computeScoreAt(index: Int, gridSize: Int): Int {
        var result = 0

        (0 until gridSize).forEach { y ->
            (300 * y until (300 * y + gridSize)).forEach { result += fuelCells[index + it].powerLevel }
        }

        return result
    }

    fun getHighestPowerLevelCoordinates(): String {
        val highScoreCell = squareScores.maxBy { it.value }
        return "${highScoreCell.key.x},${highScoreCell.key.y}"
    }
}