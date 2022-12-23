package adventofcode2018

class Day08MemoryManeuver

data class MemoryNode(
    val childs: List<MemoryNode>,
    val metadata: List<Int>,
) {
    val metaSum: Int
        get() = metadata.sum()

    fun totalMetaSum(): Int {
        if (childs.isEmpty()) return metaSum

        return metaSum + childs.sumOf { it.totalMetaSum() }
    }

    fun rootSum(): Int {
        if (childs.isEmpty()) return metaSum

        var sum = 0
        metadata.filter { it != 0 && it <= childs.size }.forEach {
            sum += childs[it - 1].rootSum()
        }

        return sum
    }

    companion object {
        fun fromString(iter: Iterator<Int>): MemoryNode {
            val childCount = iter.next()
            val metaCount = iter.next()
            val childs = (0 until childCount).map { fromString(iter) }
            val metadata = (0 until metaCount).map { iter.next() }
            return MemoryNode(childs, metadata)
        }
    }
}

class NavSystem(input: String) {
    val nums = input.split(" ").map { it.toInt() }
    val root: MemoryNode
    val totalMetadataSum: Int
        get() = root.totalMetaSum()
    val rootSum: Int
        get() = root.rootSum()

    init {
        root = MemoryNode.fromString(nums.iterator())
    }
}