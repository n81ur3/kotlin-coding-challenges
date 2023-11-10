package adventofcode2015

class Day02ToldNoMatch

data class RectPrism(
    val l: Int,
    val w: Int,
    val h: Int
) {
    fun requiredPaper() = (2 * l * w) + (2 * w * h) + (2 * h * l) + smallestSquare()

    private fun smallestSquare() = listOf(l, w, h).sorted().take(2).reduce { acc, i -> acc * i }
}

class PrismCalculator(input: List<String>) {
    val boxes: List<RectPrism>
    val totalPaperRequired: Int
        get() = boxes.sumOf { it.requiredPaper() }

    init {
        boxes = input.map { toBox(it) }
    }

    private fun toBox(input: String): RectPrism {
        val parts = input.split("x")
        return RectPrism(
            parts[0].toInt(),
            parts[1].toInt(),
            parts[2].toInt()
        )
    }

}