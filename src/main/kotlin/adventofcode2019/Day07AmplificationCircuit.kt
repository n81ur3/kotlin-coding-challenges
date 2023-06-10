package adventofcode2019

class Day07AmplificationCircuit

class AmplifierController(program: String) {
    val amplifiers = List(5) { IntComputer(program) }

    fun findLargestOutputSignal(): Int {
        var highestOutputSignal = 0

        val settings = buildPermutations()

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

    fun buildPermutations(): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        (0..4).forEach { a ->
            (0..4).forEach { b ->
                (0..4).forEach { c ->
                    (0..4).forEach { d ->
                        (0..4).forEach { e ->
                            val candidate = listOf(a, b, c, d, e)
                            if (isUnique(candidate)) result.add(listOf(a, b, c, d, e))
                        }
                    }
                }
            }
        }

        return result
    }

    fun isUnique(candidate: List<Int>): Boolean {
        val groups = candidate.groupBy { it }
        return groups.values.none { it.size > 1 }
    }
}