package adventofcode2020

class Day19MonsterMessage

data class MessageRule(
    var ruleExpression: String = "",
) {
    val fullRule: String
        get() = ruleExpression

    override fun toString(): String = (ruleExpression).replace(" ", "")

    val isEvaluated: Boolean
        get() {
            val regex = "[ab |()]*".toRegex()
            return (regex.matches(ruleExpression))
        }

    fun substituteRule(ruleNumber: String, rule: String) {
        val regex = " $ruleNumber ".toRegex()
        ruleExpression = ruleExpression.replace(regex, " ( " + rule + " ) ")
        ruleExpression = ruleExpression.replace(regex, " ( " + rule + " ) ")
    }

    fun matches(message: String): Boolean {
        val regex = (ruleExpression.replace(" ", "")).toRegex()
        return regex.matches(message)
    }
}

class RulesChecker {
    val rules = mutableMapOf<Int, MessageRule>()

    fun buildRulesFromLines(rules: List<String>) {
        rules.forEach { rule ->
            this.rules.put(
                rule.substringBefore(":").toInt(),
                MessageRule(rule.substringAfter(":").replace("\"", "").replace("\"", "") + " ")
            )
        }
        buildRulesFromRawRules()
    }

    private fun buildRulesFromRawRules() {
        var evaluatedRules = mapOf<Int, MessageRule>()
        val substitutedRules = mutableListOf<Int>()
        do {
            evaluatedRules = rules.filter { (_, messageRule) -> messageRule.isEvaluated }
            if (evaluatedRules.isNotEmpty()) {
                val rulesToReplace =
                    evaluatedRules.entries.filter { (ruleNumber, _) -> ruleNumber !in substitutedRules }
                if (rulesToReplace.isNotEmpty()) {
                    rulesToReplace.forEach { nextRuleToReplace ->
                        rules.values.forEach {
                            it.substituteRule(
                                nextRuleToReplace.key.toString(),
                                nextRuleToReplace.value.fullRule
                            )
                        }
                        substitutedRules.add(nextRuleToReplace.key)
                    }
                }
            }
        } while (evaluatedRules.size != rules.size)
    }

    fun evaluateMessage(message: String): Boolean {
        return rules.get(0)?.matches(message) ?: false
    }
}
