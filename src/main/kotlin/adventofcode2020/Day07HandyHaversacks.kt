package adventofcode2020

class Day07HandyHaversacks

data class Bag(val type: String, val color: String, val canContain: Pair<Bag, Int>?) {
    val bagName: String
        get() = type + " " + color

    fun containsBag(type: String, color: String, count: Int, recursive: Boolean = false): Boolean {
        if (recursive) {
            if (this.type == type && this.color == color) return true
        }
        val result = false
        canContain ?: return false
        val (containingBag, containingCount) = canContain
        if (containingBag.type == type && containingBag.color == color) return true
        else if (containingBag.canContain != null) return containingBag.canContain.first.containsBag(
            type,
            color,
            count,
            recursive = true
        )

        return result
    }

    fun containingBags(): List<Pair<Bag, Int>> {
        val result = mutableListOf<Pair<Bag, Int>>()
        var currentPair = canContain
        while (currentPair != null) {
            result.add(Bag(currentPair.first.type, currentPair.first.color, null) to currentPair.second)
            currentPair = currentPair.first.canContain
        }
        return result
    }

    fun getNumberOfContainingBags(): Int {
        canContain ?: return 1
        return canContain.second + canContain.first.getNumberOfContainingBags()
    }
}

object BagCreator {
    fun createBag(input: String): Bag {
        if (!input.contains(',')) {
            val parts = input.split(" ")
            val type = parts[0]
            val color = parts[1]
            if (parts.size == 3 || parts[4] == "no") return Bag(type, color, null)
            val otherType = parts[5]
            val otherColor = parts[6]
            return Bag(type, color, Pair(Bag(otherType, otherColor, null), parts[4].toInt()))
        } else {
            val subbags = input.split(".")
            val part = subbags[0].trim()
            val parts = part.split(" ")
            val type = parts[0]
            val color = parts[1]
            val count = parts[4]
            val otherType = parts[5]
            val otherColor = parts[6]
            if (subbags.size == 1) {
                return Bag(type, color, Pair(Bag(otherType, otherColor, null), count.toInt()))
            } else {
                val remaining = input.substringAfter(",").trim()
                val (remainingCount, remainingBag) = createBagWithCount(remaining)
                return Bag(
                    type,
                    color,
                    Pair(
                        Bag(
                            otherType,
                            otherColor,
                            Pair(remainingBag, remainingCount)
                        ), count.toInt()
                    )
                )
            }
        }
    }

    fun createBagWithCount(input: String): Pair<Int, Bag> {
        val count = input.first { it.isDigit() }.toString()
        var remaining = input.substring(input.indexOf(count) + 2).trim()
        remaining = remaining.replaceFirst(", ", " contains ")
        return Pair(Integer.parseInt(count), createBag(remaining))
    }
}

data class HandBag(
    val type: String,
    val color: String,
    val canContain: MutableList<Pair<HandBag, Int>> = mutableListOf()
) {
    val name: String
        get() = type + " " + color

    companion object {

        fun from(input: String): HandBag {
            if (!input.contains(',')) {
                val parts = input.split(" ")
                val type = parts[0]
                val color = parts[1]
                if (parts.size == 3 || parts[4] == "no") return HandBag(type, color)
                val otherCount = parts[4].toInt()
                val otherType = parts[5]
                val otherColor = parts[6]
                return HandBag(type, color, mutableListOf(Pair(HandBag(otherType, otherColor), otherCount)))
            } else {
                val firstBag = input.substringBefore("contain").trim().split(" ")
                val result = HandBag(firstBag[0], firstBag[1])
                val subbags = input.substringAfter("contain").trim().split(",")
                val containingBags = mutableListOf<Pair<HandBag, Int>>()
                subbags.forEach { containingBags.add(handBagFromString(it)) }
                result.canContain.addAll(containingBags)
                return result
            }
        }

        private fun handBagFromString(input: String): Pair<HandBag, Int> {
            val parts = input.trim().split(" ")
            return Pair(HandBag(parts[1], parts[2]), parts[0].toInt())
        }
    }
}