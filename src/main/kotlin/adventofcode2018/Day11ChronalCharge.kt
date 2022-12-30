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

    fun calculateScores() {
        (1..89397).forEach { index ->
            squareScores[fuelCells[index]] = computeScoreAt(index)
        }
    }

    private fun computeScoreAt(index: Int): Int {
        var result = fuelCells[index].powerLevel
        (1..2).forEach { result += fuelCells[index + it].powerLevel }
        (300..302).forEach { result += fuelCells[index + it].powerLevel }
        (600..602).forEach { result += fuelCells[index + it].powerLevel }
        return result
    }

    fun getHighestPowerLevelCoordinates(): String {
        val highScoreCell = squareScores.maxBy { it.value }
        return "${highScoreCell.key.x},${highScoreCell.key.y}"
    }
}