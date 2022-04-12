package google.bootcamp.example.myapp.generics

open class WaterSupply(var needsProcessing: Boolean)

class TapWater: WaterSupply(true) {
    fun addChemicalCleaners() {
        needsProcessing = false
    }
}

class FishStoreWater: WaterSupply(false)

class LakeWater: WaterSupply(true) {
    fun filter() {
        needsProcessing = false
    }
}

class Aquarium<T: WaterSupply>(val waterSupply: T) {
    fun addWater(cleaner: Cleaner<T>) {
        if (waterSupply.needsProcessing) {
           cleaner.clean(waterSupply)
        }
        check(!waterSupply.needsProcessing) { "water supply needs processing first" }
        println("adding water from $waterSupply")
    }

}

inline fun <reified R: WaterSupply> Aquarium<*>.hasWaterSupplyOfType() = waterSupply is R

inline fun <reified T: WaterSupply> WaterSupply.isOfType(): Boolean {
    println("This WaterSupply is of class: ${this.javaClass.simpleName}")
    return this is T
}

fun addItemTo(aquarium: Aquarium<WaterSupply>) = println("item added")

interface Cleaner<in T: WaterSupply> {
    fun clean(waterSupply: T)
}

class TapWaterCleaner: Cleaner<TapWater> {
    override fun clean(waterSupply: TapWater) = waterSupply.addChemicalCleaners()
}

fun <T: WaterSupply>isWaterClean(aquarium: Aquarium<T>) {
    println("aquarium water is clean: ${!aquarium.waterSupply.needsProcessing}")
}

fun genericsExample() {
    val aquarium = Aquarium(TapWater())
    isWaterClean(aquarium)
    val hasTapWaterSupply = aquarium.hasWaterSupplyOfType<WaterSupply>()
    println("Aquarium has WaterSupply of type TapWater: $hasTapWaterSupply")
    println("Aquarium of Type TapWater: ${aquarium.waterSupply.isOfType<TapWater>()}")

    val waterTank = Aquarium(FishStoreWater())
    println("Aquarium of Type TapWater: ${waterTank.waterSupply.isOfType<TapWater>()}")
}

fun main() {
    genericsExample()
}