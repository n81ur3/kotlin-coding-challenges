package adventofcode2017

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day07RecursiveCircusTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2017/aoc2017_day07_input.txt")
    }


    @Test
    fun runSamplePart1() {
        val input = listOf(
            "pbga (66)",
            "xhth (57)",
            "ebii (61)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (68) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)"
        )
        val rcTower = RCTower.fromLines(input)

        assertEquals("tknk", rcTower.findRoot())
        rcTower.findRoot()
    }

    @Test
    fun runSamplePart2() {
        val input = listOf(
            "pbga (66)",
            "xhth (57)",
            "ebii (61)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (68) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)"
        )
        val rcTower = RCTower.fromLines(input)

        var result = rcTower.getDependantsWeightSum("xhth")
        assertEquals(57, result)
        println("Weight for xhth: $result")

        result = rcTower.getDependantsWeightSum("fwft")
        assertEquals(243, result)
        println("Weight for fwft: $result")

        result = rcTower.getDependantsWeightSum("tknk")
        assertEquals(778, result)
        println("Weight for tknk: $result")

        rcTower.checkChildsForEquality("pbga")

        rcTower.checkChildsForEquality("fwft")

        rcTower.checkChildsForEquality("ugml")

        rcTower.checkChildsForEquality("tknk")
    }

    @Test
    fun solution_part1() {
        val input = file.readLines()
        val rcTower = RCTower.fromLines(input)

        val root = rcTower.findRoot()
        assertEquals("dtacyn", root)
        println("Solution for day 07 part 1: $root")
    }

    @Test
    fun solution_part2() {
        val input = file.readLines()
        val rcTower = RCTower.fromLines(input)

        val result = rcTower.checkChildsForEqualWeight(rcTower.findRoot())
        assertEquals(521, result)
        println("Solution for day 07 part 2: $result")
    }
}