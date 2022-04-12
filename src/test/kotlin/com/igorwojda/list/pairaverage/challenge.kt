package com.igorwojda.list.pairaverage

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

fun hasAverage(list: List<Int>, average: Double): Boolean {
    var last = 0
    do {
        for (index in (last + 1)..list.lastIndex) {
            if ((list[last].toDouble() + list[index].toDouble()) / 2 == average) return true
        }
    } while (last++ < list.lastIndex)

    return false
}

private class Test {
    @Test
    fun `empty list return false`() {
        hasAverage(listOf(), 1.0) shouldBeEqualTo false
    }

    @Test
    fun `list size 1 return false`() {
        hasAverage(listOf(1), 1.0) shouldBeEqualTo false
    }

    @Test
    fun `list 3, 5 should with average 5,5 return true`() {
        hasAverage(listOf(3, 5), 5.5) shouldBeEqualTo false
    }

    @Test
    fun `list 3, 4, 7, 9 should with average 6,5 return true`() {
        hasAverage(listOf(3, 4, 7, 9), 6.5) shouldBeEqualTo true
    }

    @Test
    fun `list 3, 5 should with average 2,7 return true`() {
        hasAverage(listOf(3, 5), 2.7) shouldBeEqualTo false
    }

    @Test
    fun `list 3, 5, 9, 7, 11, 14 should with average 8,0 return true`() {
        hasAverage(listOf(3, 5, 9, 7, 11, 14), 8.0) shouldBeEqualTo true
    }

    @Test
    fun `list 3, 7, 5 should with average 3,5 return true`() {
        hasAverage(listOf(3, 5, 7), 3.5) shouldBeEqualTo false
    }
}
