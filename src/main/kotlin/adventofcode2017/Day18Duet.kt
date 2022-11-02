package adventofcode2017

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class Day18Duet

enum class ProgramStatus {
    RUNNING,
    BLOCKED,
    TERMINATED
}

class Duet(instructions: List<String>) {
    val channelZeroToOne = Channel<Long>(Channel.UNLIMITED)
    val channelOneToZero = Channel<Long>(Channel.UNLIMITED)
    val programZero =
        DuetProgram(instructions, channelZeroToOne, channelOneToZero, programId = 0L)
    val programOne =
        DuetProgram(instructions, channelOneToZero, channelZeroToOne, programId = 1L)

    suspend fun run(): Int {
        CoroutineScope(Dispatchers.IO).launch { programZero.executeCommands() }
        val jobOne = CoroutineScope(Dispatchers.Default).async { programOne.executeCommands() }
        jobOne.join()
        return programOne.sendCount
    }
}

class DuetProgram(
    input: List<String>,
    val sendChannel: Channel<Long>,
    val receiveChannel: Channel<Long>,
    var programStatus: ProgramStatus = ProgramStatus.RUNNING,
    val programId: Long,
) {
    val commands: List<AssemblyCommand>
    var pc = 0
    val registers = mutableMapOf<Char, Long>()
    var sendCount = 0

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
                registers.put(register, 0L)
            }
        }
        registers['p'] = programId
        registers['1'] = 1
    }

    suspend fun executeCommands() {
        var command: AssemblyCommand
        var instructionParts: List<String>
        var register: Char
        while (pc < commands.size && pc >= 0 && programStatus == ProgramStatus.RUNNING) {
            command = commands[pc]
            instructionParts = command.instruction.split(" ")
            register = instructionParts[0].first()
            when (command) {
                is SndCommand -> executeSndCommand(register)
                is SetCommand -> executeSetCommand(register, instructionParts)
                is AddCommand -> executeAddCommand(register, instructionParts)
                is MulCommand -> executeMulCommand(register, instructionParts)
                is ModCommand -> executeModCommand(register, instructionParts)
                is RcvCommand -> executeRcvCommand(register)
                is JgzCommand -> executeJgzCommand(register, instructionParts)
            }
        }
        println("Program $programId send count: $sendCount")
        programStatus = ProgramStatus.TERMINATED
    }

    private suspend fun executeSndCommand(sendValue: Char) {
        val newValue: Long
        if (sendValue.isDigit()) {
            newValue = sendValue.code.toLong() - 49
        } else {
            newValue = registers[sendValue] ?: 0L
        }
        programStatus = ProgramStatus.BLOCKED
        try {
            withTimeout(20000000L) {
                sendChannel.send(newValue)
                sendCount++
                programStatus = ProgramStatus.RUNNING
            }
        } catch (exception: Exception) {
            programStatus = ProgramStatus.TERMINATED
            println("timeout in program $programId: $exception snd")
        }
        pc++
    }

    suspend private fun executeRcvCommand(register: Char) {
        programStatus = ProgramStatus.BLOCKED
        try {
            withTimeout(2000L) {
                val newValue = receiveChannel.receive()
                programStatus = ProgramStatus.RUNNING
                registers[register] = newValue
            }
        } catch (exception: Exception) {
            programStatus = ProgramStatus.TERMINATED
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
            val newValue = registers[register]?.times(multiplyer) ?: 0
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

    private fun executeJgzCommand(register: Char, instruction: List<String>) {
        if ((registers[register] ?: 0L) > 0L) {
            val offset: Long
            if (instruction[1].last().isDigit()) {
                offset = instruction[1].toLong()
            } else {
                offset = registers[register] ?: 0L
            }
            pc += offset.toInt()
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