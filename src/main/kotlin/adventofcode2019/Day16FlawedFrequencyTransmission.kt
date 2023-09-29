package adventofcode2019

import kotlin.math.abs
import kotlin.math.absoluteValue

class Day16FlawedFrequencyTransmission

class FFT(val input: String) {
    var elements: List<Int>

    init {
        elements = input.toCharArray().map { it.toString().toInt() }
    }

    fun run(phases: Int): String {
        var pattern: List<Int>

        var intermediateElements = elements
        var newElements: MutableList<Int> = mutableListOf()
        repeat(phases) { phase ->
            var currentElements: List<Int> = emptyList()
            repeat(elements.size) { loop ->
                pattern = buildPatternForPhase(loop + 1)
                currentElements = intermediateElements.mapIndexed { index, e ->
                    run {
                        return@mapIndexed e * pattern[(index + 1) % pattern.size]
                    }
                }
                val sum = abs(currentElements.sumOf { it } % 10)
                newElements.add(sum)
            }
            intermediateElements = newElements
            newElements = mutableListOf()
        }
        return intermediateElements.take(8).joinToString(separator = "")

    }

    private fun buildPatternForPhase(phase: Int): List<Int> {
        val basePattern = mutableListOf(0, 1, 0, -1)
        return basePattern.map { element -> List(phase) { element } }.flatten()
    }

    fun runExtended(phases: Int = 100): String {
        val offset = input.take(7).toInt()
        val stretchedInput = (offset until 10_000 * input.length).map { elements[it % input.length] }.toIntArray()
        repeat(phases) {
            stretchedInput.indices.reversed().fold(0) { carry, idx ->
                (stretchedInput[idx] + carry).lastDigit().also { stretchedInput[idx] = it }
            }
        }
        return stretchedInput.take(8).joinToString(separator = "")
    }

    private fun Int.lastDigit(): Int = (this % 10).absoluteValue
}