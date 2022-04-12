package com.igorwojda.string.issubstring

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

private fun isSubstring(str: String, subStr: String): Boolean {
    if (subStr.isEmpty()) return false
    var slow: Int
    var fast = 0

    for (index in 0 until str.length) {
        slow = index
        if (str.get(slow) == subStr.get(fast)) {
            fast++
            if (fast == subStr.length) return true
        } else {
            fast = if (str.get(slow) == subStr.get(0)) 1 else 0
        }
    }
    return fast == subStr.length
}

private class Test {
    @Test
    fun `abd not in abcd`() {
        isSubstring("abcd", "abd") shouldBeEqualTo false
    }

    @Test
    fun `e not in abcd`() {
        isSubstring("abcd", "e") shouldBeEqualTo false
    }

    @Test
    fun `ab in abd`() {
        isSubstring("abc", "ab") shouldBeEqualTo true
    }

    @Test
    fun `bc in abc`() {
        isSubstring("abc", "bc") shouldBeEqualTo true
    }

    @Test
    fun `abc in ababc`() {
        isSubstring("ababc", "abc") shouldBeEqualTo true
    }

    @Test
    fun `cd in abcdef`() {
        isSubstring("abcdef", "cd") shouldBeEqualTo true
    }

    @Test
    fun `empty sub-string not in abc`() {
        isSubstring("abc", "") shouldBeEqualTo false
    }

    @Test
    fun `abc not in empty string`() {
        isSubstring("", "abc") shouldBeEqualTo false
    }

    @Test
    fun `empty sub-string not in empt ystring`() {
        isSubstring("", "") shouldBeEqualTo false
    }
}
