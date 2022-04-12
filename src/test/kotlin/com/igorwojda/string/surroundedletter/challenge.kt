package com.igorwojda.string.surroundedletter

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

private fun surroundedLetter(str: String): Boolean {
    if (!str.startsWith("+") || !str.endsWith("+")) return false

    fun checkChar(substring: String): Boolean {
        if (substring[1].equals('+')) return true
        return substring.startsWith("+") && substring.endsWith("+")
    }

    return (1 until str.length - 1).map { checkChar(str.substring(it - 1, it + 2)) }.all { it == true }
}

private class Test {
    @Test
    fun `"a" return "false"`() {
        surroundedLetter("a") shouldBeEqualTo false
    }

    @Test
    fun `"+a+" return "true"`() {
        surroundedLetter("+a+") shouldBeEqualTo true
    }

    @Test
    fun `"a+" return "false"`() {
        surroundedLetter("a+") shouldBeEqualTo false
    }

    @Test
    fun `"+a" return "false"`() {
        surroundedLetter("+a") shouldBeEqualTo false
    }

    @Test
    fun `"+a+b+" return "true"`() {
        surroundedLetter("+a+b+") shouldBeEqualTo true
    }

    @Test
    fun `"+a++b+" return "true"`() {
        surroundedLetter("+a++b+") shouldBeEqualTo true
    }

    @Test
    fun `"+ab+" return "false"`() {
        surroundedLetter("+ab+") shouldBeEqualTo false
    }

    @Test
    fun `"a+b+" return "false"`() {
        surroundedLetter("a+b+") shouldBeEqualTo false
    }

    @Test
    fun `"+a+b" return "false"`() {
        surroundedLetter("+a+b") shouldBeEqualTo false
    }

    @Test
    fun `"+a+b+++c++d+e++" return "true"`() {
        surroundedLetter("+a+b+++c++d+e++") shouldBeEqualTo true
    }

    @Test
    fun `"+++a+d++de++e++" return "false"`() {
        surroundedLetter("+a+d++de++e+") shouldBeEqualTo false
    }
}
