package adventofcode2016

class BathroomKeypad(
    val keypad: List<List<Int>> = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    ),
    val keypadAdvanced: List<List<String>> = listOf(
        listOf("-1", "-1", "1", "-1", "-1"),
        listOf("-1", "2", "3", "4", "-1"),
        listOf("5", "6", "7", "8", "9"),
        listOf("-1", "A", "B", "C", "-1"),
        listOf("-1", "-1", "D", "-1", "-1"),
    ),
    val keypadType: KeypadType = KeypadType.STANDARD,
    var posX: Int = 1,
    var posY: Int = 1
) {

    fun dial(instructions: List<String>): String {
        val numbers = StringBuilder()
        instructions.forEach {
            numbers.append(dialLine(it))
        }
        return numbers.toString()
    }

    fun dialLine(instructions: String): String {
        if (keypadType == KeypadType.STANDARD) {
            instructions.forEach { i ->
                move(i)
            }
            return keypad[posY][posX].toString()
        } else {
            instructions.forEach { i ->
                moveAdvanced(i)
            }
            return keypadAdvanced[posY][posX]
        }
    }

    fun move(direction: Char) {
        when (direction) {
            'U' -> posY = (posY - 1).coerceAtLeast(0)
            'R' -> posX = (posX + 1).coerceAtMost(2)
            'D' -> posY = (posY + 1).coerceAtMost(2)
            'L' -> posX = (posX - 1).coerceAtLeast(0)
        }
    }

    fun moveAdvanced(direction: Char) {
        when (direction) {
            'U' -> {
                when (posX) {
                    0, 4 -> posY = (posY - 1).coerceAtLeast(2)
                    1, 3 -> posY = (posY - 1).coerceAtLeast(1)
                    else -> posY = (posY - 1).coerceAtLeast(0)
                }
            }

            'R' -> {
                when (posY) {
                    0, 4 -> posX = (posX + 1).coerceAtMost(2)
                    1, 3 -> posX = (posX + 1).coerceAtMost(3)
                    else -> posX = (posX + 1).coerceAtMost(4)
                }
            }

            'D' -> {
                when (posX) {
                    0, 4 -> posY = (posY + 1).coerceAtMost(2)
                    1, 3 -> posY = (posY + 1).coerceAtMost(3)
                    else -> posY = (posY + 1).coerceAtMost(4)
                }
            }

            'L' -> {
                when (posY) {
                    0, 4 -> posX = (posX - 1).coerceAtLeast(2)
                    1, 3 -> posX = (posX - 1).coerceAtLeast(1)
                    else -> posX = (posX - 1).coerceAtLeast(0)
                }
            }
        }
    }
}

enum class KeypadType {
    STANDARD, ADVANCED
}