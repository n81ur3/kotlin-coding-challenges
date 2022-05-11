package adventofcode2020

class Interpreter(var pc: Int = 0, var acc: Int = 0, val maxPc: Int) {
    var encounteredLines = mutableListOf<Int>()
    var tmpPc = 0
    private var tmpAcc = 0
    private var tmpEncounteredLines = mutableListOf<Int>()

    fun readLine(input: String): Boolean {
        val (opcode, value) = parseLine(input)
        if (checkForLoop()) {
            return executeOpCode(opcode, value)
        }
        return false
    }

    fun saveState() {
        tmpPc = pc
        tmpAcc = acc
        tmpEncounteredLines.clear()
        tmpEncounteredLines.addAll(encounteredLines)
    }

    fun resetState() {
        pc = tmpPc
        acc = tmpAcc
        encounteredLines.clear()
        encounteredLines.addAll(tmpEncounteredLines)
    }

    private fun executeOpCode(opcode: String, value: Int): Boolean {
        when (opcode) {
            "acc" -> {
                acc += value
                pc++
            }
            "jmp" -> pc += value
            else -> pc++
        }
        return true
    }

    private fun parseLine(input: String): Pair<String, Int> {
        val parts = input.split(" ")
        val value = if (parts[1].startsWith("+")) parts[1].substring(1).toInt() else parts[1].substring(1).toInt() * -1
        return Pair(parts[0], value)
    }

    private fun checkForLoop(): Boolean {
        if (encounteredLines.contains(pc)) {
//             println("Infinite Loop at pc: $pc, acc=$acc")
            return false
        } else encounteredLines.add(pc)
        return true
    }
}