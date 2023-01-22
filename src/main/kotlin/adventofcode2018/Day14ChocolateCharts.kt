package adventofcode2018

class Day14ChocolateCharts

class ChocolateChart {
    var elfOnePos = 0
    var elfTwoPos = 1
    val recipies = mutableListOf(3, 7)

    fun produce(steps: Int): String {
        while (recipies.size < steps + 10) {
            advanceRecipies()
        }
        return recipies.drop(steps).map { it.toString() }.joinToString(separator = "")
    }

    fun producePattern(pattern: String): Int {
        val patternList = pattern.map { it.digitToInt() }

        repeat(pattern.length - 1) {
            advanceRecipies()
        }

        while (true) {
            val nextScores = getNextScores()
            nextScores.forEach {
                recipies.add(it)
                if (recipiesEndsWith(patternList)) {
                    return recipies.lastIndex - (pattern.length - 1)
                }
            }
            advanceElfs()
        }
    }

    private fun recipiesEndsWith(patternList: List<Int>) =
        recipies.subList(recipies.size - (patternList.size), recipies.size) == patternList

    private fun advanceRecipies() {
        val nextValues = getNextScores()
        recipies.addAll(nextValues)
        advanceElfs()
    }

    private fun advanceElfs() {
        elfOnePos = (elfOnePos + recipies[elfOnePos] + 1) % recipies.size
        elfTwoPos = (elfTwoPos + recipies[elfTwoPos] + 1) % recipies.size
    }

    private fun getNextScores(): List<Int> {
        val sum = recipies[elfOnePos] + recipies[elfTwoPos]
        return sum.toString().map { it.digitToInt() }
    }
}
