package adventofcode2024

import kotlin.math.max

data class Antenna(
    val frequency: Char,
    val x: Int,
    val y: Int
) {
    fun distance(other: Antenna): Pair<Int, Int> {
        return this.x - other.x to this.y - other.y
    }

    fun antinodeFromDistance(x: Int, y: Int): Pair<Int, Int> {
        return this.x + x to this.y + y
    }
}

class Resonator(
    val input: List<String>
) {
    val antennas: List<Antenna>
    val height = input.size - 1
    val width = input[0].length - 1
    val antinodes = mutableSetOf<Pair<Int, Int>>()

    init {
        antennas = input.flatMapIndexed { yIndex, line ->
            line.mapIndexed { xIndex, c ->
                Antenna(c, xIndex, yIndex)
            }.filterNot { it.frequency == '.' }
        }
    }

    fun printResonateSpace() {
        (0..height).forEach { yIndex ->
            (0..width).forEach { xIndex ->
                if (antennas.any { it.x == xIndex && it.y == yIndex }) print(antennas.first { it.x == xIndex && it.y == yIndex }.frequency)
                else if (antinodes.any { it.first == xIndex && it.second == yIndex }) print('#')
                else print('.')
            }
            println()
        }
    }

    fun buildAntinodes(withResonance: Boolean = false): Int {
        val antennaGroups = antennas.groupBy { it.frequency }
        antennaGroups.forEach { antennaGroup ->
            antennaGroup.value.forEach { firstAntenna ->
                antennaGroup.value.forEach { secondAntenna ->
                    val distance = firstAntenna.distance(secondAntenna)
                    if (distance.first > 0 || distance.second > 0) {
                        if (withResonance) {
                            (1..max(width, height)).forEach { counter ->
                                antinodes.add(
                                    firstAntenna.antinodeFromDistance(
                                        distance.first * counter,
                                        distance.second * counter
                                    )
                                )
                                antinodes.add(
                                    secondAntenna.antinodeFromDistance(
                                        distance.first * -counter,
                                        distance.second * -counter
                                    )
                                )
                            }
                        } else {
                            antinodes.add(firstAntenna.antinodeFromDistance(distance.first, distance.second))
                            antinodes.add(secondAntenna.antinodeFromDistance(distance.first * -1, distance.second * -1))
                        }
                    }
                }
            }
        }
        antinodes.removeIf { !(antinodeInRange(it.first, it.second)) }
        return when {
            withResonance -> ((antennas.map { it.x to it.y }).toSet() + antinodes).size
            else -> antinodes.size
        }
    }

    private fun antinodeInRange(x: Int, y: Int): Boolean {
        return (0..width).contains(x) && (0..height).contains(y)
    }
}