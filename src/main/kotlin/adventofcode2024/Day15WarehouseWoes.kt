package adventofcode2024

open class WarehouseItem(
    var x: Int,
    var y: Int
)

class WarehouseBox(x: Int, y: Int) : WarehouseItem(x, y)
class WarehouseBlock(x: Int, y: Int) : WarehouseItem(x, y)
class WarehouseFreeSpot(x: Int, y: Int) : WarehouseItem(x, y)

class LaternWarehouse(input: List<String>) {
    val boxes = mutableListOf<WarehouseBox>()
    val blocks = mutableListOf<WarehouseBlock>()
    val freeSpots = mutableListOf<WarehouseFreeSpot>()
    val width = input[0].length - 1
    val height: Int
        get() = blocks.maxOf { it.y }
    var currentPosition = 0 to 0
    val directions: List<Char>

    init {
        input.takeWhile { it.isNotBlank() }.mapIndexed { index, line -> parseInput(index, line) }
        directions = input.dropWhile { it.isNotBlank() }.flatMap { it.toList() }
    }

    private fun parseInput(y: Int, input: String) {
        input.forEachIndexed { x, c ->
            if (c == 'O') boxes.add(WarehouseBox(x, y))
            else if (c == '#') blocks.add(WarehouseBlock(x, y))
            else if (c == '@') currentPosition = x to y
            else freeSpots.add(WarehouseFreeSpot(x, y))
        }
    }

    private fun isWall(x: Int, y: Int): Boolean =
        (x == 0) || (x == width) || (y == 0) || (y == height) || blocks.any { it.x == x && it.y == y }

    fun moveRobot() {
        directions.forEach { d ->
            when (d) {
                '^' -> tryMoveRobot(0, -1)
                'v' -> tryMoveRobot(0, 1)
                '<' -> tryMoveRobot(-1, 0)
                '>' -> tryMoveRobot(1, 0)
            }
        }
    }

    fun getGPSsum(): Int {
        return boxes.sumOf { 100 * it.y + it.x }
    }

    private fun tryMoveRobot(x: Int, y: Int) {
        val pathToWall = getPathToWall(x, y)
        if (pathToWall.isNotEmpty()) {
            pathToWall.reversed().forEach {
                if (it is WarehouseBox) tryMoveBox(it.x, it.y, x, y)
            }
            val targetX = currentPosition.first + x
            val targetY = currentPosition.second + y
            val freeSpot = freeSpots.firstOrNull { it.x == targetX && it.y == targetY }
            freeSpot?.let {
                it.x = currentPosition.first
                it.y = currentPosition.second
                currentPosition = targetX to targetY
            }
        }
    }

    private fun tryMoveBox(x: Int, y: Int, diffX: Int, diffY: Int) {
        val box = boxes.first { it.x == x && it.y == y }
        val targetX = x + diffX
        val targetY = y + diffY
        val freeSpot = freeSpots.firstOrNull { it.x == targetX && it.y == targetY }
        freeSpot?.let {
            it.x = x
            it.y = y
            box.x = targetX
            box.y = targetY
        }
    }

    private fun getPathToWall(x: Int, y: Int): List<WarehouseItem> {
        val result = mutableListOf<WarehouseItem>()
        var currentX = currentPosition.first + x
        var currentY = currentPosition.second + y
        while (true) {
            val item = getItemAt(currentX, currentY)
            if (item is WarehouseBlock) return result
            if (item is WarehouseFreeSpot) {
                result.add(item)
                return result
            } else result.add(item)
            currentX += x
            currentY += y
        }
    }

    private fun getItemAt(x: Int, y: Int): WarehouseItem {
        val block = blocks.firstOrNull { it.x == x && it.y == y }
        block?.let { return it }
        val box = boxes.firstOrNull { it.x == x && it.y == y }
        box?.let { return it }
        return freeSpots.first { it.x == x && it.y == y }
    }

    fun printWarehouse() {
        (0..height).forEach { y ->
            (0..width).forEach { x ->
                if (isWall(x, y)) print("#")
                else if (boxes.any { it.x == x && it.y == y }) print('O')
                else if (currentPosition == x to y) print('@')
                else print('.')
            }
            println()
        }
    }
}