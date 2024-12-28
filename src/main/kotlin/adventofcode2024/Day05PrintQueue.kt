package adventofcode2024

class QueuePrinter(
    val input: List<String>
) {
    val orderRules: List<Pair<Int, Int>>
    val updateRules: List<List<Int>>

    init {
        orderRules = input.takeWhile { it.isNotBlank() }.map { it.split("|") }.map { it[0].toInt() to it[1].toInt() }
        updateRules = input.dropWhile { it.isNotBlank() }.drop(1).map { it.split(",").map { it.toInt() } }
    }

    fun getSumOfCorrectUpdateRules(): Int {
        var result = 0
        val printedPages = mutableSetOf<Int>()

        updateRules.forEach { updateRule ->
            var validUpdate = true
            updateRule.forEach { update ->
                val ok = orderRules
                    .filter { it.second == update }
                    .filter { updateRule.contains(it.first) }
                    .map { it.first }
                    .all { printedPages.contains(it) }
                if (ok) printedPages.add(update)
                else validUpdate = false
            }
            if (validUpdate) result += updateRule.getMiddleValue()
            printedPages.clear()
        }

        return result
    }

    fun getSumOfIncorrectUpdateRules(): Int {
        var result = 0
        val printedPages = mutableSetOf<Int>()

        updateRules.forEach { updateRule ->
            var validUpdate = true
            updateRule.forEach { update ->
                val ok = orderRules
                    .filter { it.second == update }
                    .filter { updateRule.contains(it.first) }
                    .map { it.first }
                    .all { printedPages.contains(it) }
                if (ok) printedPages.add(update)
                else validUpdate = false
            }
            if (!validUpdate) result += fixRule(updateRule)
            printedPages.clear()
        }

        return result
    }

    private fun fixRule(updateRule: List<Int>): Int {
        var updateCandidate = updateRule.toMutableList()
        var validUpdate = false
        var currentIndex = 0
        val newCandidate = updateCandidate.toMutableList()
        while (validUpdate == false || currentIndex < updateRule.size - 1) {
            validUpdate = true
                val requiredUpdates =
                    orderRules.filter { it.second == updateCandidate[currentIndex] }.filter { updateRule.contains(it.first) }
                        .map { it.first }.filter { !(updateCandidate.take(currentIndex).contains(it)) }
                if (requiredUpdates.isNotEmpty()) {
                    validUpdate = false
                    requiredUpdates.forEachIndexed { innerIndex, innerUpdate ->
                        newCandidate.remove(innerUpdate)
                        newCandidate.add(currentIndex, innerUpdate)
                    }
                } else {
                    currentIndex++
                }
            updateCandidate = newCandidate
        }
        return updateCandidate.getMiddleValue()
    }

    private fun MutableList<Int>.swap(firstIndex: Int, secondIndex: Int) {
        val tmp = this[firstIndex]
        this[firstIndex] = this[secondIndex]
        this[secondIndex] = tmp
    }

    private fun List<Int>.getMiddleValue() = this[size / 2]
}