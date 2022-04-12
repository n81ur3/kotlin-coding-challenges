package google.bootcamp.example.myapp

fun String.hasSpaces(): Boolean = contains(" ")

open class AquariumPlant(val color: String, private val size: Int)

class GreenLeafyPlant(size: Int) : AquariumPlant("green", size)

fun AquariumPlant.print() = println("AquariumPlant")
fun GreenLeafyPlant.print() = println("GreenLeafyPlant")

val AquariumPlant.isGreen: Boolean
    get() = color == "green"

fun AquariumPlant?.pull() {
    this?.apply {
        println("Removing $this")
    }
}

fun main() {
    println("This is spaced".hasSpaces())
    println("  Trimmed  ".trim().hasSpaces())

    val plant: AquariumPlant = GreenLeafyPlant(size = 10)
    plant.print()
    val aquariumPlant: GreenLeafyPlant = plant as GreenLeafyPlant
    aquariumPlant.print()

    val redAquariumPlant = AquariumPlant("red", 25)
    val greenAquariumPlant = AquariumPlant("green", 26)
    println("green=${greenAquariumPlant.isGreen} - red=${redAquariumPlant.isGreen}")

    val nullAquariumPlant: AquariumPlant? = null
    val realAquariumPlant: AquariumPlant? = redAquariumPlant

    println(nullAquariumPlant.pull())
    realAquariumPlant.pull()
}