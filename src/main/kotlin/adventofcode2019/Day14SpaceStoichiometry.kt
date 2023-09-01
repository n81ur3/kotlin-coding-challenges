package adventofcode2019

import kotlin.math.ceil

class Day14SpaceStoichiometry

data class Chemical(var name: String)

data class Reaction(
    val resultingChemical: Pair<Long, Chemical>,
    val inputChemicals: List<Pair<Long, Chemical>>
)

class ChemicalReactor(reactionsInput: List<String>) {
    val reactions: List<Reaction>
    val requiredChemicals = mutableListOf<Pair<Long, Chemical>>()
    var extraChemicals = mutableMapOf<Chemical, Long>()

    init {
        reactions = reactionsInput.map { parseReaction(it) }
        reactions.forEach { reaction -> extraChemicals[reaction.resultingChemical.second] = 0 }
    }

    private fun parseReaction(input: String): Reaction {
        val result = parseChemicalAmount(input.substringAfter("> "))

        val inputChemicals = input.substringBefore(" =").split(", ").map { parseChemicalAmount(it) }

        return Reaction(result, inputChemicals)
    }

    private fun parseChemicalAmount(input: String): Pair<Long, Chemical> {
        val resultAmount = input.split(" ")[0].toLong()
        val resultChemical = input.split(" ")[1]

        return resultAmount to Chemical(resultChemical)
    }

    fun calculateFireForOneTrillionOres(): Long {
        var acquiredFire = 1L
        var fireMin = 1L
        var fireMax = 10000000000L
        val oreAvailable = 1000000000000L
        var requiredOre = 0L
        val results = longArrayOf(0L, 0L)
        var counter = 0

        while ((fireMin + 2) < fireMax) {
            resetReactor()
            acquiredFire = ((fireMax - fireMin) / 2) + fireMin
            requiredOre = calculateRequiredOre(acquiredFire)
            if (requiredOre < oreAvailable) fireMin = acquiredFire
            else fireMax = acquiredFire
            results[counter++ % 2] = acquiredFire
        }

        return findBestMatch(results[0], results[1])
    }

    private fun findBestMatch(firstBound: Long, secondBound: Long): Long {
        val min = if (firstBound < secondBound) firstBound else secondBound
        val max = if (firstBound > secondBound) firstBound else secondBound

        var bestMatch = min
        var smallestDistance = Long.MAX_VALUE
        var currentDistance = Long.MAX_VALUE

        (min..max).forEach {
            resetReactor()
            currentDistance = 1000000000000L - calculateRequiredOre(it)
            if (currentDistance < smallestDistance) {
                smallestDistance = currentDistance
                if (smallestDistance < 0) return@forEach
                bestMatch = it
            }
        }

        return bestMatch
    }

    fun calculateRequiredOre(amount: Long = 1): Long {
        getOreFor(Chemical("FUEL"), amount)
        val requiredGroups = requiredChemicals.groupBy { it.second }
        val requiredAmounts = requiredGroups.map { it.key to it.value.sumOf { it.first } }

        var totalOre = 0L

        requiredAmounts.forEach { chemicalAmount ->
            val requiredReaction =
                reactions.firstOrNull { it.resultingChemical.second == chemicalAmount.first } ?: return 0
            var ore = 0L
            val requiredLoops =
                ceil(chemicalAmount.second.toDouble() / requiredReaction.resultingChemical.first).toLong()
            ore += (requiredLoops * requiredReaction.inputChemicals.first().first)
            totalOre += ore
        }

        return totalOre
    }

    private fun getOreFor(chemical: Chemical, amount: Long): Long {

        val requiredReaction = reactions.firstOrNull { it.resultingChemical.second == chemical } ?: return 0

        if (requiredReaction.inputChemicals.first().second == Chemical("ORE")) {
            requiredChemicals.add(amount to requiredReaction.resultingChemical.second)
            return 0
        }

        val requiredLoops = ceil(amount.toDouble() / requiredReaction.resultingChemical.first).toLong()
        val excessiveAmount = ((requiredReaction.resultingChemical.first * requiredLoops) - amount).coerceAtLeast(0)

        extraChemicals[requiredReaction.resultingChemical.second] =
            extraChemicals[requiredReaction.resultingChemical.second]!!.plus(excessiveAmount)

        return requiredReaction.inputChemicals.sumOf { reaction ->
            val calculatedAmount = requiredLoops * reaction.first
            val inventoryAmount = extraChemicals[reaction.second] ?: 0
            extraChemicals[reaction.second] = extraChemicals[reaction.second]!!.minus(inventoryAmount).coerceAtLeast(0)

            getOreFor(reaction.second, (calculatedAmount - inventoryAmount))
        }
    }

    fun resetReactor() {
        requiredChemicals.clear()
        extraChemicals.clear()
        reactions.forEach { reaction -> extraChemicals[reaction.resultingChemical.second] = 0 }
    }
}