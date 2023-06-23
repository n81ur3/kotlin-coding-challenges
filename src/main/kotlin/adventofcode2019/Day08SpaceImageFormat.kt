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
}

class PasswordImage(input: String, width: Int, height: Int) {
    val layers: List<PasswordImageLayer>

    init {
        val rows = input.split("").filter { it.isNotEmpty() }.map { it.toInt() }.chunked(width)
        layers = rows.windowed(height, height).toList().map { PasswordImageLayer(it) }
    }
}