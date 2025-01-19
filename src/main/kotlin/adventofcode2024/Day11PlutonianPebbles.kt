package adventofcode2024

class PlutoTransformer(
    val input: String
) {
    var stones: List<String>
    private val cache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()

    init {
        stones = input.split(" ")
    }

    fun blinkSum(times: Int): Long {
        return stones.sumOf { blink(it.toLong(), times) }
    }

    fun blink(stone: Long, times: Int, key: Pair<Long, Int> = stone to times): Long {
        return when {
            times == 0 -> 1
            key in cache -> cache.getValue(key)
            else -> {
                val result = when {
                    stone == 0L -> blink(1, times - 1)
                    stone.hasEvenDigits() -> stone.split().sumOf { blink(it, times -1)}
                    else -> blink(stone * 2024, times - 1)
                }
                cache[key] = result
                result
            }
        }
    }

    private fun Long.hasEvenDigits() = this.toString().length % 2 == 0

    private fun Long.split(): List<Long> {
        val s = this.toString()
        return listOf(s.substring(0, s.length / 2).toLong(), s.substring(s.length / 2).toLong())
    }
}