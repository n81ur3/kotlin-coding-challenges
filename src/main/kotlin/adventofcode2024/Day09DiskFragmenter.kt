package adventofcode2024

class DiskMap(
    val input: String
) {
    val blocks = mutableListOf<Int>()
    val freeBlockMargins = mutableListOf<Int>()

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
        var result = 0L
        blocks.forEachIndexed { index, block ->
            if (block != -1) {
                result += (block * index)
            }
        }
        return result
    }

    fun printDiskLayout() {
        blocks.forEach { b ->
            if (b == -1) print('.')
            else print(b)
        }
        println()
    }

    fun defragEfficient() {
        buildFreeBlockMargins()
        val fileGroups = blocks.filterNot { it == -1 }.groupBy { it }.toSortedMap(Comparator.reverseOrder())
        fileGroups.forEach { group ->
            val nextFreeBlock = findFreeBlock(group.value.size)
            if (nextFreeBlock > -1 && nextFreeBlock < blocks.indexOf(group.key)) {
                swapBlocks(nextFreeBlock, blocks.indexOf(group.key), group.value.size)
                buildFreeBlockMargins()
            }
        }
    }

    private fun buildFreeBlockMargins() {
        freeBlockMargins.clear()
        (0 until blocks.lastIndexOf(-1)).forEach { index ->
            if ((blocks[index] == -1 && blocks[index + 1] != -1) || (blocks[index] != -1 && blocks[index + 1] == -1)) {
                freeBlockMargins.add(index + 1)
            }
        }
    }

    private fun swapBlocks(firstIndex: Int, secondIndex: Int, size: Int) {
        (0 until size).forEach { index ->
            blocks.swapElements(firstIndex + index, secondIndex + index)
        }
    }

    private fun findFreeBlock(size: Int): Int {
        return freeBlockMargins.windowed(2, 2).firstOrNull { it[1] - it[0] >= size }?.get(0) ?: -1
    }

    private fun MutableList<Int>.swapElements(firstIndex: Int, secondIndex: Int) {
        val tmp = this[firstIndex]
        this[firstIndex] = this[secondIndex]
        this[secondIndex] = tmp
    }
}