package adventofcode2017

class Day23CoprocessorConflagration

sealed class CoInstruction(command: String) {
    val first: Char
    val second: String

    init {
        val parts = command.split(" ")
        first = parts[1].first()
        second = parts[2]
    }

}

class SetCoInstruction(command: String) : CoInstruction(command)
class SubCoInstruction(command: String) : CoInstruction(command)
class MulCoInstruction(command: String) : CoInstruction(command)
class JnzCoInstruction(command: String) : CoInstruction(command)

class CoProcessor(instructionset: List<String>) {
    var ip = 0
    val registers = mutableMapOf<Char, Int>()
    val instructions = mutableListOf<CoInstruction>()
    var mulCounter = 0

    init {
        initRegisters()
        parseInstructions(instructionset)
    }

    private fun initRegisters() {
        ('a'..'h').forEach { registers[it] = 0 }
        registers['1'] = 1
    }

    private fun parseInstructions(instructionset: List<String>) {
        instructionset.forEach { instruction ->
            val parts = instruction.split(" ")
            when (parts[0]) {
                "set" -> instructions.add(SetCoInstruction(instruction))
                "sub" -> instructions.add(SubCoInstruction(instruction))
                "mul" -> instructions.add(MulCoInstruction(instruction))
                "jnz" -> instructions.add(JnzCoInstruction(instruction))
            }
        }
    }

    fun run() {
        while(ip < instructions.size) {
            executeInstruction(instructions[ip])
            ip++
        }
    }

    private fun executeInstruction(instruction: CoInstruction) {
        when (instruction) {
            is SetCoInstruction -> {
                registers[instruction.first] = getInstructionValue(instruction.second)
            }
            is SubCoInstruction -> {
                val registerValue = registers[instruction.first]
                registerValue?.let {
                    registers[instruction.first] = it - getInstructionValue(instruction.second)
                }
            }
            is MulCoInstruction -> {
                val registerValue = registers[instruction.first]
                registerValue?.let {
                    registers[instruction.first] = it * getInstructionValue(instruction.second)
                }
                mulCounter++
            }
            is JnzCoInstruction -> {
                val registerValue = registers[instruction.first]
                registerValue?.let {
                    if (it != 0) {
                        ip += getInstructionValue(instruction.second) - 1
                    }
                }
            }
        }
    }

    private fun getInstructionValue(value: String): Int {
        if (value.last().isDigit()) {
            return value.toInt()
        } else {
            return registers[value.first()] ?: 0
        }
    }
}