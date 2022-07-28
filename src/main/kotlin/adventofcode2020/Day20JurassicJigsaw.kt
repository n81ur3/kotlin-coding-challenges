package adventofcode2020

class Day20JurassicJigsaw

fun List<String>.rotated(): List<String> {
    val rotatedLines = mutableListOf<String>()
    val sb = StringBuilder()
    val cols = first().length
    val rows = size - 1
    (0 until cols).forEach { col ->
        (rows downTo 0).forEach { row ->
            sb.append(this[row][col])
        }
        rotatedLines.add(sb.toString())
        sb.clear()
    }
    return rotatedLines
}

fun List<String>.flip(): List<String> = reversed()

class Tile(val id: Int, var lines: MutableList<String>) {
    var rotation = 0
    var flipped = false

    fun flip() {
        lines = lines.reversed().toMutableList()
        flipped = !flipped
    }

    fun rotate() {
        lines = lines.rotated().toMutableList()
        rotation = Math.floorMod(rotation + 90, 360)
    }

    val topLine
        get() = lines.first()
    val bottomLine
        get() = lines.last()
    val leftLine: String
        get() = lines.map { it.first() }.joinToString(separator = "")
    val rightLine: String
        get() = lines.map { it.last() }.joinToString(separator = "")

    fun matchTop(otherTile: Tile): Tile? {
        if (topLine == otherTile.bottomLine) return otherTile
        val other = otherTile.copyTile()

        repeat(2) {
            if (topLine == other.bottomLine) return other
            (0 until 4).forEach {
                other.rotate()
                if (topLine == other.bottomLine) return other
            }
            other.flip()
        }

        return null
    }

    fun matchLeft(otherTile: Tile): Tile? {
        if (leftLine == otherTile.rightLine) return otherTile
        val other = otherTile.copyTile()

        repeat(2) {
            if (leftLine == other.rightLine) return other
            (0 until 4).forEach {
                other.rotate()
                if (leftLine == other.rightLine) return other
            }
            other.flip()
        }

        return null
    }

    fun matchRight(otherTile: Tile): Tile? {
        if (rightLine == otherTile.leftLine) return otherTile
        val other = otherTile.copyTile()

        repeat(2) {
            if (rightLine == other.leftLine) return other
            (0 until 4).forEach {
                other.rotate()
                if (rightLine == other.leftLine) return other
            }
            other.flip()
        }

        return null
    }

    fun matchBottom(otherTile: Tile): Tile? {
        if (bottomLine == otherTile.topLine) return otherTile
        val other = otherTile.copyTile()

        repeat(2) {
            if (bottomLine == other.topLine) return other
            (0 until 4).forEach {
                other.rotate()
                if (bottomLine == other.topLine) return other
            }
            other.flip()
        }

        return null
    }

    fun toStringListWithoutBorders(): List<String> =
        lines.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }

    companion object {

        fun fromLines(input: List<String>): Tile {
            val id = input[0].substringAfter(" ").substringBefore(":").toInt()
            return Tile(id, input.drop(1).toMutableList())
        }
    }

    fun copyTile() = Tile(id, lines.toMutableList())

    override fun toString(): String {
        val sb = StringBuilder("Tile $id flipped:$flipped rotation:$rotation:\n")
        lines.forEach { sb.append(it + "\n") }
        return sb.toString()
    }
}

class Jigsaw(val tiles: MutableMap<Int, Tile>) {
    var alreadyFoundTiles = mutableListOf<Tile>()

    companion object {
        private const val INPUT_TILE_SIZE = 12
        fun fromLines(initialLines: List<String>): Jigsaw {
            val tiles = mutableMapOf<Int, Tile>()
            initialLines.chunked(INPUT_TILE_SIZE) { chunk -> chunk.dropLast(1) }.forEach { lines ->
                val tile = Tile.fromLines(lines)
                tiles.put(tile.id, tile)
            }
            return Jigsaw(tiles)
        }
    }

    fun findCornerTiles(): List<Tile> {
        val result = mutableListOf<Tile>()
        var numberOfMatchesBottom: Int
        var numberOfMatchesRight: Int
        var numberOfMatchesLeft: Int
        var numberOfMatchesTop: Int

        for (tile in tiles) {
            val tileCopy = tile.value.copyTile()
            val otherTiles = tiles.filterKeys { key -> key != tile.key }
            numberOfMatchesBottom = numberOfMatchesBottom(otherTiles, tileCopy)
            numberOfMatchesRight = numberOfMatchesRight(otherTiles, tileCopy)
            numberOfMatchesLeft = numberOfMatchesLeft(otherTiles, tileCopy)
            numberOfMatchesTop = numberOfMatchesTop(otherTiles, tileCopy)

            if (numberOfMatchesBottom == 1 && numberOfMatchesRight == 1 && numberOfMatchesLeft == 0 && numberOfMatchesTop == 0) {
                result.add(tileCopy)
            }

            for (x in 0..3) {
                tileCopy.rotate()
                numberOfMatchesBottom = numberOfMatchesBottom(otherTiles, tileCopy)
                numberOfMatchesRight = numberOfMatchesRight(otherTiles, tileCopy)
                numberOfMatchesLeft = numberOfMatchesLeft(otherTiles, tileCopy)
                numberOfMatchesTop = numberOfMatchesTop(otherTiles, tileCopy)
                if (numberOfMatchesBottom == 1 && numberOfMatchesRight == 1 && numberOfMatchesLeft == 0 && numberOfMatchesTop == 0) {
                    result.add(tileCopy)
                    break
                }
            }
        }

        return result
    }

