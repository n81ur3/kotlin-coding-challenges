package adventofcode2016

data class BalanceBot(
    val id: Int,
    val chips: MutableSet<Int> = mutableSetOf()
) {
    val loaded: Boolean
        get() = chips.size == 2
}

class BotsProcessor(
    val bots: MutableMap<Int, BalanceBot> = mutableMapOf(),
    val outputs: MutableMap<Int, Int> = mutableMapOf(),
    var botsInteraction: Boolean = true,
    val compareValues: MutableSet<Int> = mutableSetOf()
) {

    fun buildBots(instructions: List<String>) {
        instructions.forEach { instruction ->
            val botId = instruction.split(" ").get(1).toInt()
            bots[botId] = BalanceBot(botId)
        }
    }

    fun setCompareValues(values: Set<Int>) {
        compareValues.addAll(values)
    }

    fun transferChipToBot(fromBotId: Int, toBotId: Int, transferLower: Boolean) {
        val chips = bots[fromBotId]?.chips?.sorted() ?: return
        if (chips.isEmpty()) return
        if (!transferLower && chips.size < 2) return
        val chipValue = if (transferLower) chips.get(0) else chips.get(1)
        chipValue.let {
            bots[toBotId]?.chips?.add(it)
        }
    }

    fun transferChipToOutput(fromBotId: Int, outputId: Int, transferLower: Boolean) {
        val chips = bots[fromBotId]?.chips?.sorted() ?: return
        if (chips.isEmpty()) return
        if (!transferLower && chips.size < 2) return
        val chipValue = if (transferLower) chips.get(0) else chips.get(1)
        outputs.putIfAbsent(outputId, chipValue)
    }

    fun run(instructions: List<String>): Int {
        var result = 0
        while (true) {
            instructions.forEach { instruction ->
                when {
                    instruction.startsWith("value") -> executeValueInstruction(instruction)
                    else -> {
                        result = executeTransferInstruction(instruction)
                    }
                }
                if (result > 0) return result
            }
            if (bots.none { entry -> entry.value.loaded }) break
        }
        return result
    }

    private fun executeValueInstruction(instruction: String) {
        val parts = instruction.split(" ")
        val value = parts[1].toInt()
        val botId = parts[5].toInt()
        bots[botId]?.chips?.add(value)
    }

    private fun executeTransferInstruction(instruction: String): Int {
        val parts = instruction.split(" ")
        val fromBotId = parts[1].toInt()
        if (bots[fromBotId]?.loaded == false) {
            botsInteraction = false
            return 0
        }
        val firstOut = parts[6].toInt()
        val secondOut = parts[11].toInt()

        if (bots[fromBotId]?.chips?.containsAll(compareValues) == true) return fromBotId

        if (parts[5].startsWith("bot")) transferChipToBot(
            fromBotId,
            firstOut,
            true
        ) else {
            transferChipToOutput(fromBotId, firstOut, true)
        }
        if (parts[10].startsWith("bot")) transferChipToBot(fromBotId, secondOut, false) else {
            transferChipToOutput(fromBotId, secondOut, false)
        }
        botsInteraction = true
        bots[fromBotId]?.chips?.clear()

        return 0
    }
}