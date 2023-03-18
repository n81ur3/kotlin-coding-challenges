package adventofcode2018

class Day19GoWithTheFlow

data class OpInstruction(val opCode: Opcode, val a: Int, val b: Int, val c: Int) {
    fun toInstructionCode() = InstructionCode(a = a, b = b, c = c)
}

class OpcodeExecutor(input: List<String>) {
    val opcodesMapping = Opcode.oppCodesMapping
    val instructions: List<OpInstruction>
    var registerState = RegisterState(0, 0, 0, 0)
    var instructionCounter = 0
    var ip = 0
    var ipBound = 0
    val magicRegisterValues = LinkedHashSet<Int>()

    init {
        ipBound = input[0].substringAfter(" ").toInt()
        instructions = input
            .drop(1)
            .map { parseInstructionCode(it) }
    }

    private fun parseInstructionCode(input: String): OpInstruction {
        val parts = input.split(" ")
        return OpInstruction(opcodesMapping[parts[0]]!!, parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
    }

    fun execute(): Pair<Int, Int> {
        while (ip < instructions.size) {
            registerState.setRegister(ipBound, ip)
            instructionCounter++
            val currentInstruction = instructions[ip]
            if (currentInstruction == OpInstruction(Opcode.eqrr, 1, 0, 5)) {
                if (registerState.reg1 in magicRegisterValues) {
                    return magicRegisterValues.first() to magicRegisterValues.last()
                }
                magicRegisterValues.add(registerState.reg1)
            }
            registerState = instructions[ip].opCode.execute(registerState, instructions[ip].toInstructionCode())
            ip = registerState.getRegValue(ipBound)
            ip++
        }
        println("final result: $registerState")
        return registerState.getRegValue(0) to 0
    }
}
