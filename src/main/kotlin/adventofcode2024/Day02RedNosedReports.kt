package adventofcode2024

import kotlin.math.absoluteValue

class Day02RedNosedReports {
}

class ReportClassifier(
    val input: List<String>
) {
    val reports: List<List<Int>>

    init {
        reports = input.map { it.split(" ").map { it.toInt() } }
    }

    fun getSafeReportsCount(): Int {
        return reports
            .filter { isSafeList(it) }
            .map {
                it.windowed(2, 1)
                    .map { isSafePair(it[0], it[1]) }
                    .all { it == true }
            }.filter { it == true }
            .count()
    }

    private fun isSafePair(first: Int, second: Int): Boolean {
        return (first - second).absoluteValue in (1..3)
    }

    private fun isSafeList(list: List<Int>): Boolean {
        return (list == list.sorted()) || (list == list.sortedDescending())
    }
}