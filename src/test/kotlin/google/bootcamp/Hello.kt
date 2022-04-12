package google.bootcamp

import java.util.*
import kotlin.random.Random.Default.nextInt

fun feedTheFish() {
    val day = randomDay()
    val food = fishFood(day)
    println("Today is $day and the fish eat $food")
}

private fun randomDay(): String {
    val week = listOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    )
    return week.random()
}

fun fishFood(day: String): String {
    return when (day) {
        "Monday" -> "flakes"
        "Tuesday" -> "pellets"
        "Wednesday" -> "redworms"
        "Thursday" -> "granules"
        "Friday" -> "mosquitoes"
        "Saturday" -> "lettuce"
        "Sunday" -> "planton"
        else -> " no food today"
    }
}

fun swim(speed: String = "fast") {
    println("swimming $speed")
}

fun shouldChangeWater(day: String, temperature: Int = 22, dirty: Int = 20): Boolean {
    return when {
        day == "Sunday" -> true
        temperature > 30 -> true
        dirty > 22 -> true
        else -> false
    }
    //return ( day == "Sunday" || temperature > 30 || dirty > 22)
}

fun shouldChangeWaterCompact(day: String, temperature: Int = 22, dirty: Int = getDirtySensor()) =
    isSunday(day) || isHot(temperature) || isDirty(dirty)

private fun getDirtySensor(): Int = Random().nextInt(40)
private fun isDirty(dirty: Int) = dirty > 22
private fun isHot(temperature: Int) = temperature > 30
private fun isSunday(day: String) = day == "Sunday"

val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")

fun main() {
    val numbers = listOf(12, 34, 43, 42, 55, 423, 789, 234, 4, 2, 234, 89)
    val partition = numbers.partition { (it % 2) == 0 }
    println("Even numbers: ${partition.first}")
    println("Odd numbers: ${partition.second}")
}