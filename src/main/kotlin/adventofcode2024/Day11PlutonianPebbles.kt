package adventofcode2024

class PlutoTransformer(
    val input: String
) {
    var stones: List<String>

    init {
        stones = input.split(" ")
    }

    fun blink(times: Int): Int {
        repeat(times) {
            stones = stones.map { transformStone(it) }.flatMap { it.split(" ") }
        }
        return stones.size
    }

    private fun transformStone(stone: String): String {
        return when {
            stone == "0" -> "1"
            (stone.length % 2 == 0) -> stone.substring(0, stone.length / 2) +
                    " " +
                    stone.substring(stone.length / 2).toLong()

            else -> ((stone.toLong()) * 2024).toString()
        }
    }
}