package adventofcode2016

class SquareChecker {
    fun checkTriangles(descriptions: List<String>): Int {
        return descriptions.map { Triangle.fromString(it) }.filter { it.isValid() }.count()
    }

    fun checkTrianglesVertically(descriptions: List<String>): Int {
        val sides = mutableListOf<Int>()
        val sanizitzed = descriptions.map { it.trim().split("\\s+".toRegex()).map { it.toInt() } }
        sanizitzed.forEach { sides.add(it[0]) }
        sanizitzed.forEach { sides.add(it[1]) }
        sanizitzed.forEach { sides.add(it[2]) }
        val triangles = sides.windowed(3, 3).map { Triangle.fromInts(it) }
        return triangles.filter { it.isValid() }.count()
    }
}

data class Triangle(
    val a: Int,
    val b: Int,
    val c: Int
) {

    fun isValid() = (a + b) > c

    companion object {
        fun fromString(description: String): Triangle {
            val sides = description.trim().split("\\s+".toRegex()).map { it.toInt() }.sorted()
            return Triangle(sides[0], sides[1], sides[2])

        }

        fun fromInts(sides: List<Int>): Triangle {
            val (a, b, c) = listOf(sides[0], sides[1], sides[2]).sorted()
            return Triangle(a, b, c)
        }
    }
}