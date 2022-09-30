package adventofcode2017

class Day17Spinlock

class Spinlock(private val steps: Int) {
    private val buffer = mutableListOf(0)
    private var currentPosition = 0

    fun step(count: Int) {
        (1..count).forEach { currentValue ->
            currentPosition = (currentPosition + steps) % currentValue
            buffer.add(++currentPosition, currentValue)
        }
    }

    fun getResult() = buffer[(currentPosition + 1) % buffer.size]
}