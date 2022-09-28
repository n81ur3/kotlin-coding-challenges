package adventofcode2017

class Day16PermutationPromenade

class Dance(instructions: String, numberOfPrograms: Int = 16) {
    val commands: List<Command>
    val programs = MutableList(numberOfPrograms) { (it + 97).toChar() }

    init {
        commands = instructions.split(",").map { parseCommand(it) }
    }

    private fun parseCommand(instruction: String): Command {
        return when (instruction.first()) {
            's' -> SpinCommand(instruction)
            'x' -> ExchangeCommand(instruction)
            else -> PartnerCommand(instruction)
        }
    }

    fun perform() {
        commands.forEach { command -> command.execute(programs) }
    }

    fun getProgramsAsString(): String = programs.joinToString(separator = "")
}

interface Command {
    fun execute(programs: MutableList<Char>)
}

data class SpinCommand(val instruction: String) : Command {

    override fun execute(programs: MutableList<Char>) {
        val numberOfProgrammsToMove = instruction.substring(1).toInt()
        val programsToMove = programs.drop(programs.size - numberOfProgrammsToMove)
        programs.removeAll(programsToMove)
        programs.addAll(0, programsToMove)
    }
}

data class ExchangeCommand(val instruction: String) : Command {

    override fun execute(programs: MutableList<Char>) {
        val firstIndex = instruction.substringBefore("/").drop(1).toInt()
        val secondIndex = instruction.substringAfter("/").toInt()
        val tmp = programs[firstIndex]
        programs[firstIndex] = programs[secondIndex]
        programs[secondIndex] = tmp
    }
}

data class PartnerCommand(val instruction: String) : Command {

    override fun execute(programs: MutableList<Char>) {
        val firstProgram = instruction.substringBefore("/").last()
        val secondProgram = instruction.substringAfter("/").first()
        val firstIndex = programs.indexOf(firstProgram)
        val secondIndex = programs.indexOf(secondProgram)
        val tmp = programs[firstIndex]
        programs[firstIndex] = programs[secondIndex]
        programs[secondIndex] = tmp
    }
}