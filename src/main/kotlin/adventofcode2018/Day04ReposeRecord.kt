package adventofcode2018

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Day04ReposeRecord

data class Guard(val id: Int)

data class SleepPeriod(val start: LocalDateTime, val stop: LocalDateTime)

sealed class ShiftEntry(
    val dateTime: LocalDateTime,
    val guard: Guard = Guard(-1),
    val awake: Boolean = false,
    val asleep: Boolean = false
) {
    companion object {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        fun fromString(input: String): ShiftEntry {
            val dateTime = LocalDateTime.parse(input.substring(1).substringBefore("] "), dateFormatter)
            return when {
                input.contains("Guard") -> NewGuardEntry(
                    dateTime,
                    Guard(input.substringAfter("#").substringBefore(" ").toInt())
                )

                input.contains("asleep") -> AsleepEntry(dateTime)
                else -> AwakeEntry(dateTime)
            }
        }
    }

    override fun toString(): String {
        return "ShiftEntry(dateTime=$dateTime, guard=$guard, awake=$awake, asleep=$asleep)"
    }

}

class NewGuardEntry(dateTime: LocalDateTime, guard: Guard) : ShiftEntry(dateTime, guard, awake = true)
class AsleepEntry(dateTime: LocalDateTime) : ShiftEntry(dateTime, asleep = true)
class AwakeEntry(dateTime: LocalDateTime) : ShiftEntry(dateTime, awake = true)


class ShiftPlan(input: List<String>) {
    val entries: List<ShiftEntry>
    var currentGuard = Guard(-1)
    var currentStartSleep = LocalDateTime.now()
    val guardRecords = mutableMapOf<Guard, MutableList<SleepPeriod>>()
    var guardSleeping = false

    init {
        entries = input.map { ShiftEntry.fromString(it) }.sortedBy { it.dateTime }
    }

    fun scheduleShifts() {
        entries.forEach { entry ->
            when (entry) {
                is NewGuardEntry -> {
                    if (guardRecords[entry.guard] == null) {
                        guardRecords[entry.guard] = mutableListOf()
                    }
                    if (guardSleeping) {
                        recordSleep(entry.dateTime.minusMinutes(1))
                        guardSleeping = false
                    }
                    currentGuard = entry.guard
                }

                is AsleepEntry -> {
                    currentStartSleep = entry.dateTime
                    guardSleeping = true
                }

                is AwakeEntry -> {
                    recordSleep(entry.dateTime.minusMinutes(1))
                    guardSleeping = false
                }
            }
        }
    }

    private fun recordSleep(endSleep: LocalDateTime) {
        guardRecords[currentGuard]?.add(SleepPeriod(currentStartSleep, endSleep))
    }

    fun findBestGuardAndMinute(): Pair<Guard, Int> {
        val bestGuard = findBestGuard()
        val bestMinute = findBestMinute(bestGuard)
        return bestGuard to bestMinute
    }

    private fun findBestGuard(): Guard {
        val sleepTimes = guardRecords.map { records ->
            val totalSleep = records.value.sumOf { it.stop.minute - it.start.minute }
            records.key to totalSleep
        }

        return sleepTimes.maxBy { it.second }.first
    }

    private fun findBestMinute(guard: Guard): Int {
        var result = 0
        guardRecords[guard]?.let {
            result = getMostFrequentMinute(it).first
        }
        return result
    }

    fun findBestGuardMostFrequentMinute(): Pair<Guard, Int> {
        val groupedEntries = guardRecords
            .filter { it.value.size > 0 }
            .map { it.key to getMostFrequentMinute(it.value) }
        val maxEntry = groupedEntries.maxBy { it.second.second }
        return maxEntry.first to maxEntry.second.first
    }

    private fun getMostFrequentMinute(sleepPeriods: List<SleepPeriod>): Pair<Int, Int> {
        val minutes = mutableListOf<Int>()
        sleepPeriods.forEach { sleepPeriod ->
            (sleepPeriod.start.minute..sleepPeriod.stop.minute).forEach {
                minutes.add(it)
            }
        }
        val sleepMinutesGroups = minutes.groupBy { it }
        val result = sleepMinutesGroups.maxByOrNull { it.value.size }
        return result?.let {
            result.key to result.value.size
        } ?: (-1 to 0)
    }
}