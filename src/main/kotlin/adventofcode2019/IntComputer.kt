package adventofcode2019

import kotlinx.coroutines.channels.Channel

class IntComputer(program: String) {
    var memory = mutableMapOf<Long, Long>()
    val initialState = mutableMapOf<Long, Long>()
    var ip = 0L
    var inputCounter = 0L
    var singleInput = 0L
    var secondInput = 0L
    var output = 0L
    var directPointer = false
    var relativeBase = 0L
    var outputObserver: IntComputerObserver? = null

    init {
        program.split(",").forEachIndexed { index, element ->
            initialState[index.toLong()] = element.toLong()
            memory[index.toLong()] = element.toLong()
        }
    }

    fun run(input: Long): Long {
        singleInput = input
        while ((memory.getOrDefault(ip, 0) % 100L) != 99L) {
            val increment = this.execute(memory.getOrDefault(ip, 0))
            if (!directPointer) {
                ip += increment
            }
        }
        return output
    }

    fun reset() {
        memory = initialState.toMutableMap()
        inputCounter = 0L
        singleInput = 0L
        secondInput = 0L
        output = 0L
        ip = 0L
        directPointer = false
    }

    fun runWithPhase(phase: Long, input: Long): Long {
        secondInput = input
        var result = 0L
        try {
            result = run(phase)
        } catch (exc: IndexOutOfBoundsException) {
            println("Index out of bounds")
        }
        return result
    }


    private fun executeInstruction(
        instruction: Long,
        op1: Long = 0L,
        op2: Long = 0L,
        op3: Long = 0L,
        increment: Long
    ): Long {
        directPointer = false
        when (instruction) {
            1L -> {
                memory[op3] = op1 + op2
            }

            2L -> {
                memory[op3] = op1 * op2
            }

            3L -> {
                if (inputCounter > 0) {
                    singleInput = secondInput
                }
                inputCounter++
                memory[op1] = singleInput
            }

            4L -> {
//                println("output: ${op1}")
                output = op1
                outputObserver?.run { onOutput(output) }
            }

            5L -> {
                if (op1 != 0L) {
                    ip = op2
                    directPointer = true
                }
            }

            6L -> {
                if (op1 == 0L) {
                    ip = op2
                    directPointer = true
                }
            }

            7L -> {
                if (op1 < op2) {
                    memory[op3] = 1
                } else {
                    memory[op3] = 0
                }
            }

            8L -> {
                if (op1 == op2) {
                    memory[op3] = 1
                } else {
                    memory[op3] = 0
                }
            }

            9L -> {
                relativeBase += op1
            }

            else -> throw IllegalArgumentException("Illegal opcode")
        }

        return if (directPointer) 0L else increment
    }

    private fun execute(instruction: Long): Long {
        val opcode = instruction % 10
        var increment = 2L

        increment = if (opcode in listOf(1L, 2L, 7L, 8L)) 4L else if (opcode in listOf(3L, 4L, 9L)) 2L else 3L
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

        val op1 =
            when (mode1) {
                1L -> {
                    when (opcode) {
                        in listOf(3L) -> {
                            ip + 1
                        }

                        else -> {
                            memory.getOrDefault(ip + 1, 0)
                        }
                    }
                }

                2L -> {
                    when (opcode) {
                        3L -> {
                            memory.getOrDefault(ip + 1, 0) + relativeBase
                        }

                        4L -> {
                            memory.getOrDefault(memory.getOrDefault(ip + 1, 0) + relativeBase, 0)
                        }

                        9L -> {
                            memory.getOrDefault(memory.getOrDefault(ip + 1, 0) + relativeBase, 0)
                        }

                        else -> {
                            memory.getOrDefault(memory.getOrDefault(ip + 1, 0) + relativeBase, 0)
                        }
                    }
                }

                else -> {
                    when (opcode) {
                        in listOf(3L) -> {
                            memory.getOrDefault(ip + 1, 0)
                        }

                        else -> {
                            memory.getOrDefault(memory.getOrDefault(ip + 1, 0), 0)
                        }
                    }
                }
            }
        val op2 =
            when (mode2) {
                1L -> memory.getOrDefault(ip + 2, 0)
                2L -> memory.getOrDefault(memory.getOrDefault(ip + 2, 0) + relativeBase, 0)
                else -> memory.getOrDefault(memory.getOrDefault(ip + 2, 0), 0)
            }

        val op3 =
            when (mode3) {
                1L -> ip + 3
                2L -> memory.getOrDefault(ip + 3, 0) + relativeBase
                else -> memory.getOrDefault(ip + 3, 0)
            }

        return executeInstruction(opcode, op1, op2, op3, increment)
    }

    fun setInput(inputCode: Long) {
        singleInput = inputCode
        secondInput = inputCode
        inputCounter = 0L
    }
}

interface IntComputerObserver {
    fun onOutput(output: Long)
}
