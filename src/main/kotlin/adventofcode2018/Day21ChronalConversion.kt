package adventofcode2018

class Day21ChronalConversion

class TimeTravelModule(val instructions: List<String>) {
    val codeExecutor = OpcodeExecutor(instructions)

    fun executeCode(maxLoops: Int) {
        codeExecutor.execute(maxLoops)
    }
}