package adventofcode2017

class Day12DigitalPlumber

data class PlumbProgram(val id: Int, val pipes: MutableList<Int>)

class PlumbParser(input: List<String>) {
    val programms: MutableList<PlumbProgram>

    init {
        programms = input.map { parseLine(it) }.toMutableList()
    }

    private fun parseLine(input: String): PlumbProgram {
        val id = input.substringBefore(" <").toInt()
        val pipeList = input.substringAfter("> ").split(", ").map { it.toInt() }.toMutableList()
        return PlumbProgram(id, pipeList)
    }

    fun getZeroIdGroupSize(): Int =
        programms.filter { it.id == 0 || containsZeroIdProgram(it.pipes, mutableListOf()) }.count()

    fun containsZeroIdProgram(programIds: List<Int>, alreadyCheckedIds: MutableList<Int>): Boolean {
        if (programIds.isEmpty()) return false
        programIds.forEach { programId ->
            val currentProgram = programms.find { it.id == programId } ?: return false
            alreadyCheckedIds.add(currentProgram.id)
            if (currentProgram.pipes.any { it == 0 }) {
                return true
            } else {
                return programIds.any { pipeP ->
                    val pipeProgram = programms.find { it.id == pipeP } ?: return false
                    containsZeroIdProgram(
                        pipeProgram.pipes.filterNot { alreadyCheckedIds.contains(it) },
                        alreadyCheckedIds
                    )
                }
            }
        }
        return false
    }
}