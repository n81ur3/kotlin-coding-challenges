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

        assertEquals(5, lobbyLayout.numberOfWhiteTiles())
        assertEquals(10, lobbyLayout.numberOfBlackTiles())
    }

    @Test
    fun solutionDay24Part02() {
        val input = file.readLines()
        val lobbyLayout = Day24LobbyLayout()
        lobbyLayout.parseInput(input)

        println("Number of black tiles: ${lobbyLayout.numberOfBlackTiles()}")
    }
}
