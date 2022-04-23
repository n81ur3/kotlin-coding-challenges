package adventofcode2020

class Day03TobogganTrajectory {
}

data class MapRow(val input: String) {
    var inputWithPath = input

    fun getTreePositions(): List<Int> {
        val treeIndexes = mutableListOf<Int>()
        input.forEachIndexed { index, letter -> if (letter == '#') treeIndexes.add(index) }
        return treeIndexes.toList()
    }

    fun isTreePosition(index: Int): Boolean {
        return getTreePositions().contains(index % 31)
    }
}

class LandMap(val mapRows: List<MapRow>) {

    /**
     * draws a map with the given width as min length
     */
    fun drawMapWithWidth(width: Int) {
        val singleRowLength = mapRows[0].input.length
        mapRows.forEach { row ->
            println(row.input.repeat(width / singleRowLength + 1))
        }
    }

    fun drawMapWithPath(right: Int, down: Int) {
        val singleRowLength = mapRows[0].input.length
        val repeater = (right * mapRows.count()) / singleRowLength + 1
        val extendedRows = mapRows.map { it.copy(input = it.input.repeat(repeater)) }
        var currentColumn = 0
        var hits = 0L
        (0 until mapRows.size step down).forEach { rowIndex ->
            var pathCharacter = 'O'
            if (extendedRows[rowIndex].input[currentColumn] == '#') {
                pathCharacter = 'X'
                hits++
            }
            extendedRows[rowIndex].inputWithPath =
                extendedRows[rowIndex].input.substring(0, currentColumn) + pathCharacter + extendedRows[rowIndex].input.substring(
                    currentColumn
                )
            currentColumn += right
        }
        extendedRows.forEach {
            println(it.inputWithPath)
        }

        println("Number of Trees hit during traversal: $hits")
    }
}