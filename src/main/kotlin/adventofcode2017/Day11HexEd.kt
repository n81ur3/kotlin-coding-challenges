package adventofcode2017

import adventofcode2017.HexDirection.*

class Day11HexEd

enum class HexDirection {
    N, NE, SE, S, SW, NW, NOP
}

class HexWalker(var curX: Int = 0, var curY: Int = 0) {
    companion object {
        val directionMap = mapOf(
            "n" to N,
            "ne" to NE,
            "se" to SE,
            "s" to S,
            "sw" to SW,
            "nw" to NW
        )
    }

    fun printCurrentPosition() {
        println("HexWalker at [$curX / $curY]")
    }

    fun setToStart() {
        curX = 0
        curY = 0
    }

    fun walkPath(path: String) {
        val steps = path.split(",")
        steps.forEach { step(directionMap[it] ?: NOP) }
    }

    fun getFewestStepCountToStart(): Int {
        val xAbs = Math.abs(curX)
        val yAbs = Math.abs(curY)
        if (xAbs > yAbs) {
            val diff = xAbs - yAbs
            return (diff / 2) + (xAbs - diff)
        } else if (yAbs > xAbs) {
            val diff = yAbs - xAbs
            return (diff / 2) + (yAbs -diff)
        } else {
            return xAbs
        }
    }

    fun step(direction: HexDirection) {
        when (direction) {
            NOP -> return
            N -> curY += 2
            NE -> {
                curX++
                curY++
            }

            SE -> {
                curX++
                curY--
            }

            S -> curY -= 2
            SW -> {
                curX--
                curY--
            }

            NW -> {
                curX--
                curY++
            }
        }
    }
}