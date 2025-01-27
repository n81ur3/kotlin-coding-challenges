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

    fun areas() {
        plants.groupBy { it.type }.forEach { plantGroup ->
            println("Group: ${plantGroup.key}: ${plantGroup.value.size}")
        }
    }

    fun fencesForPlantSpot(plantSpot: PlantSpot): Set<Pair<Int, Int>> {
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

    fun findArea(plant: PlantSpot, neighborsSoFar: Set<PlantSpot> = setOf()): Set<PlantSpot> {
        val neighbors = findNeighbors(plant).filterNot { neighborsSoFar.contains(it) || assignedAreas.contains(it) }
        assignedAreas.addAll(neighbors)
        if (neighbors.size == 0) {
            return if(neighborsSoFar.size == 0) setOf(plant) else neighborsSoFar
        }
        else {
            return neighbors.flatMap { findArea(it, (neighborsSoFar + neighbors + plant)) }.toSet()
        }
    }

    fun buildGroups(): Set<GardenArea> {
        val result = mutableSetOf<GardenArea>()
        plants.forEach { plant ->
            if (!(result.any { it.plants.contains(plant) }) && !(assignedAreas.contains(plant))) {
                result.add(GardenArea(findArea(plant)))
            }
        }
        return result
    }

    fun findNeighbors(plantSpot: PlantSpot): Set<PlantSpot> {
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

    fun totalFenceForArea(gardenArea: GardenArea): Int {
        return gardenArea.plants.sumOf { fencesForPlantSpot(it).size }
    }

    fun totalFencePriceForArea(gardenArea: GardenArea): Int {
        return gardenArea.getTotalFencePrice(totalFenceForArea(gardenArea))
    }
}