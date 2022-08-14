package adventofcode2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day24LobbyLayoutTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day24_input.txt")
    }

    @Test
    fun parseMovesGetTilesLocation() {
        val lobbyLayout = Day24LobbyLayout()
        var result = lobbyLayout.parseMoves("esesw")
        assertTrue(result.x == 1.0f && result.y == -2.0f)

        result = lobbyLayout.parseMoves("wnwne")
        assertTrue(result.x == -1.0f && result.y == 2.0f)

        result = lobbyLayout.parseMoves("ewewew")
        assertTrue(result.x == 0.0f && result.y == 0.0f)

        result = lobbyLayout.parseMoves("nwwswee")
        println(result)
        assertTrue(result.x == 0.0f && result.y == 0.0f)

        result = lobbyLayout.parseMoves("swswswswswsw")
        assertTrue(result.x == -3.0f && result.y == -6.0f)

        result = lobbyLayout.parseMoves("esew")
        assertTrue(result.x == 0.5f && result.y == -1.0f)
    }

    @Test
    fun simulateSample() {
        val input = listOf(
            "sesenwnenenewseeswwswswwnenewsewsw",
            "neeenesenwnwwswnenewnwwsewnenwseswesw",
            "seswneswswsenwwnwse",
            "nwnwneseeswswnenewneswwnewseswneseene",
            "swweswneswnenwsewnwneneseenw",
            "eesenwseswswnenwswnwnwsewwnwsene",
            "sewnenenenesenwsewnenwwwse",
            "wenwwweseeeweswwwnwwe",
            "wsweesenenewnwwnwsenewsenwwsesesenwne",
            "neeswseenwwswnwswswnw",
            "nenwswwsewswnenenewsenwsenwnesesenew",
            "enewnwewneswsewnwswenweswnenwsenwsw",
            "sweneswneswneneenwnewenewwneswswnese",
            "swwesenesewenwneswnwwneseswwne",
            "enesenwswwswneneswsenwnewswseenwsese",
            "wnwnesenesenenwwnenwsewesewsesesew",
            "nenewswnwewswnenesenwnesewesw",
            "eneswnwswnwsenenwnwnwwseeswneewsenese",
            "neswnwewnwnwseenwseesewsenwsweewe",
            "wseweeenwnesenwwwswnew"
        )

        val lobbyLayout = Day24LobbyLayout()
        lobbyLayout.parseInput(input)

        assertEquals(10, lobbyLayout.numberOfBlackTiles())

        repeat(100) {
            lobbyLayout.calculateLayoutForNextDay()
            println("Day ${it + 1}: ${lobbyLayout.numberOfBlackTiles()}")
        }
//        assertEquals(15, lobbyLayout.numberOfBlackTiles())
    }

    @Test
    fun solutionDay24Part01() {
        val input = file.readLines()
        val lobbyLayout = Day24LobbyLayout()
        lobbyLayout.parseInput(input)
        lobbyLayout.buildFloor()

        println("Number of black tiles: ${lobbyLayout.numberOfBlackTiles()}")
    }

    @Test
    fun getBlackAndWhiteNeighborCounts() {
        val input = file.readLines()
        val lobbyLayout = Day24LobbyLayout()
        lobbyLayout.parseInput(input)
        lobbyLayout.buildFloor()

        println(
            "Black neighbor count: ${
                lobbyLayout.getColoredNeighborCountForTile(
                    Day24LobbyLayout.Tile(-10f, 10f),
                    Day24LobbyLayout.TileColor.BLACK
                )
            }"
        )
        println(
            "White neighbor count: ${
                lobbyLayout.getColoredNeighborCountForTile(
                    Day24LobbyLayout.Tile(-10f, 10f),
                    Day24LobbyLayout.TileColor.WHITE
                )
            }"
        )
    }

    @Test
    fun checkNeighborCountAtPosition() {
        val lobbyLayout = Day24LobbyLayout()
        lobbyLayout.buildFloor()

        lobbyLayout.flipTileAtPosition(-0.5f, 3f)
        lobbyLayout.flipTileAtPosition(0.5f, 3f)
        lobbyLayout.flipTileAtPosition(-1f, 2f)
        lobbyLayout.flipTileAtPosition(1f, 2f)
        lobbyLayout.flipTileAtPosition(-0.5f, 1f)
        lobbyLayout.flipTileAtPosition(0.5f, 1f)

        assertEquals(
            6,
            lobbyLayout.getColoredNeighborCountForTile(
                Day24LobbyLayout.Tile(0.0f, 2f),
                Day24LobbyLayout.TileColor.BLACK
            )
        )

        lobbyLayout.flipTileAtPosition(1f, 2f)

        assertEquals(
            1,
            lobbyLayout.getColoredNeighborCountForTile(
                Day24LobbyLayout.Tile(0.0f, 2f),
                Day24LobbyLayout.TileColor.WHITE
            )
        )

        lobbyLayout.flipTileAtPosition(1f, 2f)

        assertEquals(
            6,
            lobbyLayout.getColoredNeighborCountForTile(
                Day24LobbyLayout.Tile(0.0f, 2f),
                Day24LobbyLayout.TileColor.BLACK
            )
        )

        lobbyLayout.flipTileAtPosition(-0.5f, 3f)
        lobbyLayout.flipTileAtPosition(0.5f, 3f)
        lobbyLayout.flipTileAtPosition(-1f, 2f)
        lobbyLayout.flipTileAtPosition(1f, 2f)
        lobbyLayout.flipTileAtPosition(-0.5f, 1f)
        lobbyLayout.flipTileAtPosition(0.5f, 1f)

        assertEquals(
            6,
            lobbyLayout.getColoredNeighborCountForTile(
                Day24LobbyLayout.Tile(0.0f, 2f),
                Day24LobbyLayout.TileColor.WHITE
            )
        )
    }

    @Test
    fun solutionDay24Part02() {
        val input = file.readLines()
        val lobbyLayout = Day24LobbyLayout()
        lobbyLayout.parseInput(input)

        repeat(100) {
            lobbyLayout.calculateLayoutForNextDay()
        }

        println("Number of black tiles after 100 days: ${lobbyLayout.numberOfBlackTiles()}")
    }
}
