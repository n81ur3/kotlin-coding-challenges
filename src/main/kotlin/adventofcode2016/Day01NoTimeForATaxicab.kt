package adventofcode2016

import kotlin.math.abs

class NoTimeForATaxicab {
    val robot = Robot()

    fun moveRobot(movement: List<String>) {
        movement.forEach { m ->
            val dir = m[0]
            val steps = m.substring(1).toInt()
            if (dir == 'R') robot.turnRight() else robot.turnLeft()
            robot.moveForward(steps)
        }
    }

    fun getRobotDistance() = abs(robot.posX) + abs(robot.posY)

    fun findFirstDoubleVisitDistance(directions: List<String>): Int {
        val alreadyVisited = mutableListOf<Pair<Int, Int>>()

        directions.forEach { m ->
            val dir = m[0]
            val steps = m.substring(1).toInt()
            if (dir == 'R') robot.turnRight() else robot.turnLeft()
            repeat(steps) {
                robot.moveForward()
                if (alreadyVisited.contains(Pair(robot.posX, robot.posY))) {
                    return getRobotDistance()
                } else {
                    alreadyVisited.add(Pair(robot.posX, robot.posY))
                }
            }
        }

        return -1
    }
}

class Robot(
    var posX: Int = 0,
    var posY: Int = 0,
    var direction: Direction = Direction.North
) {
    fun moveForward(steps: Int = 1) {
        when (direction) {
            Direction.North -> posY -= steps
            Direction.East -> posX += steps
            Direction.South -> posY += steps
            Direction.West -> posX -= steps
        }
    }

    fun turnRight() {
        direction = direction.turnRight()
    }

    fun turnLeft() {
        direction = direction.turnLeft()
    }
}

sealed class Direction {
    abstract fun turnRight(): Direction
    abstract fun turnLeft(): Direction

    object North : Direction() {
        override fun turnRight() = Direction.East
        override fun turnLeft() = Direction.West
    }

    object East : Direction() {
        override fun turnRight() = Direction.South
        override fun turnLeft() = Direction.North
    }

    object South : Direction() {
        override fun turnRight() = Direction.West
        override fun turnLeft() = Direction.East
    }

    object West : Direction() {
        override fun turnRight() = Direction.North
        override fun turnLeft() = Direction.South
    }
}