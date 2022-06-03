package adventofcode2020

class Day13ShuttleSearch

data class Bus(val tripTime: Long, val offset: Long = 0L) {
    val intervals = generateSequence(0L) { it + tripTime }

    fun getWaitTimeAfter(earliest: Long): Long {
        val nextStop = intervals.find { it >= earliest } ?: Long.MAX_VALUE
        return nextStop - earliest
    }

    fun findIntervall(requiredTimestamp: Long): Long? {
        val busstops = generateSequence(requiredTimestamp) { it + tripTime }
        return busstops.dropWhile { it < requiredTimestamp }.take(2).find { (it % (requiredTimestamp + offset)) == 0L }
    }

    fun matchesBusTimestamp(requiredTimestamp: Long): Boolean {
        return (((requiredTimestamp + offset) % tripTime) == 0L)
    }
}