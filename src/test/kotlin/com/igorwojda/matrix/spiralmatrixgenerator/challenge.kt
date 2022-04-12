package com.igorwojda.matrix.spiralmatrixgenerator

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

private fun generateSpiralMatrix(n: Int): List<MutableList<Int?>> {
    var x = -1
    var y = 0
    var direction = Direction.RIGHT

    val matrix = Array(n) { IntArray(n) { 0 } }
    var counter = 1

    while (counter <= n * n) {

        if (turnRequired(matrix, x, y, direction)) {
            when (direction) {
                Direction.RIGHT -> {
                    y++; direction = Direction.DOWN
                }
                Direction.DOWN -> {
                    x--; direction = Direction.LEFT
                }
                Direction.LEFT -> {
                    y--; direction = Direction.UP
                }
                Direction.UP -> {
                    x++; direction = Direction.RIGHT
                }
            }
        } else {
            when (direction) {
                Direction.RIGHT -> x++
                Direction.DOWN -> y++
                Direction.LEFT -> x--
                Direction.UP -> y--
            }
        }

        matrix[y][x] = counter++
    }

    val result = mutableListOf(mutableListOf<Int?>())
    for (line in matrix) {
        result.add(line.toMutableList() as MutableList<Int?>)
    }
    result.removeAt(0)
    return result.toList()
}

enum class Direction { RIGHT, DOWN, LEFT, UP }

private fun turnRequired(matrix: Array<IntArray>, x: Int, y: Int, direction: Direction): Boolean {
    return when (direction) {
        Direction.RIGHT -> x == matrix.size - 1 || matrix[x + 1][y] > 0
        Direction.DOWN -> y == matrix.size - 1 || matrix[x][y + 1] > 0
        Direction.LEFT -> x == 0 || matrix[y][x - 1] > 0
        Direction.UP -> y == 0 || matrix[y - 1][x] > 0
    }
}

private class Test {

    @Test
    fun matrixGeneratorTest() {
        generateSpiralMatrix(3)
    }

    @Test
    fun `generateSpiralMatrix generates a 2x2 matrix`() {
        val matrix = generateSpiralMatrix(2)
        matrix.size shouldBeEqualTo 2
        matrix[0] shouldBeEqualTo listOf(1, 2)
        matrix[1] shouldBeEqualTo listOf(4, 3)
    }

    @Test
    fun `generateSpiralMatrix generates a 3x3 matrix`() {
        val matrix = generateSpiralMatrix(3)
        matrix.size shouldBeEqualTo 3
        matrix[0] shouldBeEqualTo listOf(1, 2, 3)
        matrix[1] shouldBeEqualTo listOf(8, 9, 4)
        matrix[2] shouldBeEqualTo listOf(7, 6, 5)
    }

    @Test
    fun `generateSpiralMatrix generates a 4x4 matrix`() {
        val matrix = generateSpiralMatrix(4)
        matrix.size shouldBeEqualTo 4
        matrix[0] shouldBeEqualTo listOf(1, 2, 3, 4)
        matrix[1] shouldBeEqualTo listOf(12, 13, 14, 5)
        matrix[2] shouldBeEqualTo listOf(11, 16, 15, 6)
        matrix[3] shouldBeEqualTo listOf(10, 9, 8, 7)
    }

    @Test
    fun generate20x20matrix() {
        generateSpiralMatrix(20)
    }
}
