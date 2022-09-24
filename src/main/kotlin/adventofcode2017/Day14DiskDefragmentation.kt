package adventofcode2017

class Day14DiskDefragmentation

class Defragmenter {
    val knotHash = KnotHash(256)
    val disc = mutableListOf<MutableList<Int>>()

    fun calculateCompleteGrid(keyString: String): String {
        return buildString {
            (0..127).forEach { row ->
                append(getBinaryHash(keyString + '-' + row))
                reset()
            }
        }
    }

    fun getBinaryHash(input: String): String {
        val hash = knotHash.hashAdvanced(input)
        return hexHashToBinary(hash)
    }

    private fun hexHashToBinary(hash: String): String {
        return buildString {
            hash.forEach {
                append(it.toHex())
            }
        }
    }

    fun buildDisc(keyString: String) {
        val grid = calculateCompleteGrid(keyString)
        grid.windowed(128, 128).forEach { row ->
            val rowBits = row.map { if (it == '1') 1 else 0 }.toMutableList()
            disc.add(rowBits)
        }
    }

    fun defrag(): Int {
        var groupCount = 0
        disc.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, cell ->
                if (cell == 1) {
                    groupCount++
                    markNeighbors(rowIndex, colIndex)
                }
            }
        }
        return groupCount
    }

    private fun markNeighbors(rowIndex: Int, colIndex: Int) {
        if (disc[rowIndex][colIndex] == 1) {
            disc[rowIndex][colIndex] = 0
            getNeighbors(rowIndex, colIndex).forEach { markNeighbors(it.first, it.second) }
        }
    }

    private fun getNeighbors(rowIndex: Int, colIndex: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        if (rowIndex > 0) result.add(rowIndex - 1 to colIndex)
        if (rowIndex < 127) result.add(rowIndex + 1 to colIndex)
        if (colIndex > 0) result.add(rowIndex to colIndex - 1)
        if (colIndex < 127) result.add(rowIndex to colIndex + 1)
        return result
    }

    fun reset() {
        knotHash.reset()
        'c'.toHex()
    }
}

private fun Char.toHex(): String {
    return when (this) {
        '0' -> "0000"
        '1' -> "0001"
        '2' -> "0010"
        '3' -> "0011"
        '4' -> "0100"
        '5' -> "0101"
        '6' -> "0110"
        '7' -> "0111"
        '8' -> "1000"
        '9' -> "1001"
        'a' -> "1010"
        'b' -> "1011"
        'c' -> "1100"
        'd' -> "1101"
        'e' -> "1110"
        else -> "1111"
    }
}
