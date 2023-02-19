package adventofcode2018

import java.lang.Math.min
import kotlin.math.max

class Day18SettlersOfTheNorthPole

sealed interface AreaElement {
    fun transform(neighbors: List<AreaElement>): AreaElement
}

@JvmInline
value class Open(val sign: Char) : AreaElement {
    override fun transform(neighbors: List<AreaElement>): AreaElement {
        return if (neighbors.filter { it is Tree }.count() > 2) Tree('|') else this
    }

    override fun toString(): String = sign.toString()
}

@JvmInline
value class Tree(val sign: Char) : AreaElement {
    override fun transform(neighbors: List<AreaElement>): AreaElement {
        return if (neighbors.filter { it is Lumberyard }.count() > 2) Lumberyard('#') else this
    }

    override fun toString(): String = sign.toString()
}

@JvmInline
value class Lumberyard(val sign: Char) : AreaElement {
    override fun transform(neighbors: List<AreaElement>): AreaElement {
        return if (neighbors.any { it is Lumberyard } && neighbors.any { it is Tree }) this else Open('.')
    }

    override fun toString(): String = sign.toString()
}

class NorthPoleArea(input: List<String>) {
    var elements: List<List<AreaElement>>
    val totalResource: Int
        get() {
            val treeCount = elements.flatten().filter { it is Tree }.count()
            val lumberyardCount = elements.flatten().filter { it is Lumberyard }.count()
            return treeCount * lumberyardCount
        }

    init {
        elements = input.map { line ->
            line.toCharArray().map { sign ->
                elementFromSign(sign)
            }
        }
    }

    fun transform(count: Int) {
        repeat(count) {
            val transformedElements = List(elements[0].size) {
                elements[it].mapIndexed { x, element -> element.transform(neighborsAt(it, x)) }
            }
            elements = transformedElements
        }
    }

    private fun elementFromSign(sign: Char): AreaElement {
        return when (sign) {
            '|' -> Tree('|')
            '#' -> Lumberyard('#')
            else -> Open('.')
        }
    }

    fun neighborsAt(posY: Int, posX: Int): List<AreaElement> {
        val minX = max(0, posX - 1)
        val maxX = min(elements[0].size - 1, posX + 1)
        val minY = max(0, posY - 1)
        val maxY = min(elements.size - 1, posY + 1)
        val result = mutableListOf<AreaElement>()
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (y == posY && x == posX) return@forEach
                result.add(elements[y][x])
            }
        }
        return result.toList()
    }

    fun printArea() {
        println()
        elements.forEach { println(it.joinToString(separator = "")) }
        println()
    }
}