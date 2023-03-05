package adventofcode2018

import adventofcode2018.AreaElement.*
import java.lang.Math.min
import java.util.*
import kotlin.math.max

class Day18SettlersOfTheNorthPole

sealed class AreaElement(val sign: Char) {
    object Open : AreaElement('.')
    object Tree : AreaElement('|')
    object Lumberyard : AreaElement('#')

    override fun toString(): String = sign.toString()
}

fun List<List<AreaElement>>.hash(): Int {
    return Arrays.deepHashCode(this.toTypedArray())
}

class NorthPoleArea(input: List<String>) {
    var elements: List<List<AreaElement>>
    val rounds = mutableMapOf<Int, List<List<AreaElement>>>()
    val roundHashes = mutableMapOf<Int, Int>()
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

    private fun elementFromSign(sign: Char): AreaElement {
        return when (sign) {
            '|' -> Tree
            '#' -> Lumberyard
            else -> Open
        }
    }

    fun transform(count: Long) {
        var loop = 0
        var counter = 0
        while (loop == 0) {
            val transformedElements = List(elements.size) {
                elements[it].mapIndexed { x, element -> transformElement(element, it, x) }
            }
            if (roundHashes.values.contains(transformedElements.hash())) {
                println("Loop found via hash at $counter")
            }
            if (rounds.values.contains(transformedElements)) {
                loop = counter
                println("Loop at iteration: $counter")
            } else {
                rounds[counter] = transformedElements
                roundHashes[counter] = transformedElements.hash()
                counter++
            }
            elements = transformedElements
        }
        if (loop > 0) {
            val effectiveRound = Math.floorMod(count, loop) - 1
            println("Effective round: $effectiveRound")
            elements = rounds[effectiveRound]!!
        }
    }

    private fun transformElement(element: AreaElement, posY: Int, posX: Int): AreaElement {
        return when (element) {
            is Open -> transformOpen(posY, posX)
            is Tree -> transformTree(posY, posX)
            is Lumberyard -> transformLumberyard(posY, posX)
        }
    }

    private fun transformTree(posY: Int, posX: Int) =
        if (neighborsAt(posY, posX).filter { it is Lumberyard }.count() > 2) Lumberyard else Tree

    private fun transformOpen(posY: Int, posX: Int) =
        if (neighborsAt(posY, posX).filter { it is Tree }.count() > 2) Tree else Open

    private fun transformLumberyard(posY: Int, posX: Int): AreaElement {
        val containsLumberyard = neighborsAt(posY, posX).any { it is Lumberyard }
        val containsTree = neighborsAt(posY, posX).any { it is Tree }
        return if (containsLumberyard && containsTree) Lumberyard else Open
    }

    fun neighborsAt(posY: Int, posX: Int): List<AreaElement> {
        val minX = max(0, posX - 1)
        val maxX = min(elements[0].size - 1, posX + 1)
        val minY = max(0, posY - 1)
        val maxY = min(elements.size - 1, posY + 1)
        val result = mutableListOf<AreaElement>()
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if (!(y == posY && x == posX)) result.add(elements[y][x])
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