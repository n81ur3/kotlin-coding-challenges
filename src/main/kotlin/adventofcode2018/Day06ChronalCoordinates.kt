package adventofcode2018

class Day06ChronalCoordinates

data class Coordinate(val id: Int, val x: Int, val y: Int) {

    fun manhattanDistance(fromX: Int, fromY: Int): Int {
        val xDist = if (fromX > x) (fromX - x) else (x - fromX)
        val yDist = if (fromY > y) (fromY - y) else (y - fromY)
        return xDist + yDist
    }

    companion object {
        var counter = 0

        fun fromString(input: String): Coordinate {
            return Coordinate(counter++, input.substringBefore(",").toInt(), input.substringAfter(", ").toInt())
        }
    }
}

class CoordinateSystem(input: List<String>) {
    val coordinates: List<Coordinate>
    val fields = mutableListOf<Coordinate>()
    val xMin: Int
    val xMax: Int
    val yMin: Int
    val yMax: Int

    init {
        coordinates = input.map { Coordinate.fromString(it) }
        xMin = coordinates.minOf { it.x }
        xMax = coordinates.maxOf { it.x }
        yMin = coordinates.minOf { it.y }
        yMax = coordinates.maxOf { it.y }
    }

    fun innerCoordinates(): List<Coordinate> {
        return coordinates.filter {
            it.x > xMin && it.x < xMax && it.y > yMin && it.y < yMax
        }
    }

    private fun mapFields() {
        (0..xMax).forEach { x ->
            (0..yMax).forEach { y ->
                findClosestInnerCoordinate(x, y)?.run { fields.add(this) }
            }
        }
    }

    private fun findClosestInnerCoordinate(x: Int, y: Int): Coordinate? {
        val inner = innerCoordinates()
        val distances = coordinates.map { it to it.manhattanDistance(x, y) }.sortedBy { it.second }
            .filter { x >= xMin && x <= xMax && y >= yMin && y <= yMax }
        if (distances.isEmpty()) return Coordinate(-1, x, y)
        if (distances[0].second == distances[1].second) return Coordinate(-1, x, y)
        if (!(inner.contains(distances[0].first))) return Coordinate(-1, x, y)
        return Coordinate(distances[0].first.id, x, y)
    }

    fun calcLargestInnerFieldSize(): Int {
        mapFields()
        val innerFields = fields.filterNot { it.id == -1 }
        val groupedFields = innerFields.groupBy { it.id }
        return groupedFields.filterNot { isInfiniteGroup(it.value) }.maxOf { it.value.size }
    }

    private fun isInfiniteGroup(group: List<Coordinate>) = group.any { outOfBounds(it) }

    private fun outOfBounds(it: Coordinate) = it.x <= xMin || it.x >= xMax || it.y <= yMin || it.y >= yMax

    fun printSystem() {
        (0..yMax).forEach { y ->
            (0..xMax).forEach { x ->
                fields.firstOrNull { it.x == x && it.y == y }?.run {
                    if (id == -1) print('.') else print(id)
                } ?: print('.')
            }
            println()
        }
    }
}