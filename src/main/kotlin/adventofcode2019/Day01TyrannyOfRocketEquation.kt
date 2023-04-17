package adventofcode2019

class Day01TyrannyOfRocketEquation

fun massToRequiredFuel(mass: Int): Int = mass / 3 - 2

class ElfSpacecraft(input: List<String>) {
    val modules: List<Int>

    init {
        modules = input.map { it.toInt() }
    }

    fun totalFuelRequirements() = modules.sumOf(::massToRequiredFuel)
}