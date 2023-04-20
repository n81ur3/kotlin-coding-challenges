package adventofcode2019

class Day02ProgramAlarm

class IntcodeProgram(input: String) {
    var instructions: MutableList<Int>
    val initialMemoryState: List<Int>
    var ip = 0

    init {
        instructions = input.split(",").map { it.toInt() }.toMutableList()
        initialMemoryState = instructions.toList()
    }

    fun run(): Int {
        var counter = 0
        while (true) {
            if (instructions[ip] == 99) return instructions[0]
            counter++
            if (counter > 1000) return 0
            step()
        }
    }

    fun runForTarget(target: Int): Int {
        while (true) {
            (0..99).forEach { noun ->
                (0..99).forEach { verb ->
                    instructions = initialMemoryState.toMutableList()
                    setInitialState(noun, verb)
                    val result = run()
                    if (result == target) return 100 * noun + verb
                }
            }
        }
    }

    fun setInitialState(noun: Int = 12, verb: Int = 2) {
        ip = 0
        instructions[1] = noun
        instructions[2] = verb
    }

    private fun step() {
        val opCode = instructions[ip]
        val op1 = instructions[ip + 1]
        val op2 = instructions[ip + 2]
        val target = instructions[ip + 3]

        when (opCode) {
            1 -> instructions[target] = instructions[op1] + instructions[op2]
            2 -> instructions[target] = instructions[op1] * instructions[op2]
        }
        ip += 4
    }
}