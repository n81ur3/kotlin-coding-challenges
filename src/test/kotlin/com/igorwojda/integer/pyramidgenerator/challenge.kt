package com.igorwojda.integer.pyramidgenerator

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

fun generatePyramid(n: Int): List<String> {
    var spaces = n - 1
    return (0 until n).map { " ".repeat(spaces) + "#".repeat(it*2 + 1) + " ".repeat(spaces--) }
}

private class Test {

    @Test
    fun `pyramid n = 2`() {
        generatePyramid(2).also {
            it.size shouldBeEqualTo 2
            it[0] shouldBeEqualTo " # "
            it[1] shouldBeEqualTo "###"
        }
    }

    @Test
    fun `pyramid n = 3`() {
        generatePyramid(3).also {
            it.size shouldBeEqualTo 3
            it[0] shouldBeEqualTo "  #  "
            it[1] shouldBeEqualTo " ### "
            it[2] shouldBeEqualTo "#####"
        }
    }

    @Test
    fun `pyramid n = 4`() {
        generatePyramid(4).also {
            it.size shouldBeEqualTo 4
            it[0] shouldBeEqualTo "   #   "
            it[1] shouldBeEqualTo "  ###  "
            it[2] shouldBeEqualTo " ##### "
            it[3] shouldBeEqualTo "#######"
        }
    }

    @Test
    fun `pyramid n = 5`() {
        generatePyramid(5).also {
            it.size shouldBeEqualTo 5
            it[0] shouldBeEqualTo "    #    "
            it[1] shouldBeEqualTo "   ###   "
            it[2] shouldBeEqualTo "  #####  "
            it[3] shouldBeEqualTo " ####### "
            it[4] shouldBeEqualTo "#########"
        }
    }
}
