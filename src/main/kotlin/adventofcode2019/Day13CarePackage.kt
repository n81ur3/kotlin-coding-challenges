package adventofcode2019

class Day13CarePackage

class ArcadeCabinet(program: String) : IntComputerObserver {
    val intComputer = IntComputer(program)
    val collectedOutputValues = mutableListOf<Long>()

    fun play() {
        intComputer.outputObserver = this
        intComputer.run(0)
    }

    override fun onOutput(output: Long) {
        collectedOutputValues.add(output)
    }

    fun tilesCountForType(type: Long): Int =
        collectedOutputValues.windowed(3, 3) { w -> w[2] == type }.filter { it }.count()
}