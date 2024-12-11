package adventofcode2024

import kotlin.math.absoluteValue

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

    fun getDampenedSafeReportsCount(): Int {
        var result = 0
        reports.forEach {
            if (isDampenedSafe(it)) result++
        }
        return result
    }

    private fun isDampenedSafe(report: List<Int>): Boolean {
        var result = isSafeList(report)
        result = result && report.windowed(2, 1).all { isSafePair(it[0], it[1]) }
        if (result) return true
        (0 until report.size).forEach { index ->
            val r = report.toMutableList()
            r.removeAt(index)
            result = isSafeList(r)
            result = result && r.windowed(2, 1).all { isSafePair(it[0], it[1]) }
            if (result) return true
        }
        return false
    }

    private fun isSafePair(first: Int, second: Int): Boolean {
        return (first - second).absoluteValue in (1..3)
    }

    private fun isSafeList(list: List<Int>): Boolean {
        return (list == list.sorted()) || (list == list.sortedDescending())
    }
}