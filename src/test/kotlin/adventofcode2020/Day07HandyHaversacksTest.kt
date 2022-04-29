package adventofcode2020

import org.junit.Assert.*
import org.junit.jupiter.api.Test

class Day07HandyHaversacksTest {

    @Test
    fun parsingTest() {
        val line1 = "wavy magenta bags contain 2 light salmon bags."
        val line2 = "dark maroon bags contain 2 drab brown bags, 2 wavy tomato bags."

        val bag1 = BagCreator.createBag(line1)
        val bag2 = BagCreator.createBag(line2)

        println(bag1)
        println(bag2)
    }
}