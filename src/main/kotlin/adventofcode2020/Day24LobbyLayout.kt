package adventofcode2020

class Day24LobbyLayout {
    var tiles = mutableSetOf<Tile>()

    val neighborOffsets = listOf(
        Pair(-0.5f, -1f),
        Pair(0.5f, -1f),
        Pair(-1f, 0f),
        Pair(1f, 0f),
        Pair(-0.5f, 1f),
        Pair(0.5f, 1f)
    )

    fun buildFloor() {
        (-60..60).forEach { x ->
            (-60..60 step 2).forEach { y ->
                tiles.add(Tile(x.toFloat(), y.toFloat()))
            }
        }
        (-60..60).forEach { x ->
            (-59..61 step 2).forEach { y ->
                tiles.add(Tile(x.toFloat() + 0.5f, y.toFloat()))
            }
        }
    }

    fun parseMoves(moves: String): Tile {
        var x = 0.0f
        var y = 0.0f

        var index = 0
        while (index < moves.length) {
            when {
                moves[index] == 'e' -> x++
                moves[index] == 'w' -> x--
                moves[index] == 's' && moves[index + 1] == 'e' -> {
                    y--; x += 0.5f; index++
                }

                moves[index] == 's' && moves[index + 1] == 'w' -> {
                    y--; x -= 0.5f; index++
                }

                moves[index] == 'n' && moves[index + 1] == 'w' -> {
                    y++; x -= 0.5f; index++
                }

                moves[index] == 'n' && moves[index + 1] == 'e' -> {
                    y++; x += 0.5f; index++
                }

            }
            index++
        }
        return Tile(x, y)
    }

    fun getColoredNeighborCountForTile(tile: Tile, color: TileColor): Int {
        var neighborCount = 0

        neighborOffsets.forEach { (nX, nY) ->
            val neighbor = tiles.find { it.x == (tile.x + nX) && it.y == (tile.y + nY) }
            neighbor?.let { candidate ->
                if (candidate.color == color) neighborCount++
            }
        }

        return neighborCount
    }

    fun parseInput(input: List<String>) {
        var currentTile: Tile
        input.forEach {
            currentTile = parseMoves(it)
            currentTile.flip()
            if (currentTile in tiles) {
                tiles.find { currentTile == it }?.flip()
            } else {
                tiles.add(currentTile)
            }
        }
        addBorderTiles()
    }

    fun flipTileAtPosition(x: Float, y: Float) = tiles.find { it.x == x && it.y == y }?.flip()

    fun calculateLayoutForNextDay() {
        tiles = tiles.map { calculateNextColoredTile(it) }.toMutableSet()
        addBorderTiles()
    }

    private fun calculateNextColoredTile(tile: Tile): Tile {
        val blackNeighbors = getColoredNeighborCountForTile(tile, TileColor.BLACK)
        if (tile.color == TileColor.BLACK && (blackNeighbors == 0 || blackNeighbors > 2)) {
            return Tile(tile.x, tile.y)
        } else if (tile.color == TileColor.WHITE && blackNeighbors == 2) {
            return Tile(tile.x, tile.y, TileColor.BLACK)
        } else return tile
    }

    private fun addBorderTiles() {
        tiles.filter { it.color == TileColor.BLACK }.forEach { addVacantNeighbors(it) }
    }

    private fun addVacantNeighbors(tile: Tile) {
        neighborOffsets.forEach { (nX, nY) ->
            tiles.find { it.x == (tile.x + nX) && it.y == (tile.y + nY) } ?: tiles.add(Tile((tile.x + nX), (tile.y + nY)))
        }
    }

    fun numberOfWhiteTiles(): Int {
        return tiles.filter { it.color == TileColor.WHITE }.count()
    }

    fun numberOfBlackTiles(): Int {
        return tiles.filter { it.color == TileColor.BLACK }.count()
    }

    data class Tile(var x: Float = 0.0f, var y: Float = 0.0f, var color: TileColor = TileColor.WHITE) {
        fun flip() {
            color = if (color == TileColor.WHITE) TileColor.BLACK else TileColor.WHITE
        }
    }

    enum class TileColor {
        WHITE,
        BLACK
    }
}