package adventofcode2019

class Day01TyrannyOfRocketEquation

fun massToRequiredFuel(mass: Int): Int = mass / 3 - 2

fun massToRequiredFuelRec(mass: Int): Int {
    val addOn = massToRequiredFuel(mass)
    return if (addOn < 6) addOn
    else {
        addOn + massToRequiredFuelRec(addOn)
    }
}

class ElfSpacecraft(input: List<String>) {
    val modules: List<Int>

    init {
        modules = input.map { it.toInt() }
    }

    fun totalFuelRequirements() = modules.sumOf(::massToRequiredFuel)

    fun totalFuelRequirementsRec() = modules.sumOf(::massToRequiredFuelRec)
}