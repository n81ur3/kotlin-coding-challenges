package adventofcode2020

class Day07HandyHaversacks

data class Bag(val type: String, val color: String, val canContain: MutableMap<Bag, Int> = mutableMapOf())

object BagCreator {
    fun createBag(input: String): Bag {
        if (!input.contains(',')) {
            val parts = input.split(" ")
            val type =  parts[0]
            val color = parts[1]
            val count = parts[4]
            if (count == "no") return Bag(type, color)
            val otherType = parts[5]
            val otherColor = parts[6]
            return Bag(type, color, mutableMapOf(Bag(otherType, otherColor) to count.toInt()))
        } else {
            val subbags = input.split(".")
            val part = subbags[0].trim()
            val parts = part.split(" ")
            val type =  parts[0]
            val color = parts[1]
            val count = parts[4]
            val otherType = parts[5]
            val otherColor = parts[6]
            if (subbags.size == 1) {
                return Bag(type, color, mutableMapOf(Bag(otherType, otherColor) to count.toInt()))
            } else {
                return Bag(type, color, mutableMapOf(createBag(input.substringAfter(",").trim()) to count.toInt()))
            }
        }
    }
}