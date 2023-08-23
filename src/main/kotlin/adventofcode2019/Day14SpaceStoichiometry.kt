package adventofcode2019

import kotlin.math.ceil

class Day14SpaceStoichiometry

data class Chemical(var name: String)

data class Reaction(
    val resultingChemical: Pair<Int, Chemical>,
    val inputChemicals: List<Pair<Int, Chemical>>
)

class ChemicalReactor(reactionsInput: List<String>) {
    val reactions: List<Reaction>
    val requiredChemicals = mutableListOf<Pair<Int, Chemical>>()
    val extraChemicals = mutableMapOf<Chemical, Int>()

    init {
        reactions = reactionsInput.map { parseReaction(it) }
        reactions.forEach { reaction -> extraChemicals[reaction.resultingChemical.second] = 0 }
    }

    private fun parseReaction(input: String): Reaction {
        val result = parseChemicalAmount(input.substringAfter("> "))

        val inputChemicals = input.substringBefore(" =").split(", ").map { parseChemicalAmount(it) }

        return Reaction(result, inputChemicals)
    }

    private fun parseChemicalAmount(input: String): Pair<Int, Chemical> {
        val resultAmount = input.split(" ")[0].toInt()
        val resultChemical = input.split(" ")[1]

        return resultAmount to Chemical(resultChemical)
    }

    fun calculateRequiredOre(): Int {
        getOreFor(Chemical("FUEL"), 1)
        val requiredGroups = requiredChemicals.groupBy { it.second }
        val requiredAmounts = requiredGroups.map { it.key to it.value.sumOf { it.first } }

        var totalOre = 0

        requiredAmounts.forEach { chemicalAmount ->
            val requiredReaction =
                reactions.firstOrNull { it.resultingChemical.second == chemicalAmount.first } ?: return 0
            var ore = 0
            val requiredLoops =
                ceil(chemicalAmount.second.toDouble() / requiredReaction.resultingChemical.first).toInt()
            ore += (requiredLoops * requiredReaction.inputChemicals.first().first)
            totalOre += ore
        }

        return totalOre
    }

    private fun getOreFor(chemical: Chemical, amount: Int): Int {

        val requiredReaction = reactions.firstOrNull { it.resultingChemical.second == chemical } ?: return 0

        if (requiredReaction.inputChemicals.first().second == Chemical("ORE")) {
            requiredChemicals.add(amount to requiredReaction.resultingChemical.second)
            return 0
        }

        val requiredLoops = ceil(amount.toDouble() / requiredReaction.resultingChemical.first).toInt()
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
}