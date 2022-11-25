package adventofcode2018

class Day02InventoryManagmentSystem

data class InventoryBox(val id: String) {
    val containsTuple: Boolean
    val containsTriple: Boolean

    init {
        val orderedChars = id.split("").filterNot { it == "" }.groupBy { it }
        containsTuple = orderedChars.any { it.value.size == 2 }
        containsTriple = orderedChars.any { it.value.size == 3 }
    }

    fun compareTo(otherBox: InventoryBox): Int {
        val zipped = id.zip(otherBox.id)
        return zipped.count { it.first != it.second }
    }

    fun commonChars(otherBox: InventoryBox): String {
        val zipped = id.zip(otherBox.id)
        return buildString {
            zipped.forEach {
                if (it.first == it.second) append(it.first)
            }
        }
    }
}

class InventorySystem(boxIds: List<String>) {
    val boxes: List<InventoryBox>

    init {
        boxes = boxIds.map { InventoryBox(it) }
    }

    fun checkSum(): Int {
        val boxesWithTuple = boxes.count { it.containsTuple }
        val boxesWithTriple = boxes.count { it.containsTriple }
        return boxesWithTuple * boxesWithTriple
    }

    fun findPrototypes(): String {
        boxes.forEach { box ->
            boxes.forEach { otherBox ->
                if (box.compareTo(otherBox) == 1) {
                    return box.commonChars(otherBox)
                }
            }
        }
        return ""
    }
}