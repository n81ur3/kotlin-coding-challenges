package google.bootcamp.example.myapp.decor

data class Decoration(val rocks: String)

data class Decoration2(val rocks: String, val wood: String, val diver: String)

enum class Color(val rgb: Int) {
    RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF);
}

enum class Direction(val degree: Int) {
    NORTH(0), SOUTH(180), EAST(90), WEST(270)
}

sealed class Seal

class SeaLion: Seal()
class Walrus: Seal()

fun matchSeal(seal: Seal): String {
    return when(seal) {
        is SeaLion -> "sea lion"
        is Walrus -> "walrus"
    }
}

fun makeDecoration() {
    val decoration = Decoration("granite")
    println(decoration)
    val decoration2 = Decoration("slate")
    println(decoration2)
    val decoration3 = Decoration("slate")
    println(decoration3)

    println(decoration.equals(decoration2))
    println(decoration2.equals(decoration3))

    println(decoration2 == decoration3)
    println(decoration2 === decoration3)

    val decoration4 = decoration3.copy(rocks = "stones")
    println(decoration4)

    val decoration5 = Decoration2("crystal", "wood", "diver")
    println(decoration5)
    val(rock, wood, diver) = decoration5
    println("Decoration2: Rock=$rock - Wood=$wood - Diver=$diver")
}

fun main() {
    //makeDecoration()
    for (d in Direction.values()) {
        println(d.ordinal)
        println(d.name)
        println(d.degree)
        println("-------")
    }

    println("This is a Lion: ${matchSeal(SeaLion())}")
}