package adventofcode2018

class Day12SubterraneanSustainability

data class PotRule(val input: String, val result: Char) {
    companion object {
        fun fromString(rule: String): PotRule {
            return PotRule(rule.substringBefore(" ="), rule.last())
        }
    }
}

class RulesSet(val rules: List<PotRule>) {

    fun getResultForInput(input: List<Char>): Char {
        val pattern = input.joinToString(separator = "")
        return rules.firstOrNull { it.input == pattern }?.result ?: '.'
    }

    companion object {
        fun fromRules(rules: List<String>): RulesSet {
            val potRules = rules.map { PotRule.fromString(it) }
            return RulesSet(potRules)
        }
    }
}

class Tunnel(initialState: String, rules: List<String>) {
    var pots = ArrayDeque(initialState.toList())
    var nullIndex = 0
    val rulesSet: RulesSet = RulesSet.fromRules(rules)

    private fun extendPots() {
        repeat(3) {
            pots.addFirst('.')
            pots.addLast('.')
        }
    }

    fun computeGenerations(iterations: Long): Long {
        if (iterations < 2000) {
            (0 until iterations).forEach {
                computeNextGeneration()
            }
            return computeTotalPotsSum()
        } else {
            return computeVeryOldGeneration(iterations)
        }
    }

    private fun computeVeryOldGeneration(iterations: Long): Long {
        computeGenerations(1000)
        val oneThousandGenerationCount = computeTotalPotsSum()
        computeGenerations(1000)
        val twoThousandGenerationCount = computeTotalPotsSum()
        val oneThousandIntervalCount = twoThousandGenerationCount - oneThousandGenerationCount
        val numberOfIntervalls = ((iterations - 1000)/1000)
        return (numberOfIntervalls) * oneThousandIntervalCount + oneThousandGenerationCount
    }

    fun computeNextGeneration(): Boolean {
        extendPots()
        val newList = pots.windowed(5, 1, false).map {
            rulesSet.getResultForInput(it)
        }
        if (newList.first() == '#') {
            nullIndex++
            pots = ArrayDeque(newList)
        } else {
            pots = ArrayDeque(newList.drop(1))
        }
        return false
    }

    fun computeTotalPotsSum(): Long {
        var result = 0L
        pots.forEachIndexed { index, pot ->
            if (pot == '#') {
                result += (index - nullIndex)
            }
        }
        return result
    }
}