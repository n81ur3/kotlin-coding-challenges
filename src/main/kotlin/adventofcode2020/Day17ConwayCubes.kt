package adventofcode2020

class Day17ConwayCubes

enum class CubeState {
    ACTIVE,
    INACTIVE
}

data class Cube(var x: Int, var y: Int, val z: Int, val w: Int, var state: CubeState = CubeState.INACTIVE) {
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
    val minW: Int
        get() = activeCubes.minByOrNull { cube -> cube.w }?.w ?: 0
    val maxW: Int
        get() = activeCubes.maxByOrNull { cube -> cube.w }?.w ?: 0
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

    data class Offset(val xOffset: Int, val yOffset: Int, val zOffset: Int, val wOffset: Int)

    fun getActiveNeighborCountAt(x: Int, y: Int, z: Int, w: Int): Int {
        val offsets = mutableListOf<Offset>()
        (-1..1).forEach { xOffset ->
            (-1..1).forEach { yOffset ->
                (-1..1).forEach { zOffset ->
                    (-1..1).forEach { wOffset ->
                        offsets.add(Offset(xOffset, yOffset, zOffset, wOffset))
                    }
                }
            }
        }
        val neighborCubes = offsets.mapNotNull { (xOffset, yOffset, zOffset, wOffset) ->
            val xPos = x + xOffset
            val yPos = y + yOffset
            val zPos = z + zOffset
            val wPos = w + wOffset
            getCubeAtPosition(xPos, yPos, zPos, wPos)
        }.toMutableSet()

        neighborCubes.removeIf { cube -> cube == Cube(x, y, z, w, CubeState.ACTIVE) }
        return neighborCubes.size
    }

    fun getCubeAtPosition(x: Int, y: Int, z: Int, w: Int): Cube? {
        return activeCubes.find { it.x == x && it.y == y && it.z == z && it.w == w }
    }

    fun takeTurn() {
        val newCubes = mutableListOf<Cube>()
        (minX - 1..maxX + 1).forEach { x ->
            (minY - 1..maxY + 1).forEach { y ->
                (minZ - 1..maxZ + 1).forEach { z ->
                    (minW - 1..maxW + 1).forEach { w ->
                        if (getCubeAtPosition(x, y, z, w) != null) {
                            if (getActiveNeighborCountAt(x, y, z, w) in 2..3) {
                                newCubes.add(getCubeAtPosition(x, y, z, w)!!)
                            }
                        } else {
                            if (getActiveNeighborCountAt(x, y, z, w) == 3) {
                                newCubes.add(Cube(x, y, z, w, state = CubeState.ACTIVE))
                            }
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
        (minW..maxW).forEach { w ->
            (minZ..maxZ).forEach { z ->
                println("z=$z, w=$w")
                (minY..maxY).forEach { y ->
                    (minX..maxX).forEach { x ->
                        print(if (getCubeAtPosition(x, y, z, w) != null) '#' else '.')
                    }
                    println()
                }
            }
        }
        println("-".repeat(20))
    }
}