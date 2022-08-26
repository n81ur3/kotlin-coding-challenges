package adventofcode2017

class Day06MemoryReallocation

data class MemorySegmentation(val blocks: MutableList<Int>)

class MemoryReallocator(initialDistribution: String) {
    var memory: MemorySegmentation
    val distributionHistory: MutableList<MemorySegmentation>

    init {
        memory = MemorySegmentation(initialDistribution.split("\\s+".toRegex()).map { it.toInt() }.toMutableList())
        distributionHistory = mutableListOf()
    }

    fun reallocate(): Pair<Int, Int> {
        var cycleCounter = 0
        while (true) {
            var maxBlockCount = memory.blocks.maxOrNull() ?: 0
            var currentIndex = memory.blocks.indexOf(maxBlockCount)
            memory.blocks[currentIndex] = 0
            while (maxBlockCount > 0) {
                currentIndex = (currentIndex + 1) % memory.blocks.size
                memory.blocks[currentIndex] = memory.blocks[currentIndex] + 1
                maxBlockCount--
            }
            cycleCounter++
            if (distributionHistory.contains(memory)) {
                break
            } else {
                distributionHistory.add(MemorySegmentation(memory.blocks.map { it }.toMutableList()))
            }
        }
        return cycleCounter to (cycleCounter - 1 - distributionHistory.indexOf(memory))
    }
}