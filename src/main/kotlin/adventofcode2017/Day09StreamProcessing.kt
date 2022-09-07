package adventofcode2017

class Day09StreamProcessing

class StreamProcessor(val input: String) {
    var groupCount = 0
    var garbageCount = 0
    private var groupLevel = 0
    private var inGarbage = false
    private var ignore = false

    fun process() {
        input.forEach { evaluateChar(it) }
    }

    private fun evaluateChar(char: Char) {
        if (ignore) {
            ignore = false
            return
        }

        when (char) {
            '{' -> {
                if (!inGarbage) {
                    groupLevel++
                    return
                }
            }

            '<' -> {
                if (!inGarbage) {
                    inGarbage = true
                    return
                }
            }

            '!' -> {
                ignore = true
                return
            }
        }

        if (inGarbage && char != '>') {
            garbageCount++
        }

        when (char) {
            '>' -> {
                inGarbage = false
            }

            '}' -> {
                if (!inGarbage) {
                    groupCount += groupLevel
                    groupLevel--
                }
            }
        }
    }
}