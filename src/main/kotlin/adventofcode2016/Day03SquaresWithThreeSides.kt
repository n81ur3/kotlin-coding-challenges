package adventofcode2016

class SquareChecker {
    fun checkTriangles(descriptions: List<String>): Int {
        return descriptions.map { Triangle.fromString(it) }.filter { it.isValid() }.count()
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
    }
}