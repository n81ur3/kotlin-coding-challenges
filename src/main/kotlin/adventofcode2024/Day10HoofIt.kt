package adventofcode2024

data class Stepstone(
    val x: Int,
    val y: Int,
    val height: Int
) {
    fun isNeighbor(other: Stepstone): Boolean {
        return (height == other.height - 1)
                && ((x == other.x - 1 && y == other.y)
                || (x == other.x + 1 && y == other.y)
                || (y == other.y - 1 && x == other.x)
                || (y == other.y + 1 && x == other.x))
    }
}

class Pathfinder(
    val input: List<String>
) {
    val stepstones: List<Stepstone>
    val trailheads: List<Stepstone>
    val width = input[0].length - 1
    val height = input.size - 1
    val trailPaths = mutableMapOf<Stepstone, MutableSet<Stepstone>>()

    init {
        stepstones = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, entry -> Stepstone(x, y, entry.digitToInt()) }
        }
        trailheads = stepstones.filter { it.height == 0 }
        trailheads.forEach { trailPaths[it] = mutableSetOf<Stepstone>() }
    }

    fun printMap() {
        (0..height).forEach { y ->
            (0..width).forEach { x ->
                print(stepstones.find { it.x == x && it.y == y }?.height)
            }
            println()
        }
    }

    fun findPaths(): Int {
        trailheads.forEach { trailhead ->
            val neighbors = getNeighbors(trailhead)
            neighbors.forEach { findPath(it, trailhead) }
        }
        return trailPaths.values.sumOf { it.size }
    }

    private fun findPath(nextStep: Stepstone, trailhead: Stepstone) {
        if (nextStep.height == 9) {
            trailPaths[trailhead]!!.add(nextStep)
            return
        }
        getNeighbors(nextStep).forEach { findPath(it, trailhead) }
    }

    fun getNeighbors(stepstone: Stepstone): List<Stepstone> {
        return stepstones.filter { stepstone.isNeighbor(it) }
    }
}