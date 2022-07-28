package adventofcode2020

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day20JurassicJigsawTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day20_input.txt")
    }

    @Test
    fun createTileFromSampleLines() {
        val input = listOf(
            "Tile 1171:",
            "..##.#..#.",
            "##..#.....",
            "#...##..#.",
            "####.#...#",
            "##...#.###",
            ".#.#.#..##",
            "..#....#..",
            "###...#.#.",
            "..###..###"
        )

        val tile = Tile.fromLines(input)
        println(tile)

        println("Top Line: ${tile.topLine}")
        println("Bottom Line: ${tile.bottomLine}")
        println("Left Line: ${tile.leftLine}")
        println("Right Line: ${tile.rightLine}")
    }

    @Test
    fun flipAndRotateTile() {
        val inputTile = listOf(
            "Tile 1951:",
            "#.##...##.",
            "#.####...#",
            ".....#..##",
            "#...######",
            ".##.#....#",
            ".###.#####",
            "###.##.##.",
            ".###....#.",
            "..#.#..#.#",
            "#...##.#.."
        )

        val tile = Tile.fromLines(inputTile)

        println("Original:\n$tile")
        tile.flip()
        println("First Flip:\n$tile")
        tile.flip()
        println("Second Flip:\n$tile")

        tile.rotate()
        println("First Rotation:\n$tile")
        tile.rotate()
        println("Second rotation:\n$tile")
        tile.rotate()
        println("Third rotation:\n$tile")
        tile.rotate()
        println("Fourth rotation:\n$tile")
    }

    @Test
    fun tileToStringWithoutBorders() {
        val inputTile = listOf(
            "Tile 1951:",
            "#.##...##.",
            "#.####...#",
            ".....#..##",
            "#...######",
            ".##.#....#",
            ".###.#####",
            "###.##.##.",
            ".###....#.",
            "..#.#..#.#",
            "#...##.#.."
        )

        val tile = Tile.fromLines(inputTile)

        println(tile.toStringListWithoutBorders().joinToString("\n"))
    }

    @Test
    fun tileMatchesTop() {
        val inputBottomTile = listOf(
            "Tile 2729:",
            "...#.#.#.#",
            "####.#....",
            "..#.#.....",
            "....#..#.#",
            ".##..##.#.",
            ".#.####...",
            "####.#.#..",
            "##.####...",
            "##..#.##..",
            "#.##...##."
        )

        val inputTopTile1 = listOf(
            "Tile 1951:",
            "#.##...##.",
            "#.####...#",
            ".....#..##",
            "#...######",
            ".##.#....#",
            ".###.#####",
            "###.##.##.",
            ".###....#.",
            "..#.#..#.#",
            "#...##.#.."
        )

        val inputTopTile2 = listOf(
            "Tile 1988:",
            "#########.",
            "#.####...#",
            ".....#..##",
            "#...#####.",
            ".##.#.....",
            ".###.####.",
            "###.##.###",
            ".###....##",
            "..#.#..#..",
            "#...##.#.#"
        )

        val bottomTile = Tile.fromLines(inputBottomTile)
        val topTile1 = Tile.fromLines(inputTopTile1)
        val topTile2 = Tile.fromLines(inputTopTile2)

        bottomTile.flip()

        println("Tile matches Top1: ${bottomTile.matchTop(topTile1)}")
        println("Tile matches Top1: ${bottomTile.matchTop(topTile2)}")

    }

    @Test
    fun readJigsawTilesFromFile() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        println(jigsaw.tiles.get(3331))
    }

    @Test
    fun findIdsOfCornerTiles() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        val cornerTiles = jigsaw.findCornerTiles()
        println("Ids of corner tiles: ${cornerTiles.map { tile -> tile.id }.toList()}")
    }

    @Test
    fun solutionDay20Part01() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        val cornerTiles = jigsaw.findCornerTiles()
        println("Corner tiles:\n $cornerTiles")

        println(
            "Sum of the four corner tile-ids = ${
                cornerTiles.map { tile -> tile.id.toLong() }.reduce { acc, id -> acc * id }
            }"
        )
    }

    @Test
    fun findTileMatchesRightToTopLeftTile() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        val topLeftTile = jigsaw.findCornerTiles().first()
        println("Top Left: $topLeftTile")

        val tileToRight = jigsaw.findTileToRight(topLeftTile)
        println()
        println("Tile to Right: $tileToRight")
    }

    @Test
    fun findTopLineOfJigsaw() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        jigsaw.findTopLine()
    }

    @Test
    fun assembleAndPrintJigsaw() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        jigsaw.assemble()
        jigsaw.printJigsaw()
    }

    @Test
    fun jigsawAssembledWithoutBorders() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        jigsaw.assemble()
        jigsaw.printJigsaw()

        for (line in jigsaw.toListOfStringsWithoutBorders()) {
            println(line)
        }
    }

    @Test
    fun findMonsterInPiece() {
        val lines = listOf(
//            "oooooooooooooooooo#o",
//            "#oooo##oooo##oooo###",
//            "o#oo#oo#oo#oo#oo#ooo"
            "......#..#.#.....##.",
            "#...#####.###..#####",
            ".#.##.##..#.###.##.."
        )

        val jigsaw = Jigsaw(mutableMapOf())
        assertTrue(jigsaw.containsMonster(lines))
    }

    @Test
    fun getMonstersCountInPuzzle() {
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        jigsaw.assemble()
        assertEquals(16, jigsaw.findMonsters())
    }

    @Test
    fun solutionDay20Part02() {
        val hashCountInMonstersPattern = 15
        val lines = file.readLines()
        val jigsaw = Jigsaw.fromLines(lines)
        jigsaw.assemble()
        val numberOfMonsters = jigsaw.findMonsters()
        val monsterHashes = numberOfMonsters * hashCountInMonstersPattern
        val totalHashesInAssembledJigsaw = jigsaw.totalNumberOfHashes()
        println("Total number of hashes in jigsaw: $totalHashesInAssembledJigsaw")
        println("Number of hashes in monsters: $monsterHashes")

        println("Roughness of water: ${totalHashesInAssembledJigsaw - monsterHashes}")
    }
}