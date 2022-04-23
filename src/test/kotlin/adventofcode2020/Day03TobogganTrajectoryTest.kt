package adventofcode2020

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class Day03TobogganTrajectoryTest {

    lateinit private var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("day03.txt")
    }

    @Test
    fun testIndexRetrieval() {
        val mapRow =
            MapRow(".......#..#....#...#...#......#.......#..#....#...#...#......#.......#..#....#...#...#......#")
        println("Index: ${mapRow.getTreePositions()}")
    }

    @Test
    fun testTreeHits() {
        val mapRow = MapRow(".......#..#....#...#...#......#")
        val treePositions = listOf(7, 10, 15, 19, 23, 30, 38, 41, 46, 50, 54, 61, 69, 72, 77, 81, 85, 92)
        treePositions.forEach { Assertions.assertTrue(mapRow.isTreePosition(it)) }
    }

    @Test
    fun testTreeMisses() {
        val mapRow = MapRow(".......#..#....#...#...#......#")
        Assertions.assertFalse(mapRow.isTreePosition(8))
        Assertions.assertFalse(mapRow.isTreePosition(17))
        Assertions.assertFalse(mapRow.isTreePosition(77))
        Assertions.assertFalse(mapRow.isTreePosition(8887))
    }

    @Test
    fun solution03_1() {
        val lines = file.readLines().map { MapRow(it) }
        var currentColumn = 0
        val hits = lines.filter { row ->
            val result = row.isTreePosition(currentColumn)
            currentColumn += 3
            result
        }.count()

        println("Solution for Day03: $hits Tree hits")
    }

    @Test
    fun drawMap() {
        val lines = file.readLines().map { MapRow(it) }
        val landMap = LandMap(lines)
        //landMap.drawMapWithWidth(120)
        landMap.drawMapWithPath(3, 1)
    }

    @Test
    fun solution03_2() {
        val lines = file.readLines().map { MapRow(it) }
        val firstResult = getHitsByPath(lines, 1, 1)
        val secondResult = getHitsByPath(lines, 3, 1)
        val thirdResult = getHitsByPath(lines, 5, 1)
        val fourthResult = getHitsByPath(lines, 7, 1)
        val fifthResult = getHitsByPath(lines, 1, 2)

        val result = firstResult * secondResult * thirdResult * fourthResult * fifthResult

        println("first: $firstResult")
        println("second: $secondResult")
        println("third: $thirdResult")
        println("fourth: $fourthResult")
        println("fifth: $fifthResult")
        println("Solution for Day03 part two: $result")

    }

    private fun getHitsByPath(lines: List<MapRow>, stepRight: Int, stepDown: Int): Long {
        var hits = 0L
        var currentColumn = 0
        lines.mapIndexed { index, row ->
            if (index % stepDown == 0) {
                if (row.isTreePosition(currentColumn)) hits++
                currentColumn += stepRight
            }
        }
        return hits
    }
}