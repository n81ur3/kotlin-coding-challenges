package adventofcode2019

class Day08SpaceImageFormat

data class PasswordImageLayer(
    val pixels: List<List<Int>>
) {
    val zeroCount: Int
        get() = pixels.flatMap { it.toList() }.count { it == 0 }
    val oneCount: Int
        get() = pixels.flatMap { it.toList() }.count { it == 1 }
    val twoCount: Int
        get() = pixels.flatMap { it.toList() }.count { it == 2 }

    fun pixelAt(x: Int, y: Int): Int = pixels[x][y]
}

class PasswordImage(input: String, val width: Int, val height: Int) {
    val layers: List<PasswordImageLayer>

    init {
        val rows = input.split("").filter { it.isNotEmpty() }.map { it.toInt() }.chunked(width)
        layers = rows.windowed(height, height).toList().map { PasswordImageLayer(it) }
    }

    fun decryptMessage() {
        val result = mutableListOf<Int>()

        (0 until height).forEach { y ->
            (0 until width).forEach { x ->
                result.add(layers.first { it.pixelAt(y, x) != 2 }.pixelAt(y, x))
            }
        }

        val message = result.windowed(width, width)
        message.forEach { line ->
            println(line)
        }
    }
}