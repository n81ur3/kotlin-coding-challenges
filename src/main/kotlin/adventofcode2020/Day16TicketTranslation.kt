package adventofcode2020

class Day17TickeTranslation

data class CodeRange(val name: String, val firstRange: IntRange, val secondRange: IntRange) {
    fun isValidNumber(number: Int) = number in firstRange || number in secondRange
}

class TicketReader {
    val validRanges = mutableListOf<CodeRange>()
    val validTickets = mutableListOf<List<Int>>()

    // Input examples:
    // arrival track: 26-681 or 703-953
    // class: 49-293 or 318-956
    fun readRangeInput(input: String) {
        val rangeName = input.substringBefore(":")
        val firstInputRange = input.substringAfter(": ").substringBefore(" or").split("-")
        val secondInputRange = input.substringAfter("or ").split("-")
        val firstRange = IntRange(firstInputRange[0].toInt(), firstInputRange[1].toInt())
        val secondRange = IntRange(secondInputRange[0].toInt(), secondInputRange[1].toInt())
        validRanges.add(CodeRange(rangeName, firstRange, secondRange))
    }

    fun validateNumber(number: Int): Boolean = (validRanges.any { range -> range.isValidNumber(number) })

    fun validateTicket(ticket: String): Boolean {
        val numbers = ticket.split(",").map(String::toInt)
        return numbers.all { number -> validRanges.any { range -> range.isValidNumber(number) } }
    }

    fun readValidTicket(ticket: String) {
        validTickets.add(ticket.split(",").map(String::toInt))
    }

    fun determineValidRangeAtIndex(): MutableList<Pair<Int, String>> {
        val numbersToCheck = mutableListOf<Int>()
        var rangeMap = mutableMapOf<Int, MutableList<String>>()
        (0..19).forEach { rangeMap.put(it, mutableListOf()) }
        for (index in 0..19) {
            for (ticket in validTickets) {
                numbersToCheck.add(ticket[index])
            }

            for (range in validRanges) {
                if (numbersToCheck.all { number -> range.isValidNumber(number) }) rangeMap.get(index)?.add(range.name)
            }
            numbersToCheck.clear()
        }

        val rangeIndexes = mutableListOf<Pair<Int, String>>()
        while (rangeMap.isNotEmpty()) {
            var nextRange = ""
            rangeMap.forEach { (index, list) -> if (list.size == 1) {
                rangeIndexes.add(index to list.first())
                nextRange = list.first()
            } }
            rangeMap.values.forEach {it.removeIf { name -> name == nextRange } }
            rangeMap = rangeMap.filterNot { entry -> entry.value.isEmpty() }.toMutableMap()
        }

        return rangeIndexes
    }
}