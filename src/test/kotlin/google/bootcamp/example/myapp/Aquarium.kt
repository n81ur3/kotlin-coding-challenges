import google.bootcamp.example.myapp.Plecostomus
import google.bootcamp.example.myapp.Shark
import kotlin.math.PI

open class Aquarium(open var width: Int = 20, open var height: Int = 31, open var length: Int = 100) {

    open val shape = "rectangle"

    open var water: Double = 0.0
        get() = volume * 0.9

    open var volume: Int
        get() = width * length * height / 1000  // 1000cm^3 = 1 liter
        set(value) {
            height = value * 1000 / (width * length)
        }

    constructor(numberOfFish: Int) : this() {
        val tank = numberOfFish * 2000 * 1.1
        height = (tank / (length * width)).toInt()
    }

    init {
        println("aquarium initializing")
    }

    fun printSize() {
        println(shape)
        println("Width: $width cm Length: $length cm Height: $height")
        println("Volume: $volume 1 Water: $water l (${"%.2f".format(water / volume * 100.0)} full)")
    }

    companion object {
        init {
            println("Companion object instantiated")
        }
        const val manufacturer = "Tankmaker"
    }
}

class TowerTank(override var height: Int, var diameter: Int) :
    Aquarium(height = height, width = diameter, length = diameter) {
    override var volume: Int
        get() = (width / 2 * length / 2 * height / 1000 * PI).toInt()
        set(value) {
            height = ((value * 1000 / PI) / (width / 2 * length / 2)).toInt()
        }
    override var water: Double = volume * 0.8

    override var shape = "cylinder"
}

fun buildAquarium() {
    val aquarium6 = Aquarium(length = 25, width = 25, height = 40)
    aquarium6.printSize()
    val myTower = TowerTank(diameter = 25, height = 40)
    myTower.printSize()
    myTower.volume = 80
    myTower.printSize()
}

fun makeFish() {
    val shark = Shark()
    val plecostomus = Plecostomus()

    println("Shark: ${shark.color}")
    shark.eat()
    println("Plecostomus: ${plecostomus.color}")
    plecostomus.eat()
}

fun main() {
    //makeFish()
    val aquarium1 = Aquarium()
    val aquarium2 = Aquarium()
}

