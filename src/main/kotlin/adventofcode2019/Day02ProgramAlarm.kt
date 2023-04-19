package adventofcode2019

class Day02ProgramAlarm

class IntcodeProgram(input: String) {
    val instructions: MutableList<Int>
    var ip = 0

    init {
        instructions = input.split(",").map { it.toInt() }.toMutableList()
    }

    fun run(): Int {
        while (true) {
            if (instructions[ip] == 99) return instructions[0]
            step()
        }
    }

    fun set1202State() {
        instructions[1] = 12
        instructions[2] = 2
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