package adventofcode2020

class Day15RambunctiousRecitation

fun MutableList<Int>.appendRecitation() {
    val previousIndexOfLast = subList(0, lastIndex).lastIndexOf(last())
    if (previousIndexOfLast != -1) {
        add((size - 1) - previousIndexOfLast)
    } else {
        add(0)
    }
}