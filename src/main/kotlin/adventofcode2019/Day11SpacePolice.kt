package adventofcode2019

class Day11SpacePolice

data class PaintingCoordinate(val x: Int, val y: Int)

sealed class RobotMode {
    object PAINTING: RobotMode()
    object MOVING: RobotMode()
}

sealed class RobotDirection {
    object NORTH: RobotDirection()
    object EAST: RobotDirection()
    object SOUTH: RobotDirection()
    object WEST: RobotDirection()
}

class PaintingRobot(intProgram: String) : IntComputerObserver {
    private var xPos = 0
    private var yPos = 0
    private var direction: RobotDirection = RobotDirection.NORTH
    private val intComputer = IntComputer(intProgram)
    private val paintingPoints = mutableMapOf<PaintingCoordinate, Int>()
    private var robotMode: RobotMode = RobotMode.PAINTING
    val paintedPanelsCount: Int
        get() = paintingPoints.size

    init {
        intComputer.setInput(1)
        intComputer.outputObserver = this
    }

    fun paint(startInstruction: Long) {
        if (startInstruction == 1L) paintingPoints[currentPosition()] = 1
        intComputer.run(startInstruction)
    }

    fun paintRegistrationIdentifier() {
        val minX = paintingPoints.keys.minOf { it.x }
        val maxX = paintingPoints.keys.maxOf { it.x }
        val minY = paintingPoints.keys.minOf { it.y }
        val maxY = paintingPoints.keys.maxOf { it.y }

        (minY..maxY).forEach { y ->
            (minX .. maxX).forEach { x->
                paintingPoints[PaintingCoordinate(x, y)]?.let {
                    if (it == 1) print("·") else print(" ")
                } ?: print(" ")
            }
            println()
        }
    }

    override fun onOutput(output: Long) {
        if (robotMode == RobotMode.PAINTING) {
            paintingPoints[currentPosition()] = output.toInt()
            robotMode = RobotMode.MOVING
        } else {
            move(output.toInt())
            robotMode = RobotMode.PAINTING

            val nextInput = paintingPoints[currentPosition()]?.let {
                if (it == 1) 1L else 0L
            } ?: 0L
            intComputer.setInput(nextInput)

        }
    }

    private fun currentPosition(): PaintingCoordinate = PaintingCoordinate(xPos, yPos)

    private fun move(instruction: Int) {
        rotate(instruction)
        when (direction) {
            RobotDirection.NORTH -> yPos--
            RobotDirection.EAST -> xPos++
            RobotDirection.SOUTH -> yPos++
            RobotDirection.WEST -> xPos--
        }
    }

    private fun rotate(instruction: Int) {
        direction = when (direction) {
            RobotDirection.NORTH -> {
                if (instruction == 0) RobotDirection.WEST else RobotDirection.EAST
            }

            RobotDirection.EAST -> {
                if (instruction == 0) RobotDirection.NORTH else RobotDirection.SOUTH
            }

            RobotDirection.SOUTH -> {
                if (instruction == 0) RobotDirection.EAST else RobotDirection.WEST
            }

            RobotDirection.WEST -> {
                if (instruction == 0) RobotDirection.SOUTH else RobotDirection.NORTH
            }
        }
    }
}