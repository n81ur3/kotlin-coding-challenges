package adventofcode2020

class Day06CustomCustoms

class CustomCustoms(val input: String) {
    val distinctAnswers: Set<Char>
    val answersCount: Int
        get() = distinctAnswers.size

    init {
        distinctAnswers = input.toSet()
    }
}

fun containsBoth(first: String, second: String): String {
    val result = first.filter { second.contains(it) }
    return result
}

class CustomCustomsGroup(val input: List<String>) {

    fun commonAnswers(): Set<Char> {
        val result = input.filterNot { it.isEmpty() }.reduce(::containsBoth)
        return result.toSet()
    }

}