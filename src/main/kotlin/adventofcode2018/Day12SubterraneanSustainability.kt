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
    val recordedPatterns = mutableListOf<List<Char>>()

    private fun extendPots() {
        repeat(3) {
            pots.addFirst('.')
            pots.addLast('.')
        }
    }

    fun computeGenerations(iterations: Int): Int {
        repeat(iterations) {
            if (computeNextGeneration()) {
                return computeTotalPotsSum()
            }
        }
        return computeTotalPotsSum()
    }

    fun computeNextGeneration(): Boolean {
        extendPots()
        val newList = pots.windowed(5, 1, false).map {
            rulesSet.getResultForInput(it)
        }
        if (newList.first() == '#') {
            nullIndex++
            pots = ArrayDeque(newList.dropLastWhile { it == '.' })
        } else {
            pots = ArrayDeque(newList.drop(1).dropLastWhile { it == '.' })
        }
        if (recordedPatterns.contains(pots)) {
            return true
        } else {
            recordedPatterns.add(pots)
        }
        return false
    }

    fun computeTotalPotsSum(): Int {
        var result = 0
        pots.forEachIndexed { index, pot ->
            if (pot == '#') {
                result += (index - nullIndex)
            }
        }
        return result
    }
}