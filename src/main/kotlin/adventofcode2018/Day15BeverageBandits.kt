package adventofcode2018

class Day15BeverageBandits

sealed class Token(val sign: Char, var posX: Int, var posY: Int) : Comparable<Token> {

    open fun nextEnemy(): Token = NullToken()

    override fun toString(): String = sign.toString()

    override fun compareTo(other: Token): Int {
        return when {
            posY < other.posY -> -1
            posY > other.posY -> 1
            posX < other.posX -> -1
            posX > other.posX -> 1
            else -> 0
        }
    }

    fun adjacentCoordinates(): List<Pair<Int, Int>> {
        return listOf(posX to posY - 1, posX + 1 to posY, posX to posY + 1, posX - 1 to posY)
    }

    companion object {

        fun fromChar(c: Char, posX: Int, posY: Int): Token {
            return when (c) {
                '#' -> Wall(posX, posY)
                '.' -> Cavern(posX, posY)
                'G' -> Goblin(posX, posY)
                'E' -> Elf(posX, posY)
                else -> NullToken()
            }
        }
    }
}

class Wall(posX: Int, posY: Int) : Token('#', posX, posY)
class Cavern(posX: Int, posY: Int) : Token('.', posX, posY)
class Goblin(posX: Int, posY: Int) : Token('G', posX, posY)
class Elf(posX: Int, posY: Int) : Token('E', posX, posY)
class NullToken : Token(' ', -1, -1)

class Cave(input: List<String>) {
    val caveMap: List<Token>
    val caveWidth = input[0].length
    val caveHeight = input.size
    val sortedFighters: List<Token>
        get() = caveMap.sorted().filter { it is Goblin || it is Elf }

    init {
        caveMap = input.flatMapIndexed { posY, line ->
            line.mapIndexed { posX, token ->
                Token.fromChar(token, posX, posY)
            }
        }
    }

    fun playRound() {
        sortedFighters.forEach { fighter ->
            val caverns = getAdjacentCaverns(fighter)
            val enemies = findEnemies(fighter)
            val targets = findTargets(fighter)
            println(caverns)
        }
    }

    private fun getAdjacentCaverns(token: Token): List<Token> {
        return caveMap.filter { it is Cavern }.filter { it.posX to it.posY in token.adjacentCoordinates() }
    }

    private fun findEnemies(token: Token): List<Token> {
        return when (token) {
            is Goblin ->  caveMap.filter { it is Elf }
            is Elf -> caveMap.filter{ it is Goblin }
            else -> emptyList()
        }
    }

    private fun findTargets(token: Token): List<Token> {
        val enemies = findEnemies(token)
        return enemies.flatMap { getAdjacentCaverns(it) }
    }

    fun printCaveMap() {
        caveMap.sorted().forEachIndexed { index, token ->
            if (index % caveWidth == 0) { println() }
            print(token)
        }
        println()
    }
}