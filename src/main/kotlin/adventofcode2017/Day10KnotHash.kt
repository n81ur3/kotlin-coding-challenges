package adventofcode2017

class Day10KnotHash

class KnotHash(private val size: Int) {
    val knots: MutableList<Int>
    private var currentPosition = 0
    private var skipSize = 0
    val finalHash: Int
        get() = knots[0] * knots[1]

    init {
        knots = MutableList(size) { it }
    }

    fun hashSimple(length: Int) {
        reversePart(currentPosition, length)
        currentPosition = (currentPosition + length + skipSize) % size
        skipSize++
    }

    fun hashAdvanced(input: String): String {
        val lengths = parseInputToAsciiLengths(input)
        repeat(64) {
            lengths.forEach { length ->
                hashSimple(length)
            }
        }
        val denseHash = knots.windowed(16, 16).map { block -> denseBlock(block) }
        return denseHash.map { it.toString(16).padStart(2, '0') }.joinToString(separator = "")
    }

    private fun reversePart(start: Int, length: Int) {
        var subKnots = mutableListOf<Int>()
        (start..start + length - 1).forEach { index ->
            subKnots.add(knotAt(index))
        }
        subKnots = subKnots.reversed().toMutableList()
        subKnots.forEachIndexed { index, value -> setKnotAt(start + index, value) }
    }

    fun parseInputToAsciiLengths(input: String): List<Int> {
        val asciiCodes = input.toCharArray().map { it.toInt() }
        return asciiCodes + listOf(17, 31, 73, 47, 23)
    }

    fun denseBlock(sparseHash: List<Int>): Int {
        return sparseHash.reduce { acc, knot -> acc xor knot }
    }

    private fun knotAt(index: Int) = knots[index % size]

    private fun setKnotAt(index: Int, value: Int) {
        knots[index % size] = value
    }

    fun reset() {
        currentPosition = 0
        skipSize = 0
        knots.forEachIndexed { index, knot -> knots[index] = index }
    }
}