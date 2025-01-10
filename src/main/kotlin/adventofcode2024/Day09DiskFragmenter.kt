package adventofcode2024

class DiskMap(
    val input: String
) {
    val blocks = mutableListOf<Int>()

    init {
        parseInput()
    }

    fun parseInput() {
        var counter = 0
        input.windowed(2, 2, true) { block ->
            val blockSize = block[0].digitToInt()
            repeat(blockSize) { blocks.add(counter) }
            block.getOrNull(1)?.let {
                val freeSpace = block[1].digitToInt()
                repeat(freeSpace) { blocks.add(-1) }
            }
            counter++
        }
    }

    fun defrag() {
//        var counter = blocks.size - 1
        var counter = blocks.size - 1
        while (blocks[counter] == -1) {
            counter--
        }
        var forwardCounter = 0
        val freespaces = blocks.count { it == -1 }
        while (blocks.indexOf(-1) != blocks.size - freespaces) {
            if (blocks[forwardCounter] == -1) {
                blocks.swapElements(forwardCounter, counter--)
                if (blocks[counter] == -1) {
                    while (blocks[counter] == -1) {
                        counter--
                    }
                }
            }
            forwardCounter++
        }
    }

    fun checksum(): Long {
        var counter = 0L
        return blocks.takeWhile { it != -1 }.sumOf { counter++ * it }
    }

    fun printDiskLayout() {
        blocks.forEach { b ->
            if (b == -1) print('.')
            else print(b)
        }
        println()
    }

    private fun MutableList<Int>.swapElements(firstIndex: Int, secondIndex: Int) {
        val tmp = this[firstIndex]
        this[firstIndex] = this[secondIndex]
        this[secondIndex] = tmp
    }
}