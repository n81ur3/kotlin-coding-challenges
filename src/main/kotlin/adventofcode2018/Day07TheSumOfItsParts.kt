package adventofcode2018

class Day07TheSumOfItsParts

enum class Step {
    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
}

data class Instruction(val step: Step, val preSteps: MutableSet<Step> = mutableSetOf())

data class ConcurrentWorker(var executionTime: Int = 0, var busy: Boolean = false) {
    fun isAvailable(processTime: Int) = executionTime <= processTime
}

class SleighProcessor(input: List<String>, private val numberOfSteps: Int) {
    val instructions: MutableList<Instruction>

    init {
        instructions = Step.values().take(numberOfSteps).map { Instruction(it) }.toMutableList()
        parseInput(input)
    }

    private fun parseInput(input: List<String>) {
        input.forEach { instruction ->
            val step = Step.valueOf(instruction.substringAfter("step ").substring(0, 1))
            val preStep = Step.valueOf(instruction.substringAfter("Step ").substring(0, 1))
            instructions.find { it.step == step }?.preSteps?.add(preStep)
        }
    }

    fun executeInstructions(): String {
        return buildString {
            while (instructions.isNotEmpty()) {
                val nextInstruction = instructions.first { it.preSteps.isEmpty() }
                append(nextInstruction.step)
                instructions.forEach { instruction -> instruction.preSteps.remove(nextInstruction.step) }
                instructions.remove(nextInstruction)
            }
        }
    }

    fun executeConcurrently(numberOfWorkers: Int): Int {
        var totalTime = 0
        var maxTime = 0
        val workers = List(numberOfWorkers) { ConcurrentWorker() }

        while (instructions.isNotEmpty()) {
            var subTime = 0
            val pendingInstructions = instructions.filter { it.preSteps.isEmpty() }
            pendingInstructions.forEach { nextInstruction ->
                val availableWorker = workers.firstOrNull { !(it.busy) } ?: workers.minBy { it.executionTime }
                val currentExecutionTime = nextInstruction.step.ordinal + 1
                availableWorker.executionTime = totalTime + subTime + currentExecutionTime


                subTime += currentExecutionTime

                instructions.forEach { instruction -> instruction.preSteps.remove(nextInstruction.step) }
                instructions.remove(nextInstruction)


                workers.forEach { worker ->
                    worker.busy = worker.executionTime >= totalTime + subTime + 1
                }
            }
            totalTime += subTime
        }

        println("total time: $totalTime")


        return workers.maxOf { it.executionTime }
    }
}