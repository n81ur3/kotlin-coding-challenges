package adventofcode2017

class Day17Spinlock

class Spinlock(private val steps: Int) {
    private val buffer = mutableListOf(0)
    private var currentPosition = 0
    var target = 0

    fun step(count: Int) {
        (1..count).forEach { currentValue ->
            currentPosition = (currentPosition + steps) % currentValue
            buffer.add(++currentPosition, currentValue)
        }
    }

    fun spin(count: Int) {
        (1..count).forEach { currentValue ->
            currentPosition = (currentPosition + steps) % currentValue
            if (currentPosition == 0) {
                target = currentValue
            }
            currentPosition++
        }
    }

    fun getResult() = buffer[(currentPosition + 1) % buffer.size]
}