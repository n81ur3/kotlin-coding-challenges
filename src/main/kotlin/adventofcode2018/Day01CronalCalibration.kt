package adventofcode2018

class Day01CronalCalibration

class FrequencyDisplay(input: List<String>) {
    val adjustments: List<Int>
    val recordedFrequencies = mutableSetOf<Int>()
    val finalFrequency: Int
        get() = adjustments.sum()

    init {
        adjustments = input.map { it.toInt() }
    }

    fun findFirstDuplicatedFrequency(): Int {
        var currentFrequency = 0
        while (true) {
            adjustments.forEach {
                currentFrequency += it
                if (currentFrequency in recordedFrequencies) return currentFrequency
                recordedFrequencies.add(currentFrequency)
            }
        }
    }
}