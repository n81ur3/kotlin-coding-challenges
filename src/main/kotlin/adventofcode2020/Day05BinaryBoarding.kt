package adventofcode2020

import java.lang.Math.ceil
import java.lang.Math.floor

private val ClosedFloatingPointRange<Double>.dif: Double
    get() = endInclusive - start

class BinaryBoardingPass(encodedSeat: String) {
    val row: Int
    val column: Int
    val seatId: Int
        get() = row * 8 + column

    init {
        row = calculateRow(encodedSeat.substring(0..6))
        column = calculateColumn(encodedSeat.substring(7..9))
    }

    private fun calculateRow(code: String): Int {
        return code.fold(0.0..127.0, ::calcRange).start.toInt()
    }

    private fun calculateColumn(code: String): Int {
        return code.fold(0.0..7.0, ::calcRange).start.toInt()
    }

    private fun calcRange(acc: ClosedFloatingPointRange<Double>, c: Char) =
        if (c == 'F' || c == 'L') acc.start..floor(acc.dif / 2 + acc.start)
        else ceil(acc.dif / 2 + acc.start)..acc.endInclusive
}

data class Seat(val prevSeat: Int, val seat: Int, val nextSeat: Int) {
    fun isEligible(): Boolean {
        return (nextSeat - prevSeat == 3)
    }
}

object SeatCalculator {

    fun calculateFreeSeat(lines: List<String>): Int {
        val occupiedSeats = mutableListOf<Int>()
        lines.forEach { occupiedSeats.add(BinaryBoardingPass(it).seatId) }
        occupiedSeats.sort()
        val seats = (0..occupiedSeats.size - 3).map { Seat(occupiedSeats[it], occupiedSeats[it + 1], occupiedSeats[it + 2])}
        val result = seats.first { it.isEligible() }.seat
        return result + 1
    }
}