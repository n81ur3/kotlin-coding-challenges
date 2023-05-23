package adventofcode2019

class Day06UniversalOrbitMap

data class OrbitObject(val code: String)

data class Orbit(val center: OrbitObject, val satelite: OrbitObject)

class SpaceOrbit(val orbitMap: List<String>) {
    val orbits: List<Orbit>

    init {
        orbits = orbitMap.map {
            val (center, satelite) = it.split(")").take(2)
            Orbit(OrbitObject(center), OrbitObject(satelite))
        }
    }

    fun orbitsCount(): Int {
        return orbits.sumOf { orbit -> findObjectDepth(orbit.satelite, 0) }
    }

    private fun findObjectDepth(orbitObject: OrbitObject, currentDepth: Int = 0): Int {
        val center = orbits.firstOrNull { it.satelite == orbitObject }?.center

        return if (center == null) {
            currentDepth
        } else {
            findObjectDepth(center, currentDepth + 1)
        }
    }

    fun findYouSanDistance(): Int {
        val youMap = mutableMapOf<OrbitObject, Int>()
        val sanMap = mutableMapOf<OrbitObject, Int>()

        var parent = orbits.firstOrNull { it.satelite.code == "YOU" }?.center
        var distance = 0
        while (parent != null) {
            youMap[parent] = distance++
            parent = orbits.firstOrNull { it.satelite.code == parent?.code }?.center
        }

        parent = orbits.first { it.satelite.code == "SAN" }.center
        distance = 0
        while (parent != null) {
            sanMap[parent] = distance++
            parent = orbits.firstOrNull { it.satelite.code == parent?.code }?.center
        }

        val intersection = youMap.keys.first { sanMap.contains(it) }
        val youDistance = youMap[intersection] ?: 0
        val sanDistance = sanMap[intersection] ?: 0

        return youDistance + sanDistance
    }

}