package adventofcode2020

class Day11SeatingSystem

class SeatPlan(val seats: MutableMap<Pair<Int, Int>, Char>) {

    val numberOfOccupiedSeats: Int
        get() = seats.filter { (position, type) -> type == '#' }.count()

    companion object {
        fun from(input: List<String>): SeatPlan {
            return SeatPlan(parseInput(input))
        }

        fun parseInput(lines: List<String>): MutableMap<Pair<Int, Int>, Char> {
            val result = mutableMapOf<Pair<Int, Int>, Char>()
            lines.forEachIndexed { index, line ->
                line.forEachIndexed { pos, character ->
                    result.put(index to pos, character)
                }
            }
            return result
        }
    }

    fun resettle() {
        val newPlan = mutableMapOf<Pair<Int, Int>, Char>()
        seats.forEach { (position, type) ->
            if (type == 'L' && getNeighborcountAtPosition(position) == 0) {
                newPlan.put(position, '#')
            } else if (type == '#' && getNeighborcountAtPosition(position) >= 4) {
                newPlan.put(position, 'L')
            } else {
                newPlan.put(position, type)
            }
        }
        seats.clear()
        seats.putAll(newPlan)
    }

    fun resettleWithSight() {
        val newPlan = mutableMapOf<Pair<Int, Int>, Char>()
        seats.forEach { (position, type) ->
            if (type == 'L' && getSightCountAtPosition(position) == 0) {
                newPlan.put(position, '#')
            } else if (type == '#' && getSightCountAtPosition(position) >= 5) {
                newPlan.put(position, 'L')
            } else {
                newPlan.put(position, type)
            }
        }
        seats.clear()
        seats.putAll(newPlan)
    }

    fun getNeighborcountAtPosition(position: Pair<Int, Int>): Int {
        val (row, col) = position
        var result = 0
        val neighborPositions = listOf(
            row - 1 to col - 1,
            row - 1 to col,
            row - 1 to col + 1,
            row to col - 1,
            row to col + 1,
            row + 1 to col - 1,
            row + 1 to col,
            row + 1 to col + 1
        )

        val occupiedSeats = seats.filter { seat -> seat.value == '#' }
        neighborPositions.forEach { neighbor ->
            result += if (occupiedSeats.containsKey(neighbor)) 1 else 0
        }

        return result
    }

    fun getNeighborcountAtPosition(row: Int, col: Int): Int {
        return getNeighborcountAtPosition(row to col)
    }

    fun getSightCountAtPosition(position: Pair<Int, Int>): Int {
        val (row, col) = position
        var result = 0
        val maxRow = seats.maxOf { (pos, _) -> pos.first }
        val maxCol = seats.maxOf { (pos, _) -> pos.second }
        val scanner = mutableListOf<Char>()
        var currentRow = row
        var currentCol = col

        // Up-Left
        while (currentRow >= 0 && currentCol >= 0) {
            seats.get(--currentRow to --currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)

        // Up
        scanner.clear()
        currentRow = row
        currentCol = col
        while (currentRow >= 0) {
            seats.get(--currentRow to currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)

        // Up-Right
        scanner.clear()
        currentRow = row
        currentCol = col
        while (currentRow >= 0 && currentCol <= maxCol) {
            seats.get(--currentRow to ++currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)


        // Left
        scanner.clear()
        currentRow = row
        currentCol = col
        while (currentCol >= 0) {
            seats.get(currentRow to --currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)

        // Right
        scanner.clear()
        currentRow = row
        currentCol = col
        while (currentCol <= maxCol) {
            seats.get(currentRow to ++currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)

        // Down-Left
        scanner.clear()
        currentRow = row
        currentCol = col
        while (currentRow <= maxRow && currentCol >= 0) {
            seats.get(++currentRow to --currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)

        // Down
        scanner.clear()
        currentRow = row
        currentCol = col
        while (currentRow <= maxRow) {
            seats.get(++currentRow to currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)


        // Down-Right
        scanner.clear()
        currentRow = row
        currentCol = col
        while (currentRow <= maxRow && currentCol <= maxCol) {
            seats.get(++currentRow to ++currentCol)?.let { scanner.add(it) }
        }
        result += checkLine(scanner)

        return result
    }

    private fun checkLine(line: List<Char>): Int {
        val firstSeat = line.firstOrNull { type -> type == 'L' || type == '#' }
        firstSeat?.let {
            return when(it) {
                'L' -> 0
                else -> 1
            }
        }
        return 0
    }

    fun displayPlan() {
        val maxRow = seats.keys.maxByOrNull { it.first }?.first ?: 0
        val maxCol = seats.keys.maxByOrNull { it.second }?.second ?: 0
        for (row in 0..maxRow) {
            for (col in 0..maxCol) {
                print(seats.get(row to col))
            }
            println()
        }
    }
}