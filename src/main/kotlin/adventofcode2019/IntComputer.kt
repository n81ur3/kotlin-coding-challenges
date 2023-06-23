package adventofcode2019

class IntComputer(program: String) {
    var memory: MutableList<Int>
    val initialState: List<Int>
    var ip = 0
    var singleInput = 0
    var output = 0
    var directPointer = false

    init {
        initialState = program.split(",").map { it.toInt() }
        memory = initialState.toMutableList()
    }

    fun run(input: Int): Int {
        singleInput = input
        while ((memory[ip] % 100) != 99) {
            val increment = execute(memory[ip])
            if (!directPointer) {
                ip += increment
            }
        }
        return output
    }

    fun reset() {
        memory = initialState.toMutableList()
    }

    fun runWithPhase(phase: Int, input: Int): Int {
        memory[0] = phase
        memory[1] = input
        var result = 0
        try {
            result = run(0)
        } catch (exc: IndexOutOfBoundsException) {
            println("Index out of bounds")
        }
        return result
    }

    private fun execute(instruction: Int): Int {
        return if (instruction < 3) {
            executeSimpleInstruction(instruction, memory[memory[ip + 1]], memory[memory[ip + 2]], 0, 4)
        } else if (instruction < 5) {
            executeSimpleInstruction(instruction, memory[memory[ip + 1]], 0, 0, 2)
        } else if (instruction in listOf(5, 6)) {
            executeSimpleInstruction(instruction, memory[memory[ip + 1]], memory[memory[ip + 2]], memory[ip + 3], 3)
        } else if (instruction in listOf(7, 8)) {
            executeSimpleInstruction(instruction, memory[memory[ip + 1]], memory[memory[ip + 2]], memory[ip + 3], 4)
        } else {
            executeComplexInstruction(instruction)
        }
    }

    private fun executeSimpleInstruction(
        instruction: Int,
        op1: Int = 0,
        op2: Int = 0,
        op3: Int = 0,
        increment: Int
    ): Int {
        directPointer = false
        when (instruction) {
            1 -> {
                memory[memory[ip + 3]] = op1 + op2
            }

            2 -> {
                memory[memory[ip + 3]] = op1 * op2
            }

            3 -> {
                memory[memory[ip + 1]] = singleInput
            }

            4 -> {
                output = op1
            }

            5 -> {
                if (op1 != 0) {
                    ip = op2
                    directPointer = true
                }
            }

            6 -> {
                if (op1 == 0) {
                    ip = op2
                    directPointer = true
                }
            }

            7 -> {
                if (op1 < op2) {
                    memory[op3] = 1
                } else {
                    memory[op3] = 0
                }
            }

            8 -> {
                if (op1 == op2) {
                    memory[op3] = 1
                } else {
                    memory[op3] = 0
                }
            }

            else -> throw IllegalArgumentException("Illegal opcode")
        }

        return if (directPointer) 0 else increment
    }

    private fun executeComplexInstruction(instruction: Int): Int {
        val opcode = instruction % 10
        var increment = 2

        if (opcode == 4) {
            return executeSimpleInstruction(opcode, memory[memory[ip + 1]], 0, 0, increment)
        }

        increment = if (opcode in listOf(1, 2, 7, 8)) 4 else if (opcode in listOf(3, 4)) 2 else 3
        val mode1 = instruction / 100 % 10
        val mode2 = if (instruction > 999) {
            instruction / 1000 % 10
        } else {
            0
        }
        val mode3 = if (instruction > 9999) {
            instruction / 10000 % 10
        } else {
            0
        }

        val op1 = if (mode1 == 1) memory[ip + 1] else memory[memory[ip + 1]]
        val op2 = if (mode2 == 1) memory[ip + 2] else memory[memory[ip + 2]]

        val op3 = if (mode3 == 1) ip + 3 else memory[ip + 3]

        return executeSimpleInstruction(opcode, op1, op2, op3, increment)
    }
}