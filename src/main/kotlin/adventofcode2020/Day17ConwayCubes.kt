package adventofcode2020

class Day17ConwayCubes

enum class CubeState {
    ACTIVE,
    INACTIVE
}

data class Cube(var x: Int, var y: Int, val z: Int, var state: CubeState = CubeState.INACTIVE) {
    fun getStateAsChar(): Char = if (state == CubeState.ACTIVE) '#' else '.'
    fun shiftRight() = x++
    fun shiftDown() = y++
    val isActive
        get() = state == CubeState.ACTIVE

    override fun toString(): String = "[$x/$y/$z]"
}

data class ConwayLayer(val rows: MutableList<MutableList<Cube>>, val level: Int = 0) {

    constructor(rowCount: Int, colCount: Int, level: Int) : this(mutableListOf(), level) {
        for (row in 0 until rowCount) {
            rows.add(mutableListOf())
            for (col in 0 until colCount) {
                rows[row].add(Cube(col, row, level))
            }
        }
    }

    fun shiftRows() {
        rows.forEachIndexed { index, row ->
            run {
                row.forEach { cube -> cube.shiftRight() }
                row.add(0, Cube(0, index, level))
            }
        }
    }

    fun expandTop() {
        rows.forEach { row -> row.forEach { cube -> cube.shiftDown() } }
        val colCount = rows[0].size
        val newRow = mutableListOf<Cube>()
        (0 until colCount).forEach { newRow.add(Cube(it, 0, level)) }
        rows.add(0, newRow)
    }

    fun expandBottom() {
        val colCount = rows[0].size
        val newRow = mutableListOf<Cube>()
        (0 until colCount).forEach { newRow.add(Cube(it, rows.size, level)) }
        rows.add(rows.size, newRow)
    }

    fun resetLayer() {
        rows.forEach { row -> row.forEach { cube -> cube.state = CubeState.INACTIVE } }
    }
}

class ConwayCube(val activeCubes: MutableList<Cube>) {
    var frontLevel = 0
    val backLevel = 0
    val minX: Int
        get() {
            return activeCubes.minByOrNull { cube -> cube.x }?.x ?: 0
        }
    val maxX: Int
        get() {
            return activeCubes.maxByOrNull { cube -> cube.x }?.x ?: 0
        }
    val minY: Int
        get() {
            return activeCubes.minByOrNull { cube -> cube.y }?.y ?: 0
        }
    val maxY: Int
        get() {
            return activeCubes.maxByOrNull { cube -> cube.y }?.y ?: 0
        }
    val minZ: Int
        get() {
            return activeCubes.minByOrNull { cube -> cube.z }?.z ?: 0
        }
    val maxZ: Int
        get() {
            return activeCubes.maxByOrNull { cube -> cube.z }?.z ?: 0
        }

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
            val xPos = when {
                x + xOffset < minX -> maxX
                x + xOffset > maxX -> minX
                else -> x + xOffset
            }
            val yPos = when {
                y + yOffset < minY -> maxY
                y + yOffset > maxY -> minY
                else -> y + yOffset
            }
            val zPos = when {
                z + zOffset < minZ -> maxZ
                z + zOffset > maxZ -> minZ
                else -> z + zOffset
            }
//            println("Search at: $xPos/$yPos/$zPos")
            getCubeAtPosition(xPos, yPos, zPos)
        }.toMutableSet()

        neighborCubes.removeIf { cube -> cube == Cube(x, y, z, CubeState.ACTIVE) }
        println("Neighbors: $neighborCubes")
        return neighborCubes.size
    }

    fun getCubeAtPosition(x: Int, y: Int, z: Int): Cube? {
        return activeCubes.find { it.x == x && it.y == y && it.z == z }
    }

    fun takeTurn() {
    }

    fun printActiveCubes() {
        activeCubes.forEach { println(it) }
    }

    fun printCubeLayers() {
        (minZ..maxZ).forEach { z ->
            println("Layer: $z")
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