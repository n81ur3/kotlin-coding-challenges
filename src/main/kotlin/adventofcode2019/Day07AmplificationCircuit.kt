package adventofcode2019

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Day07AmplificationCircuit

class AmplifierController(program: String) {
    val amplifiers = List(5) { IntComputer(program) }

    fun findLargestOutputSignal(): Long {
        var highestOutputSignal = 0L

        val settings = buildPermutations(0, 4)

        settings.forEach { setting ->
            val outA = amplifiers[0].runWithPhase(setting[0], 0)
            amplifiers[0].reset()
            val outB = amplifiers[1].runWithPhase(setting[1], outA)
            amplifiers[1].reset()
            val outC = amplifiers[2].runWithPhase(setting[2], outB)
            amplifiers[2].reset()
            val outD = amplifiers[3].runWithPhase(setting[3], outC)
            amplifiers[3].reset()
            val outE = amplifiers[4].runWithPhase(setting[4], outD)
            if (outE > highestOutputSignal) highestOutputSignal = outE
            amplifiers[4].reset()
        }
        return highestOutputSignal
    }

    fun findLargestAmplifiedSignal(input: String) = runBlocking {
        val program = input.split(",").map { it.toInt() }.toIntArray()
        listOf(5, 6, 7, 8, 9).permutations().map { runAmplified(program, it) }.max()
            ?: throw IllegalStateException("No max value, something is wrong")
    }

    private suspend fun runAmplified(program: IntArray, settings: List<Int>): Int = coroutineScope {

        val a = IntChannelComputer(program.copyOf(), listOf(settings[0], 0).toChannel())
        val b = IntChannelComputer(program.copyOf(), a.output.andSend(settings[1]))
        val c = IntChannelComputer(program.copyOf(), b.output.andSend(settings[2]))
        val d = IntChannelComputer(program.copyOf(), c.output.andSend(settings[3]))
        val e = IntChannelComputer(program.copyOf(), d.output.andSend(settings[4]))
        val channelSpy = Spy(e.output, a.input)

        coroutineScope {
            launch { channelSpy.listen() }
            launch { a.runSuspending() }
            launch { b.runSuspending() }
            launch { c.runSuspending() }
            launch { d.runSuspending() }
            launch { e.runSuspending() }
        }
        channelSpy.spy.receive()
    }

    private suspend fun <T> Channel<T>.andSend(msg: T): Channel<T> =
        this.also { send(msg) }

    fun buildPermutations(start: Long, end: Long): List<List<Long>> {
        val result = mutableListOf<List<Long>>()

        (start..end).forEach { a ->
            (start..end).forEach { b ->
                (start..end).forEach { c ->
                    (start..end).forEach { d ->
                        (start..end).forEach { e ->
                            val candidate = listOf(a, b, c, d, e)
                            if (isUnique(candidate)) result.add(listOf(a, b, c, d, e))
                        }
                    }
                }
            }
        }

        return result
    }

    fun isUnique(candidate: List<Long>): Boolean {
        val groups = candidate.groupBy { it }
        return groups.values.none { it.size > 1 }
    }
}

fun <T> List<T>.permutations(): List<List<T>> =
    if (this.size <= 1) listOf(this)
    else {
        val elementToInsert = first()
        drop(1).permutations().flatMap { permutation ->
            (0..permutation.size).map { i ->
                permutation.toMutableList().apply { add(i, elementToInsert) }
            }
        }
    }

class Spy<T>(
    private val input: Channel<T>,
    private val output: Channel<T>,
    val spy: Channel<T> = Channel(Channel.CONFLATED)) {

    suspend fun listen() = coroutineScope {
        for (received in input) {
            spy.send(received)
            output.send(received)
        }
    }
}