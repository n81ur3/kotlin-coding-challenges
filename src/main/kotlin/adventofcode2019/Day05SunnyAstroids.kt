package adventofcode2019

class Day05SunnyAstroids

class AirConditionUnit(program: String) {
    val memory: MutableList<Int> = program.split(",").map { it.toInt() }.toMutableList()
    var ip = 0
    val singleInput = 1
    var output = 0

    fun run(): Int {
        while (memory[ip] != 99) {
            ip += execute(memory[ip])
        }
        return output
    }

    private fun execute(instruction: Int): Int {
        if (instruction < 3) {
            return executeSimpleInstruction(instruction, memory[memory[ip + 1]], memory[memory[ip + 2]])
        } else if (instruction < 5) {
            return executeSimpleInstruction(instruction, memory[memory[ip + 1]], 0)
        } else {
            return executeComplexInstruction(instruction)
        }
    }

    private fun executeSimpleInstruction(instruction: Int, op1: Int = 0, op2: Int = 0): Int {
        return when (instruction) {
            1 -> {
                memory[memory[ip + 3]] = op1 + op2
                4
            }

            2 -> {
                memory[memory[ip + 3]] = op1 * op2
                4
            }

            3 -> {
                memory[memory[ip + 1]] = singleInput
                2
            }

            4 -> {
                output = op1
                2
            }

            else -> throw IllegalArgumentException("Illegal opcode")
        }
    }

    private fun executeComplexInstruction(instruction: Int): Int {
        val opcode = instruction % 10

        if (opcode == 4) {
            return executeSimpleInstruction(opcode, memory[memory[ip + 1]])
        }

        val mode1 = instruction / 100 % 10
        val mode2 = if (instruction > 999) {
            instruction / 1000 % 10
        } else {
            0
        }

        val op1 = if (mode1 == 1) memory[ip + 1] else memory[memory[ip + 1]]
        val op2 = if (opcode < 3) {
            if (mode2 == 1) memory[ip + 2] else memory[memory[ip + 2]]
        } else {
            0
        }

        return executeSimpleInstruction(opcode, op1, op2)
    }
}