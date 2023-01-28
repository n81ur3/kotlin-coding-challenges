package adventofcode2018

class Day16ChronalClassification

data class RegisterState(val reg0: Int, val reg1: Int, val reg2: Int, val reg3: Int) {
    fun getRegValue(reg: Int) = when (reg) {
        0 -> reg0
        1 -> reg1
        2 -> reg2
        else -> reg3
    }
}

data class InstructionCode(val opcode: Int, val a: Int, val b: Int, val c: Int)

sealed class Opcode {
    abstract fun evaluateResult(registerInState: RegisterState, instruction: InstructionCode): Int

    fun execute(registerInState: RegisterState, instruction: InstructionCode): RegisterState {
        val result = evaluateResult(registerInState, instruction)
        return when (instruction.c) {
            0 -> registerInState.copy(reg0 = result)
            1 -> registerInState.copy(reg1 = result)
            2 -> registerInState.copy(reg2 = result)
            else -> registerInState.copy(reg3 = result)
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
        val allOpcodes = listOf(
            addi,
            addr,
            mulr,
            muli,
            banr,
            bani,
            bori,
            borr,
            setr,
            seti,
            gtir,
            gtri,
            gtrr,
            eqir,
            eqri,
            eqrr
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

    init {
        executionSteps = input
            .takeWhile { !it.startsWith("1 0 0 1") }
            .windowed(3, 4)
            .map { ExecutionStep(parseRegisterState(it[0]), parseInstructionCode(it[1]), parseRegisterState(it[2])) }
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

    private fun checkExecutionStepAmbiguity(executionStep: ExecutionStep) =
        Opcode.allOpcodes.filter { it.verifyResult(executionStep) }.size > 2
}