    fun findTopLine(): List<Tile> {
        val result = mutableListOf<Tile>()
        val cornerTiles = findCornerTiles()
        val topLeft = cornerTiles.first()
        result.add(topLeft)
        var currentTile = topLeft
        while (currentTile.id !in cornerTiles.drop(1).map { it.id }) {
            currentTile = findTileToRight(currentTile)
            result.add(currentTile)
        }
        return result
    }

    fun findTileToRight(tile: Tile): Tile {
        val otherTiles = tiles.filterKeys { key -> key != tile.id }.values
        return otherTiles.mapNotNull { otherTile -> tile.matchRight(otherTile) }.first()
    }

    fun findTileToBottom(tile: Tile): Tile {
        val otherTiles = tiles.filterKeys { key -> key != tile.id }.values
        return otherTiles.mapNotNull { otherTile -> tile.matchBottom(otherTile) }.first()
    }

    fun assemble() {
        val topLine = findTopLine()
        alreadyFoundTiles.addAll(topLine)
        val numberOfHorizontalTiles = topLine.size
        var currentTile = alreadyFoundTiles[alreadyFoundTiles.size - numberOfHorizontalTiles]
        while (alreadyFoundTiles.size != tiles.size) {
            alreadyFoundTiles.add(findTileToBottom(currentTile))
            currentTile = alreadyFoundTiles[alreadyFoundTiles.size - numberOfHorizontalTiles]
        }
    }

    fun getJigsawAsString(): String {
        val sb = StringBuilder()
        val resultSb = StringBuilder()
        val topLine = findTopLine()
        val numberOfHorizontalTiles = topLine.size
        alreadyFoundTiles.chunked(numberOfHorizontalTiles).forEach { tilesLines ->
            (0..9).forEach { row ->
                tilesLines.forEach { tile ->
                    sb.append(tile.lines[row])
                    sb.append(" ")
                }
                sb.append("\n")
            }
            resultSb.append(sb.toString())
            sb.clear()
        }
        return resultSb.toString()
    }

    fun toListOfStringsWithoutBorders(): List<String> {
        val result = mutableListOf<String>()
        val tilesWithoutBorders = alreadyFoundTiles.map { tile -> tile.toStringListWithoutBorders() }
        val topLine = findTopLine()
        val numberOfHorizontalTiles = topLine.size
        val sb = StringBuilder()
        tilesWithoutBorders.chunked(numberOfHorizontalTiles) { tilesLine ->
            (0..7).forEach { row ->
                tilesLine.forEach { tile ->
                    sb.append(tile[row])
                }
                result.add(sb.toString())
                sb.clear()
            }
        }
        return result
    }

    fun findMonsters(): Int {
        var puzzleLines = toListOfStringsWithoutBorders()
        var totalMonstersCount = 0

        repeat(7) { counter ->
            totalMonstersCount += searchForMonsters(puzzleLines)
            puzzleLines = puzzleLines.rotated()
            if (counter == 3) puzzleLines = puzzleLines.flip()
        }

        totalMonstersCount += searchForMonsters(puzzleLines)
        printBorderlessPuzzle(puzzleLines)
        println("Number of Monsters found: $totalMonstersCount")
        return totalMonstersCount
    }

    private fun searchForMonsters(lines: List<String>): Int {
        var monsterCount = 0
        val maxCol = lines[0].length - 19

        val currentLines = mutableListOf<String>()
        lines.windowed(3, 1).forEach { rows ->
            (0 until maxCol).forEach { col ->
                currentLines.add(rows[0].substring(col, col + 20))
                currentLines.add(rows[1].substring(col, col + 20))
                currentLines.add(rows[2].substring(col, col + 20))
                if (containsMonster(currentLines)) monsterCount++
                currentLines.clear()
            }
        }
        return monsterCount
    }

    fun containsMonster(piece: List<String>): Boolean {
        // Monster Pattern:
        //   01234567890123456789
        // 0                   #
        // 1 #    ##    ##    ###
        // 2  #  #  #  #  #  #
        // line 0: 18
        // line 1: 0, 5, 6, 11, 12, 17, 18, 19
        // line 2: 1, 4, 7, 10, 13, 16

        val line0Count = listOf(18).filter { index -> piece[0][index] == '#' }.count()
        val line1Count =
            listOf(0, 5, 6, 11, 12, 17, 18, 19).filter { index -> piece[1][index] == '#' }.count()
        val line2Count =
            listOf(1, 4, 7, 10, 13, 16).filter { index -> piece[2][index] == '#' }.count()

        return (line0Count + line1Count + line2Count) == 15
    }

    fun printJigsaw() = println(getJigsawAsString())

    private fun printBorderlessPuzzle(puzzleLines: List<String>) = puzzleLines.forEach { println(it) }

    private fun numberOfMatchesTop(
        otherTiles: Map<Int, Tile>,
        tileCopy: Tile
    ) = otherTiles.values.mapNotNull { otherTile -> tileCopy.matchTop(otherTile) }.count()

    private fun numberOfMatchesBottom(
        otherTiles: Map<Int, Tile>,
        tileCopy: Tile
    ) = otherTiles.values.mapNotNull { otherTile -> tileCopy.matchBottom(otherTile) }.count()

    private fun numberOfMatchesRight(
        otherTiles: Map<Int, Tile>,
        tileCopy: Tile
    ) = otherTiles.values.mapNotNull { otherTile -> tileCopy.matchRight(otherTile) }.count()

    private fun numberOfMatchesLeft(
        otherTiles: Map<Int, Tile>,
        tileCopy: Tile
    ) = otherTiles.values.mapNotNull { otherTile -> tileCopy.matchLeft(otherTile) }.count()

    fun totalNumberOfHashes(): Int {
        val linesWithoutBorder = toListOfStringsWithoutBorders()
        return linesWithoutBorder.sumBy { line -> line.filter { char -> char == '#' }.length }
    }

}