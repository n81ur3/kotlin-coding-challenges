package adventofcode2017

import adventofcode2020.mod

class Day18Duet

class Duet(private val input: List<String>) {
    val commands: List<AssemblyCommand>
    var pc = 0
    val registers = mutableMapOf<Char, Long>()
    var lastSound = 0L

    init {
        commands = input.map {
            val type = it.substringBefore(" ")
            val instruction = it.substringAfter(" ")
            when (type) {
                "snd" -> SndCommand(instruction)
                "set" -> SetCommand(instruction)
                "add" -> AddCommand(instruction)
                "mul" -> MulCommand(instruction)
                "mod" -> ModCommand(instruction)
                "rcv" -> RcvCommand(instruction)
                else -> JgzCommand(instruction)
            }
        }

        initRegisters()
    }

    private fun initRegisters() {
        commands.forEach { command ->
            val register = command.instruction.split(" ")[0].first()
            if (command !is JgzCommand) {
                registers.put(register, 0)
            }
        }
    }

    fun executeCommands(): Long {
        var command: AssemblyCommand
        var instructionParts: List<String>
        var register: Char
        while (pc < commands.size && pc >= 0) {
            command = commands[pc]
            instructionParts = command.instruction.split(" ")
            register = instructionParts[0].first()
            println("Executing: ${command.instruction}")
            when (command) {
                is SndCommand -> executeSndCommand(register)
                is SetCommand -> executeSetCommand(register, instructionParts)
                is AddCommand -> executeAddCommand(register, instructionParts)
                is MulCommand -> executeMulCommand(register, instructionParts)
                is ModCommand -> executeModCommand(register, instructionParts)
                is RcvCommand -> {
                    val result = executeRcvCommand(register)
                    if (result != 0L) return result
                }

                is JgzCommand -> executeJgzCommand(register, instructionParts)
            }
        }
        return 0
    }

    private fun executeSndCommand(register: Char) {
        registers[register]?.let {
            lastSound = it
        }
        pc++
    }

    private fun executeSetCommand(register: Char, instruction: List<String>) {
        val newValue: Long
        val newValueString = instruction[1]
        if (newValueString.last().isDigit()) {
            newValue = newValueString.toLong()
        } else {
            newValue = registers[newValueString.first()] ?: 0
        }
        registers[register]?.let {
            registers.put(register, newValue)
        }
        pc++
    }

    private fun executeAddCommand(register: Char, instruction: List<String>) {
        val increment: Long
        val incrementString = instruction[1]
        if (incrementString.last().isDigit()) {
            increment = incrementString.toLong()
        } else {
            increment = registers[incrementString.first()] ?: 0
        }
        val newValue = registers[register]?.plus((increment)) ?: 0
        registers.put(register, newValue)
        pc++
    }

    private fun executeMulCommand(register: Char, instruction: List<String>) {
        val multiplyer: Long
        val mulString = instruction[1]
        if (mulString.last().isDigit()) {
            multiplyer = mulString.toLong()
        } else {
            multiplyer = registers[mulString.first()] ?: 0
        }

        registers[register]?.let {
            val newValue = registers[register]?.times(multiplyer) ?: 1
            registers.put(register, newValue)
        }
        pc++
    }

    private fun executeModCommand(register: Char, instruction: List<String>) {
        val divider: Long
        val divString = instruction[1]
        if (divString.last().isDigit()) {
            divider = divString.toLong()
        } else {
            divider = registers[divString.first()] ?: 0L
        }
        if (divider != 0L) {
            registers[register]?.let {
                val registerValue = registers[register]
                if (registerValue != null) {
                    val newValue = Math.floorMod(registerValue, divider)
                    registers.put(register, newValue)
                }
            }
        }
        pc++
    }

    private fun executeRcvCommand(register: Char): Long {
        val registerValue = registers[register] ?: 0
        if (registerValue != 0L) {
            println("Recovering $lastSound")
            pc++
            return lastSound
        }
        pc++
        return 0
    }

    private fun executeJgzCommand(register: Char, instruction: List<String>) {
        val jump: Long
        val jgzString = instruction[0]
        if (jgzString.last().isDigit()) {
            jump = jgzString.toLong()
        } else {
            jump = registers[jgzString.first()] ?: 0L
        }

        if (jump > 0) {
            val offset = instruction[1].toLong()
            if (offset < 0L) {
                pc += offset.toInt()
            } else {
                pc += offset.toInt()
                pc++
            }
        } else {
            pc++
        }
    }

}

sealed class AssemblyCommand(val instruction: String)
class SndCommand(instruction: String) : AssemblyCommand(instruction)
class SetCommand(instruction: String) : AssemblyCommand(instruction)
class AddCommand(instruction: String) : AssemblyCommand(instruction)
class MulCommand(instruction: String) : AssemblyCommand(instruction)
class ModCommand(instruction: String) : AssemblyCommand(instruction)
class RcvCommand(instruction: String) : AssemblyCommand(instruction)
class JgzCommand(instruction: String) : AssemblyCommand(instruction)