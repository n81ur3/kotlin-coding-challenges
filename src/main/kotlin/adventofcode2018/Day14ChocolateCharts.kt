package adventofcode2018

class Day14ChocolateCharts

class ChocolateChart() {
    var elfOnePos = 0
    var elfTwoPos = 1
    val recipies = mutableListOf(3, 7)

    fun produce(steps: Int): String {
        while(recipies.size < steps + 10) {
            getNextScores().forEach { recipies.add(it) }
            elfOnePos = (elfOnePos + recipies[elfOnePos] + 1) % recipies.size
            elfTwoPos = (elfTwoPos + recipies[elfTwoPos] + 1) % recipies.size
        }
        return recipies.drop(steps).map { it.toString() }.joinToString(separator = "")
    }

    fun getNextScores(): List<Int> {
        val sum = recipies[elfOnePos] + recipies[elfTwoPos]
        return if (sum > 9) {
            listOf(sum / 10, sum % 10)
        } else listOf(sum)
    }
}
