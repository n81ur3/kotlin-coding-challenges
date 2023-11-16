package adventofcode2015

class Day03SphericalHouses

class NorthVillage {
    var currentPositionX = 0
    var currentPositionY = 0
    var visitedHouses = mutableSetOf(0 to 0)
    val numberOfVisitedHouses: Int
        get() = visitedHouses.size

    fun visit(directions: String) {
        directions.forEach { step(it) }
    }

    private fun step(direction: Char) {
        when(direction) {
            '<' -> { currentPositionX--; visitedHouses.add(currentPositionX to currentPositionY) }
            '>' -> { currentPositionX++; visitedHouses.add(currentPositionX to currentPositionY) }
            '^' -> { currentPositionY--; visitedHouses.add(currentPositionX to currentPositionY) }
            'v' -> { currentPositionY++; visitedHouses.add(currentPositionX to currentPositionY) }
        }
    }
}