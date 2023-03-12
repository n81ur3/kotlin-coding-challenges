package adventofcode2018

class Day16ChronalClassification

data class RegisterState(
    var reg0: Int,
    var reg1: Int,
    var reg2: Int,
    var reg3: Int,
    var reg4: Int = 0,
    var reg5: Int = 0
) {
    val highestRegValue: Int
        get() = maxOf(reg0, reg1, reg2, reg3, reg4, reg5)

    fun getRegValue(reg: Int) = when (reg) {
        0 -> reg0
        1 -> reg1
        2 -> reg2
        3 -> reg3
        4 -> reg4
        else -> reg5
    }

    fun setRegister(register: Int, value: Int) {
        if (value > 1000000) println("High reg value: $value")
        when (register) {
            0 -> reg0 = value
            1 -> reg1 = value
            2 -> reg2 = value
            3 -> reg3 = value
            4 -> reg4 = value
            else -> reg5 = value
        }
    }
}

data class InstructionCode(val opcode: Int = 0, val a: Int, val b: Int, val c: Int)

sealed class Opcode {
    abstract fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int

    fun execute(registerInState: RegisterState, instruction: InstructionCode): RegisterState {
        val result = evaluateResult(registerInState, instruction)
        return when (instruction.c) {
            0 -> registerInState.copy(reg0 = result)
            1 -> registerInState.copy(reg1 = result)
            2 -> registerInState.copy(reg2 = result)
            3 -> registerInState.copy(reg3 = result)
            4 -> registerInState.copy(reg4 = result)
            else -> registerInState.copy(reg5 = result)
        }
    }

    fun verifyResult(executionStep: ExecutionStep) =
        verifyResult(executionStep.inState, executionStep.instructionCode, executionStep.outState)

    fun verifyResult(
        registerInState: RegisterState,
        instructionCode: InstructionCode,
        expectedResult: RegisterState
    ): Boolean = (execute(registerInState, instructionCode) == expectedResult)

    companion object {
        val allOpcodes =
            listOf(addi, addr, mulr, muli, banr, bani, bori, borr, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr)
        val oppCodesMapping = mapOf(
            "addi" to addi,
            "addr" to addr,
            "mulr" to mulr,
            "muli" to muli,
            "banr" to banr,
            "bani" to bani,
            "bori" to bori,
            "borr" to borr,
            "setr" to setr,
            "seti" to seti,
            "gtir" to gtir,
            "gtri" to gtri,
            "gtrr" to gtrr,
            "eqir" to eqir,
            "eqri" to eqri,
            "eqrr" to eqrr,
        )
    }

    object addr : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) + registerInState.getRegValue(instruction.b)
    }

    object addi : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) + instruction.b
    }

    object mulr : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) * registerInState.getRegValue(instruction.b)
    }

    object muli : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) * instruction.b
    }

    object banr : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) and registerInState.getRegValue(instruction.b)
    }

    object bani : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) and instruction.b
    }

    object borr : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) or registerInState.getRegValue(instruction.b)
    }

    object bori : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a) or instruction.b
    }

    object setr : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            registerInState.getRegValue(instruction.a)
    }

    object seti : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            instruction.a
    }

    object gtir : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            if (instruction.a > registerInState.getRegValue(instruction.b)) 1 else 0
    }

    object gtri : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            if (registerInState.getRegValue(instruction.a) > instruction.b) 1 else 0
    }

    object gtrr : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            if (registerInState.getRegValue(instruction.a) > registerInState.getRegValue(instruction.b)) 1 else 0
    }

    object eqir : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            if (instruction.a == registerInState.getRegValue(instruction.b)) 1 else 0
    }

    object eqri : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            if (registerInState.getRegValue(instruction.a) == instruction.b) 1 else 0
    }

    object eqrr : Opcode() {
        override fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int =
            if (registerInState.getRegValue(instruction.a) == registerInState.getRegValue(instruction.b)) 1 else 0
    }
}

data class ExecutionStep(val inState: RegisterState, val instructionCode: InstructionCode, val outState: RegisterState)

class OpcodePuzzle(input: List<String>) {
    val executionSteps: List<ExecutionStep>
    val opcodes: Set<Int>
    val opcodesMapping = mutableMapOf<Int, Opcode>()
    val instructions: List<InstructionCode>
    var registerState = RegisterState(0, 0, 0, 0)

    init {
        executionSteps = input
            .takeWhile { !it.startsWith("1 0 0 1") }
            .windowed(3, 4)
            .map { ExecutionStep(parseRegisterState(it[0]), parseInstructionCode(it[1]), parseRegisterState(it[2])) }
        opcodes = executionSteps.map { it.instructionCode.opcode }.toSet()
        instructions = input
            .dropWhile { it != "1 0 0 1" }
            .map { parseInstructionCode(it) }
        mapOpcodes()
    }

    private fun parseRegisterState(input: String): RegisterState {
        val parts = input.substringAfter("[").substringBefore("]").split(", ")
        return RegisterState(parts[0].toInt(), parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
    }

    private fun parseInstructionCode(input: String): InstructionCode {
        val parts = input.split(" ")
        return InstructionCode(parts[0].toInt(), parts[1].toInt(), parts[2].toInt(), parts[3].toInt())
    }

    fun findNumberOfAmbiguousExecutionSteps() = executionSteps.filter { checkExecutionStepAmbiguity(it) }.size

    fun runAllInstructions() {
        instructions.forEach { instruction ->
            opcodesMapping[instruction.opcode]?.execute(registerState, instruction)?.let { registerState = it }
        }
    }

    private fun checkExecutionStepAmbiguity(executionStep: ExecutionStep) =
        Opcode.allOpcodes.filter { it.verifyResult(executionStep) }.size > 2

    private fun mapOpcodes() {
        val pendingOpcodes = opcodes.toMutableSet()
        val opcodesFound = mutableSetOf<Opcode>()
        while (pendingOpcodes.isNotEmpty()) {
            executionSteps.forEach { step ->
                val candidates = Opcode.allOpcodes.filterNot { it in opcodesFound }.filter { it.verifyResult(step) }
                if (candidates.size == 1) {
                    opcodesFound.add(candidates.first())
                    pendingOpcodes.remove(step.instructionCode.opcode)
                    opcodesMapping[step.instructionCode.opcode] = candidates.first()
                }
            }
        }
    }
}