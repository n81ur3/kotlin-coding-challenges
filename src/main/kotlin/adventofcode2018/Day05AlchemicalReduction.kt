package adventofcode2018

class Day05AlchemicalReduction

fun String.isOpposite(other: String): Boolean {
    if (this.isEmpty() || other.isEmpty()) return false
    if (this.last().lowercase() != other.first().lowercase()) return false
    if (this.last().isUpperCase()) return other.first().isLowerCase()
    else return other.first().isUpperCase()
}

class ChemicalProcessor(val input: String) {

    fun processPolymer(): String {
        return input.split("").reduce { acc, ch -> if (acc.isOpposite(ch)) acc.dropLast(1) else acc + ch }
    }
}