package adventofcode2020

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import utils.ResourceLoader
import java.io.File

class Day07HandyHaversacksTest {
    lateinit var file: File

    @BeforeEach
    fun setup() {
        file = ResourceLoader.getFile("aoc2020/day07_input.txt")
    }


    @Test
    fun parsingTest() {
        val line1 = "wavy magenta bags contain 2 light salmon bags."
        val line2 = "dark maroon bags contain 2 drab brown bags, 2 wavy tomato bags."
        val line3 = "posh purple bags contain 2 mirrored lavender bags, 4 light chartreuse bags, 3 shiny green bags."
        val line4 =
            "wavy chartreuse bags contain 3 striped purple bags, 3 vibrant blue bags, 2 mirrored fuchsia bags, 2 muted indigo bags."

        val bag1 = BagCreator.createBag(line1)
        val bag2 = BagCreator.createBag(line2)
        val bag3 = BagCreator.createBag(line3)
        val bag4 = BagCreator.createBag(line4)

        val result = bag4.containsBag("muted", "indigo", 0)
        println("Result: $result")
    }

    @Test
    fun solution07_1() {
        val lines = file.readLines()
        var matchingBags = lines.map { BagCreator.createBag(it) }.filter { it.containsBag("shiny", "gold", 0) }
        val resultingBags = matchingBags.toMutableSet()

        while (matchingBags.isNotEmpty()) {
            matchingBags = lines.filter { containsMatchingBag(BagCreator.createBag(it), matchingBags) }
                .map { BagCreator.createBag(it) }
            resultingBags.addAll(matchingBags)
        }
        println("Solution for day 07 part 1: ${resultingBags.size}")
    }

    private fun containsMatchingBag(candidate: Bag, matchingBags: List<Bag>): Boolean {
        matchingBags.forEach { if (candidate.containsBag(it.type, it.color, 0)) return true }
        return false
    }

    @Test
    fun countingTest() {
        val line3 = "posh purple bags contain 2 mirrored lavender bags, 4 light chartreuse bags, 3 shiny green bags."
        val bag3 = BagCreator.createBag(line3)
        val line4 =
            "wavy chartreuse bags contain 3 striped purple bags, 3 vibrant blue bags, 2 mirrored fuchsia bags, 2 muted indigo bags."
        val bag4 = BagCreator.createBag(line4)
        println("Counting bag3: ${bag3.getNumberOfContainingBags()}")
        println("Counting bag4: ${bag4.getNumberOfContainingBags()}")

    }

    @Test
    fun containigTest() {
        val line3 = "posh purple bags contain 2 mirrored lavender bags, 4 light chartreuse bags, 3 shiny green bags."
        val bag3 = BagCreator.createBag(line3)
        println("Containing: ${bag3.containingBags()}")
    }

    @Test
    fun handBagParsingTest() {
        val line1 =
            "wavy chartreuse bags contain 3 striped purple bags, 3 vibrant blue bags, 2 mirrored fuchsia bags, 2 muted indigo bags."

        val bag1 = HandBag.from(line1)
        println("Result: $bag1")
    }

    @Test
    fun solution07_2() {
        val lines = file.readLines()
        val result = getNumberOfBags(lines.find {it.startsWith("shiny gold")}!!, lines)
        println("Solution day07 part 2: ${result - 1}")
    }

    private fun getNumberOfBags(input: String, lines: List<String>): Int {
        val bag = HandBag.from(input)
        if (bag.canContain.isEmpty()) return 1

        var result = 1
        for (b in bag.canContain) {
            result += b.second * getNumberOfBags(lines.find {it.startsWith(b.first.name)}!!, lines)
        }
        return result
    }
}