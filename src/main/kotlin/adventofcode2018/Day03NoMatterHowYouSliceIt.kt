package adventofcode2018

class Day03NoMatterHowYouSliceIt

data class Claim(val id: Int, val x: Int, val y: Int, val width: Int, val height: Int) {

    fun getFabricCoordinates(): List<FabricCoordinate> {
        val result = mutableListOf<FabricCoordinate>()
        (x until (x + width)).forEach { xCoordinate ->
            (y until (y + height)).forEach { yCoordinate ->
                result.add(FabricCoordinate(xCoordinate, yCoordinate))
            }
        }
        return result
    }

    companion object {
        fun fromString(input: String): Claim {
            val parts = input.split(" ")
            val id = parts[0].substringAfter("#").toInt()
            val x = parts[2].split(",")[0].toInt()
            val y = parts[2].split(",")[1].substringBefore(":").toInt()
            val (width, height) = parts[3].split("x").take(2).map { it.toInt() }
            return Claim(id, x, y, width, height)
        }
    }
}

data class FabricCoordinate(val x: Int, val y: Int)

class SantaFabric(input: List<String>) {
    val claims: List<Claim>
    val squaresTaken = mutableMapOf<FabricCoordinate, Int>()
    val numberOfMultiClaims: Int
        get() = squaresTaken.count { it.value > 1 }

    init {
        claims = input.map { Claim.fromString(it) }
    }

    fun putClaims() {
        claims.forEach { claim ->
            claim.getFabricCoordinates().forEach { coordinate ->
                if (!(squaresTaken.contains(coordinate))) {
                    squaresTaken[coordinate] = 1
                } else {
                    squaresTaken[coordinate]?.let {
                        squaresTaken[coordinate] = it + 1
                    }
                }
            }
        }
    }
}