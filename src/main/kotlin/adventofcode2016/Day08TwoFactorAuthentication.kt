package adventofcode2016


data class LcdPixel(
    var x: Int = 0,
    var y: Int = 0
)

class LcdScreen {
    private val screenWidth = 50
    private val screenHeight = 6
    private val activePixels = mutableSetOf<LcdPixel>()
    val numberOfActivePixels: Int
        get() = activePixels.size

    fun rectAB(width: Int, height: Int) {
        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                activePixels.add(LcdPixel(x, y))
            }
        }
    }

    fun rotateColumn(index: Int, offset: Int) {
        activePixels.filter { it.x == index }.onEach { pixel ->
            pixel.y = (pixel.y + offset) % screenHeight
        }
    }

    fun rotateRow(index: Int, offset: Int) {
        activePixels.filter { it.y == index }.onEach { pixel ->
            pixel.x = (pixel.x + offset) % screenWidth
        }
    }

    fun printScreen() {
        (0 until screenHeight).forEach { y ->
            (0 until screenWidth).forEach { x ->
                if (activePixels.any { pixel -> pixel.x == x && pixel.y == y}) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }
}

class LcdScreenOperator(
    val lcdScreen: LcdScreen = LcdScreen()
) {

    val activePixelsCount: Int
        get() = lcdScreen.numberOfActivePixels

    fun executeCommand(command: String) {
        when {
            command.startsWith("rect") -> executeRect(command)
            command.startsWith("rotate column") -> executeRotateColumn(command)
            command.startsWith("rotate row") -> executeRotateRow(command)
        }
    }

    fun printScreen() {
        println()
        lcdScreen.printScreen()
    }

    private fun executeRect(command: String) {
        val parts = command.split(" ")
        val coordinates = parts[1].split("x")
        val xCoordinate = coordinates[0].toInt()
        val yCoordinate = coordinates[1].toInt()
        lcdScreen.rectAB(xCoordinate, yCoordinate)
    }

    private fun executeRotateColumn(command: String) {
        val parts = command.split(" ")
        val (index, offset) = getIndexAndOffset(parts)
        lcdScreen.rotateColumn(index, offset)
    }

    private fun executeRotateRow(command: String) {
        val parts = command.split(" ")
        val (index, offset) = getIndexAndOffset(parts)
        lcdScreen.rotateRow(index, offset)
    }

    private fun getIndexAndOffset(parts: List<String>): Pair<Int, Int> {
        val index = parts[2].split("=")[1].toInt()
        val offset = parts[4].toInt()
        return Pair(index, offset)
    }
}