package adventofcode2017

class Day14DiskDefragmentation

class Defragmenter() {
    val knotHash = KnotHash(256)

    fun calculateCompleteGrid(keyString: String): String {
        return buildString {
            (0..127).forEach { row ->
                append(getBinaryHash(keyString + '-' + row))
                reset()
            }
        }
    }

    fun getBinaryHash(input: String): String {
        val hash = knotHash.hashAdvanced(input)
        return hexHashToBinary(hash)
    }

    private fun hexHashToBinary(hash: String): String {
        return buildString {
            hash.forEach {
                append(it.toHex())
            }
        }
    }

    fun reset() {
        knotHash.reset()
        'c'.toHex()
    }
}

private fun Char.toHex(): String {
    return when (this) {
        '0' -> "0000"
        '1' -> "0001"
        '2' -> "0010"
        '3' -> "0011"
        '4' -> "0100"
        '5' -> "0101"
        '6' -> "0110"
        '7' -> "0111"
        '8' -> "1000"
        '9' -> "1001"
        'a' -> "1010"
        'b' -> "1011"
        'c' -> "1100"
        'd' -> "1101"
        'e' -> "1110"
        else -> "1111"
    }
}
