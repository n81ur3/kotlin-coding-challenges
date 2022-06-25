package adventofcode2020

class Day17ConwayCubes

enum class CubeState {
    ACTIVE,
    INACTIVE
}

data class Cube(var x: Int, var y: Int, val z: Int, var state: CubeState = CubeState.INACTIVE) {
    override fun toString(): String = "[$x/$y/$z]"
}

class ConwayCube(val activeCubes: MutableList<Cube>) {
    val minX: Int
        get() = activeCubes.minByOrNull { cube -> cube.x }?.x ?: 0
    val maxX: Int
        get() = activeCubes.maxByOrNull { cube -> cube.x }?.x ?: 0
    val minY: Int
        get() = activeCubes.minByOrNull { cube -> cube.y }?.y ?: 0
    val maxY: Int
        get() = activeCubes.maxByOrNull { cube -> cube.y }?.y ?: 0
    val minZ: Int
        get() = activeCubes.minByOrNull { cube -> cube.z }?.z ?: 0
    val maxZ: Int
        get() = activeCubes.maxByOrNull { cube -> cube.z }?.z ?: 0
    val numberOfActiveCubes
        get() = activeCubes.size

    companion object {
        fun fromLines(initialRows: List<String>): ConwayCube {
            val initialCubes = mutableListOf<Cube>()
            initialRows.forEachIndexed { yIndex, row ->
                row.forEachIndexed { xIndex, char ->
                    if (char == '#') {
                        initialCubes.add(
                            Cube(
                                xIndex,
                                yIndex,
                                0,
                                CubeState.ACTIVE
                            )
                        )
                    }
                }
            }
            return ConwayCube(initialCubes)
        }
    }

    fun getActiveNeighborCountAt(x: Int, y: Int, z: Int): Int {
        val offsets = listOf(
            Triple(-1, -1, 1),
            Triple(0, -1, 1),
            Triple(1, -1, 1),
            Triple(-1, 0, 1),
            Triple(0, 0, 1),
            Triple(1, 0, 1),
            Triple(-1, 1, 1),
            Triple(0, 1, 1),
            Triple(1, 1, 1),
            Triple(-1, -1, 0),
            Triple(0, -1, 0),
            Triple(1, -1, 0),
            Triple(-1, 0, 0),
            Triple(1, 0, 0),
            Triple(-1, 1, 0),
            Triple(0, 1, 0),
            Triple(1, 1, 0),
            Triple(-1, -1, -1),
            Triple(0, -1, -1),
            Triple(1, -1, -1),
            Triple(-1, 0, -1),
            Triple(0, 0, -1),
            Triple(1, 0, -1),
            Triple(-1, 1, -1),
            Triple(0, 1, -1),
            Triple(1, 1, -1),
        )
        val neighborCubes = offsets.mapNotNull { (xOffset, yOffset, zOffset) ->
            val xPos = x + xOffset
            val yPos = y + yOffset
            val zPos = z + zOffset
            getCubeAtPosition(xPos, yPos, zPos)
        }.toMutableSet()

        neighborCubes.removeIf { cube -> cube == Cube(x, y, z, CubeState.ACTIVE) }
        return neighborCubes.size
    }

    fun getCubeAtPosition(x: Int, y: Int, z: Int): Cube? {
        return activeCubes.find { it.x == x && it.y == y && it.z == z }
    }

    fun takeTurn() {
        val newCubes = mutableListOf<Cube>()
        (minX - 1..maxX + 1).forEach { x ->
            (minY - 1..maxY + 1).forEach { y ->
                (minZ - 1..maxZ + 1).forEach { z ->
                    if (getCubeAtPosition(x, y, z) != null) {
                        if (getActiveNeighborCountAt(x, y, z) in 2..3) {
                            newCubes.add(getCubeAtPosition(x, y, z)!!)
                        }
                    } else {
                        if (getActiveNeighborCountAt(x, y, z) == 3) {
                            newCubes.add(Cube(x, y, z, state = CubeState.ACTIVE))
                        }
                    }
                }
            }
        }
        activeCubes.clear()
        activeCubes.addAll(newCubes)
    }

    fun printActiveCubes() {
        activeCubes.forEach { println(it) }
    }

    fun printCubeLayers() {
        (minZ..maxZ).forEach { z ->
            println("z=$z")
            (minY..maxY).forEach { y ->
                (minX..maxX).forEach { x ->
                    print(if (getCubeAtPosition(x, y, z) != null) '#' else '.')
                }
                println()
            }
        }
        println("-".repeat(20))
    }
}