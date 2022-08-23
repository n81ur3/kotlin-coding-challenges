package adventofcode2017

class Day05TwistyMaze

class InstructionSet(input: List<String>) {
    val instructions: MutableList<Int>

    init {
        instructions = input.map { Integer.parseInt(it) }.toMutableList()
    }

    fun run(part: Int): Int {
        var stepCounter = 0
        var currentPosition = 0

        var step: Int
        while (currentPosition < instructions.size) {
            step = instructions[currentPosition]
            if (part == 2 && step >= 3) {
                instructions[currentPosition] = instructions[currentPosition] - 1
            } else {
                instructions[currentPosition] = instructions[currentPosition] + 1
            }
            currentPosition += step
            stepCounter++
        }
        return stepCounter
    }
}