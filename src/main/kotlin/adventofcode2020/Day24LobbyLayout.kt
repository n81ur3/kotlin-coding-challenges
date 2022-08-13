package adventofcode2020

class Day24LobbyLayout {
    val tiles = mutableSetOf<Tile>()

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
                    y++; x -= 0.5f ; index++
                }

                moves[index] == 'n' && moves[index + 1] == 'e' -> {
                    y++; x += 0.5f; index++
                }

            }
            index++
        }
        return Tile(x, y)
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