package adventofcode2024

import adventofcode2020.SeatPlan.Companion.parseInput
import kotlin.math.abs

class Day01HistorianHysteria {
}

class ListComparer(
    input: List<String>
) {
    val list1: List<Int>
    val list2: List<Int>

    init {
        val lists = parseInput(input)
        list1 = lists.first
        list2 = lists.second
    }

    private fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val result = input.map { it.split(" ") }.map { parts -> parts.first().toInt() to parts.last().toInt() }
        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()
        result.forEach { pair ->
            firstList.add(pair.first)
            secondList.add(pair.second)
        }
        return firstList.sorted() to secondList.sorted()
    }

    fun getDifference(): Int {
        return list1.zip(list2).map { abs(it.first - it.second) }.sum()
    }
}