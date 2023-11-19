package adventofcode2015

class Day03SphericalHouses

class NorthVillage {
    var currentPositionX = 0
    var currentPositionY = 0
    var currentRoboPositionX = 0
    var currentRoboPositionY = 0
    var visitedHouses = mutableSetOf(0 to 0)
    val numberOfVisitedHouses: Int
        get() = visitedHouses.size

    fun visit(directions: String) {
        directions.forEach { step(it) }
    }

    fun visitWithRobo(directions: String) {
        directions.forEachIndexed {index, direction ->
            if (index % 2 == 0) step(direction) else roboStep(direction)
        }
    }

    private fun step(direction: Char) {
        when(direction) {
            '<' -> { currentPositionX--; visitedHouses.add(currentPositionX to currentPositionY) }
            '>' -> { currentPositionX++; visitedHouses.add(currentPositionX to currentPositionY) }
            '^' -> { currentPositionY--; visitedHouses.add(currentPositionX to currentPositionY) }
            'v' -> { currentPositionY++; visitedHouses.add(currentPositionX to currentPositionY) }
        }
    }

    private fun roboStep(direction: Char) {
        when(direction) {
            '<' -> { currentRoboPositionX--; visitedHouses.add(currentRoboPositionX to currentRoboPositionY) }
            '>' -> { currentRoboPositionX++; visitedHouses.add(currentRoboPositionX to currentRoboPositionY) }
            '^' -> { currentRoboPositionY--; visitedHouses.add(currentRoboPositionX to currentRoboPositionY) }
            'v' -> { currentRoboPositionY++; visitedHouses.add(currentRoboPositionX to currentRoboPositionY) }
        }
    }
}