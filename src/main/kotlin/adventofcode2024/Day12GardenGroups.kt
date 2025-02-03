package adventofcode2024

data class PlantSpot(
    val x: Int,
    val y: Int,
    val type: Char
)

data class GardenArea(
    val plants: Set<PlantSpot>
) {
    val totalArea = plants.size
    val type = plants.first().type

    fun getTotalFencePrice(fenceCount: Int): Int {
        return totalArea * fenceCount
    }
}

class ElveGarden(
    val input: List<String>
) {
    val plants: List<PlantSpot>
    val width = input[0].length - 1
    val height = input.size - 1
    val assignedAreas = mutableSetOf<PlantSpot>()
    val gardenAreas: Set<GardenArea>

    init {
        plants = input.flatMapIndexed { y, line ->
            line.mapIndexed { x, plant ->
                PlantSpot(x, y, plant)
            }
        }
        gardenAreas = buildGroups()
    }

    fun printGardenLayout() {
        (0..height).forEach { y ->
            (0..width).forEach { x ->
                print(plants.first { it.x == x && it.y == y }.type)
            }
            println()
        }
    }

    private fun fencesForPlantSpot(plantSpot: PlantSpot): Set<Pair<Int, Int>> {
        val fences = mutableSetOf<Pair<Int, Int>>()
        val neighbors = findNeighbors(plantSpot)
        if (plantSpot.x == 0 || neighbors.none { it.x == plantSpot.x - 1 && it.y == plantSpot.y }) {
            fences.add(plantSpot.x - 1 to plantSpot.y)
        }
        if (plantSpot.x == width || neighbors.none { it.x == plantSpot.x + 1 && it.y == plantSpot.y }) {
            fences.add(plantSpot.x + 1 to plantSpot.y)
        }
        if (plantSpot.y == 0 || neighbors.none { it.y == plantSpot.y - 1 && it.x == plantSpot.x }) {
            fences.add(plantSpot.x to plantSpot.y - 1)
        }
        if (plantSpot.y == height || neighbors.none { it.y == plantSpot.y + 1 && it.x == plantSpot.x }) {
            fences.add(plantSpot.x to plantSpot.y + 1)
        }
        return fences
    }

    private fun findArea(plant: PlantSpot, neighborsSoFar: Set<PlantSpot> = setOf()): Set<PlantSpot> {
        val neighbors = findNeighbors(plant).filterNot { neighborsSoFar.contains(it) || assignedAreas.contains(it) }
        assignedAreas.addAll(neighbors)
        if (neighbors.size == 0) {
            return if (neighborsSoFar.size == 0) setOf(plant) else neighborsSoFar
        } else {
            return neighbors.flatMap { findArea(it, (neighborsSoFar + neighbors + plant)) }.toSet()
        }
    }

    private fun buildGroups(): Set<GardenArea> {
        val result = mutableSetOf<GardenArea>()
        plants.forEach { plant ->
            if (!(result.any { it.plants.contains(plant) }) && !(assignedAreas.contains(plant))) {
                result.add(GardenArea(findArea(plant)))
            }
        }
        return result
    }

    private fun findNeighbors(plantSpot: PlantSpot): Set<PlantSpot> {
        val top = plants.filter { it.type == plantSpot.type }
            .firstOrNull { it.x == plantSpot.x && it.y == plantSpot.y - 1 }
        val down = plants.filter { it.type == plantSpot.type }
            .firstOrNull { it.x == plantSpot.x && it.y == plantSpot.y + 1 }
        val right = plants.filter { it.type == plantSpot.type }
            .firstOrNull { it.x == plantSpot.x + 1 && it.y == plantSpot.y }
        val left = plants.filter { it.type == plantSpot.type }
            .firstOrNull { it.x == plantSpot.x - 1 && it.y == plantSpot.y }
        return setOf(top, down, right, left).filterNotNull().toSet()
    }

    fun totalFencePriceForGarden(): Int {
        return gardenAreas.sumOf { area -> totalFencePriceForArea(area) }
    }

    private fun totalFenceForArea(gardenArea: GardenArea): Int {
        return gardenArea.plants.sumOf { fencesForPlantSpot(it).size }
    }

    private fun totalFencePriceForArea(gardenArea: GardenArea): Int {
        return gardenArea.getTotalFencePrice(totalFenceForArea(gardenArea))
    }

    fun totalDiscountedFencePriceForGarden(): Int {
        return gardenAreas.sumOf { area -> totalDiscountedFencePriceForArea(area) }
    }

    private fun totalDiscountedFencePriceForArea(gardenArea: GardenArea): Int {
        return gardenArea.totalArea * gardenAreaCornerCount(gardenArea)
    }

    private fun gardenAreaCornerCount(gardenArea: GardenArea): Int {
        return gardenArea.plants.sumOf { cornerCount(it) }
    }

    private fun cornerCount(plantSpot: PlantSpot): Int {
        val north = plants.firstOrNull { it.x == plantSpot.x && it.y == plantSpot.y - 1 }?.type ?: '-'
        val south = plants.firstOrNull { it.x == plantSpot.x && it.y == plantSpot.y + 1 }?.type ?: '-'
        val west = plants.firstOrNull { it.x == plantSpot.x - 1 && it.y == plantSpot.y }?.type ?: '-'
        val east = plants.firstOrNull { it.x == plantSpot.x + 1 && it.y == plantSpot.y }?.type ?: '-'

        val northEast = plants.firstOrNull { it.x == plantSpot.x + 1 && it.y == plantSpot.y - 1 }?.type ?: '-'
        val eastSouth = plants.firstOrNull { it.x == plantSpot.x + 1 && it.y == plantSpot.y + 1 }?.type ?: '-'
        val southWest = plants.firstOrNull { it.x == plantSpot.x - 1 && it.y == plantSpot.y + 1 }?.type ?: '-'
        val westNorth = plants.firstOrNull { it.x == plantSpot.x - 1 && it.y == plantSpot.y - 1 }?.type ?: '-'

        val outerCornerCount = checkOuterCorner(north, south, west, east, plantSpot.type)
        val innerCornerCount =
            checkInnerCorner(north, south, west, east, northEast, eastSouth, southWest, westNorth, plantSpot.type)

        return outerCornerCount + innerCornerCount
    }

    private fun checkOuterCorner(north: Char, south: Char, west: Char, east: Char, target: Char): Int {
        var result = 0
        if (north != target && east != target) result++
        if (east != target && south != target) result++
        if (south != target && west != target) result++
        if (west != target && north != target) result++
        return result
    }

    private fun checkInnerCorner(
        north: Char,
        south: Char,
        west: Char,
        east: Char,
        northEast: Char,
        eastSouth: Char,
        southWest: Char,
        westNorth: Char,
        target: Char
    ): Int {
        var result = 0
        if (north == target && east == target && northEast != target) result++
        if (east == target && south == target && eastSouth != target) result++
        if (south == target && west == target && southWest != target) result++
        if (west == target && north == target && westNorth != target) result++
        return result
    }
}