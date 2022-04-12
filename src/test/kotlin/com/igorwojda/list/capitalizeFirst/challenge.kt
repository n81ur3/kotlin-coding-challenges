package com.igorwojda.list.capitalizeFirst

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

private fun capitalizeFirst1(list: List<String>): List<String> = list.map { it.capitalize() }

private fun capitalizeFirst(list: List<String>): List<String> {
    val result = mutableListOf<String>()
    list.forEach { result.add(it.capitalize())}
    return result
}


private class Test {
    @Test
    fun `capitalize list with one string`() {
        capitalizeFirst(listOf("igor")) shouldBeEqualTo listOf("Igor")
    }

    @Test
    fun `capitalize list with two strings`() {
        capitalizeFirst(listOf("igor", "wojda")) shouldBeEqualTo listOf("Igor", "Wojda")
    }

    @Test
    fun `capitalize list with empty string`() {
        capitalizeFirst(listOf("")) shouldBeEqualTo listOf("")
    }

    @Test
    fun `capitalize list with sentence`() {
        capitalizeFirst(listOf("what a", "beautiful", "morning")) shouldBeEqualTo listOf(
            "What a",
            "Beautiful",
            "Morning"
        )
    }
}
