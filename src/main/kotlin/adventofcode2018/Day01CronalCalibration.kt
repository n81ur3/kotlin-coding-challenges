package adventofcode2018

import java.lang.reflect.Array.get

class Day01CronalCalibration

class FrequencyDisplay(input: List<String>) {
    val adjustments: List<Int>
    val finalFrequency: Int
        get() = adjustments.sum()

    init {
        adjustments = input.map { it.toInt() }
    }
}