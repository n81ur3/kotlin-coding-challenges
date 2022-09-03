package adventofcode2017

import adventofcode2017.Comparator.*
import adventofcode2017.RegisterOperation.*

class Day08LikeRegisters

enum class RegisterOperation {
    INC, DEC
}

enum class Comparator {
    LT, LET, EQ, NE, GET, GT
}

data class Register(val name: String, var value: Int = 0) {
    fun inc(incValue: Int) {
        value += incValue
    }

    fun dec(decValue: Int) {
        value -= decValue
    }
}

data class Instruction(val register: String, val operation: RegisterOperation, val value: Int, val condition: String) {
    val comparator: Comparator
    val compareValue: Int
    val registerToCheck: String

    init {
        val parts = condition.split(" ")
        registerToCheck = parts[0]
        compareValue = parts[2].toInt()
        val comp = parts[1]
        comparator = when {
            comp == "<" -> LT
            comp == "<=" -> LET
            comp == "==" -> EQ
            comp == "!=" -> NE
            comp == ">=" -> GET
            else -> GT
        }
    }

    companion object {
        fun fromLineInput(input: String): Instruction {
            // Example: vk inc 880 if pnt == 428
            val parts = input.split(" ")
            val operation = if (parts[1] == "inc") INC else DEC
            return Instruction(parts[0], operation, parts[2].toInt(), input.substringAfter("if "))
        }
    }

    fun evaluate(registerValue: Int) = when (comparator) {
        LT -> registerValue < compareValue
        LET -> registerValue <= compareValue
        EQ -> registerValue == compareValue
        NE -> registerValue != compareValue
        GET -> registerValue >= compareValue
        GT -> registerValue > compareValue
    }
}

class RegisterMachine {
    private val instructions = mutableListOf<Instruction>()
    private val registers = mutableListOf<Register>()
    var registerMax = 0
    val highestRegister: Int
        get() = registers.maxByOrNull { it.value }?.value ?: 0

    fun readInstructions(input: List<String>) {
        input.forEach { instructions.add(Instruction.fromLineInput(it)) }
        instructions.forEach { registers.add(Register(it.register)) }
    }

    fun runInstructions() {
        instructions.forEach { instruction ->
            val registerName = instruction.register
            val registerToCheckName = instruction.registerToCheck
            val (register, registerToCheck) = findRegisters(registerName, registerToCheckName)
            if (checkCondition(registerToCheck, instruction)) {
                register?.run {
                    when (instruction.operation) {
                        INC -> inc(instruction.value)
                        DEC -> dec(instruction.value)
                    }
                    if (value > registerMax) registerMax = value
                }
            }
        }
    }

    private fun checkCondition(registerToCheck: Register?, instruction: Instruction): Boolean {
        return registerToCheck?.let {
            instruction.evaluate(registerToCheck.value)
        } ?: false
    }

    private fun findRegisters(registerName: String, registerToCheckName: String): Pair<Register?, Register?> {
        val register = registers.find { it.name == registerName }
        val registerToCheck = registers.find { it.name == registerToCheckName }
        return register to registerToCheck
    }
}