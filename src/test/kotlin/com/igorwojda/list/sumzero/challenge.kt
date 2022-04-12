package com.igorwojda.list.sumzero

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.lang.Math.abs

private fun sumZero(list: List<Int>): Pair<Int, Int>? {
    list.forEachIndexed { index, first ->
        ((index + 1)..list.lastIndex).forEach { innerIndex ->
            if (abs(list[index]) == abs(list[innerIndex])) return Pair(list[index], list[innerIndex])
        }
    }
    return null
}

private class Test {
    @Test
    fun `sumZero empty list return null`() {
        sumZero(listOf()) shouldBeEqualTo null
    }

    @Test
    fun `sumZero 1, 2 return null`() {
        sumZero(listOf(1, 2)) shouldBeEqualTo null
    }

    @Test
    fun `sumZero 1, 2, 4, 7 return null`() {
        sumZero(listOf(1, 2, 4, 7)) shouldBeEqualTo null
    }

    @Test
    fun `sumZero -4, -3, -2, 1, 2, 3, 5 return Pair(-3, 3)`() {
        sumZero(listOf(-4, -3, -2, 1, 2, 3, 5)) shouldBeEqualTo Pair(-3, 3)
    }

    @Test
    fun `sumZero -4, -3, -2, 1, 2, 5 return Pair(-2, 2)`() {
        sumZero(listOf(-4, -3, -2, 1, 2, 5)) shouldBeEqualTo Pair(-2, 2)
    }
}
