package adventofcode2019

class Day13CarePackage

class ArcadeCabinet(program: String) : IntComputerObserver {
    val intComputer = IntComputer(program)
    val collectedOutputValues = mutableListOf<Long>()
    var currentScore = 0L
    var segmentMode = false
    var outputLoop = 0
    var currentX = 0L
    var currentPaddleX = 0L

    fun play() {
        intComputer.outputObserver = this
        intComputer.run(0)
    }

    override fun onOutput(output: Long) {
        collectedOutputValues.add(output)

        if (segmentMode && outputLoop == 1) {
            outputLoop++
            return
        }

        if (segmentMode && outputLoop == 2) currentScore = output

        if (!segmentMode && outputLoop == 0) currentX = output

        if (!segmentMode && outputLoop == 2 && output == 3L) currentPaddleX = currentX

        if (!segmentMode && outputLoop == 2 && output == 4L) {
            if (currentX < currentPaddleX) {
                intComputer.setInput(-1)
            } else if (currentX > currentPaddleX) {
                intComputer.setInput(1)
            } else {
                intComputer.setInput(0)
            }
        }

        segmentMode = if (outputLoop == 0 && output == -1L) true else false

        outputLoop = (outputLoop + 1) % 3
    }

    fun tilesCountForType(type: Long): Int =
        collectedOutputValues.windowed(3, 3) { w -> w[2] == type }.filter { it }.count()

    fun enableFreePlay() {
        intComputer.memory[0] = 2
    }
}