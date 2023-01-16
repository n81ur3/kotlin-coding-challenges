package adventofcode2018

class Day13MineCartMadness

sealed class CartDirection {
    object UP : CartDirection()
    object RIGHT : CartDirection()
    object DOWN : CartDirection()
    object LEFT : CartDirection()
}

data class MineCoordinate(val x: Int, val y: Int) {
    fun asCsv(): String = "$x,$y"
}

data class MineCart(var x: Int, var y: Int, var currentDirection: CartDirection, var active: Boolean = true) :
    Comparable<MineCart> {
    var nextTurn = 0

    fun move(track: Char) {
        when (track) {
            '\\' -> {
                when (currentDirection) {
                    CartDirection.UP -> {
                        x--
                        currentDirection = CartDirection.LEFT
                    }

                    CartDirection.DOWN -> {
                        x++
                        currentDirection = CartDirection.RIGHT
                    }

                    CartDirection.LEFT -> {
                        y--
                        currentDirection = CartDirection.UP
                    }

                    CartDirection.RIGHT -> {
                        y++
                        currentDirection = CartDirection.DOWN
                    }
                }
            }

            '/' -> {
                when (currentDirection) {
                    CartDirection.UP -> {
                        x++
                        currentDirection = CartDirection.RIGHT
                    }

                    CartDirection.DOWN -> {
                        x--
                        currentDirection = CartDirection.LEFT
                    }

                    CartDirection.LEFT -> {
                        y++
                        currentDirection = CartDirection.DOWN
                    }

                    CartDirection.RIGHT -> {
                        y--
                        currentDirection = CartDirection.UP
                    }
                }
            }

            '|' -> {
                when (currentDirection) {
                    CartDirection.DOWN -> y++
                    else -> y--
                }
            }

            '-' -> {
                when (currentDirection) {
                    CartDirection.RIGHT -> x++
                    else -> x--
                }
            }

            '+' -> {
                when (currentDirection) {
                    CartDirection.UP -> {
                        when (nextTurn) {
                            0 -> {
                                x--
                                takeTurn()
                                currentDirection = CartDirection.LEFT
                            }

                            1 -> {
                                y--
                                takeTurn()
                            }

                            2 -> {
                                x++
                                takeTurn()
                                currentDirection = CartDirection.RIGHT
                            }
                        }
                    }

                    CartDirection.DOWN -> {
                        when (nextTurn) {
                            0 -> {
                                x++
                                takeTurn()
                                currentDirection = CartDirection.RIGHT
                            }

                            1 -> {
                                y++
                                takeTurn()
                            }

                            2 -> {
                                x--
                                takeTurn()
                                currentDirection = CartDirection.LEFT
                            }
                        }
                    }

                    CartDirection.LEFT -> {
                        when (nextTurn) {
                            0 -> {
                                y++
                                takeTurn()
                                currentDirection = CartDirection.DOWN
                            }

                            1 -> {
                                x--
                                takeTurn()
                            }

                            2 -> {
                                y--
                                takeTurn()
                                currentDirection = CartDirection.UP
                            }
                        }
                    }

                    CartDirection.RIGHT -> {
                        when (nextTurn) {
                            0 -> {
                                y--
                                takeTurn()
                                currentDirection = CartDirection.UP
                            }

                            1 -> {
                                x++
                                takeTurn()
                            }

                            2 -> {
                                y++
                                takeTurn()
                                currentDirection = CartDirection.DOWN
                            }
                        }
                    }

                }
            }
        }
    }

    private fun takeTurn() {
        nextTurn = (nextTurn + 1) % 3
    }

    override fun compareTo(other: MineCart): Int =
        when {
            y < other.y -> -1
            y > other.y -> 1
            x < other.x -> -1
            x > other.x -> 1
            else -> 0
        }
}

class Mine(input: List<String>) {
    val track: List<List<Char>>
    val carts: List<MineCart>
    val numberOfActiveCarts: Int
        get() = carts.filter { it.active }.count()

    init {
        val initialCarts = mutableListOf<MineCart>()
        val initialTrack = mutableListOf<List<Char>>()
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c in arrayOf('<', '>', '^', 'v')) {
                    when (c) {
                        '<' -> initialCarts.add(MineCart(x, y, CartDirection.LEFT))
                        '>' -> initialCarts.add(MineCart(x, y, CartDirection.RIGHT))
                        '^' -> initialCarts.add(MineCart(x, y, CartDirection.UP))
                        'v' -> initialCarts.add(MineCart(x, y, CartDirection.DOWN))
                    }
                }
            }
        }
        carts = initialCarts.toList()
        input.forEach { line ->
            val newLine = line.replace('^', '|').replace('v', '|').replace('<', '-').replace('>', '-')
            initialTrack.add(newLine.toCharArray().toList())
        }
        track = initialTrack.toList()
    }

    fun startCarts(): MineCoordinate {
        var collisionCoordinate: MineCoordinate? = null

        while (collisionCoordinate == null) {
            collisionCoordinate = tick()
            collisionCoordinate?.let {
                println("Collision at: ${collisionCoordinate.asCsv()}")
            }
        }

        return collisionCoordinate
    }

    fun startEndGame(): MineCoordinate {
        while (numberOfActiveCarts > 1) {
            tick(false)
        }

        val lastCart = carts.first { it.active }
        return MineCoordinate(lastCart.x, lastCart.y)
    }

    fun tick(detectFirstColission: Boolean = true): MineCoordinate? {
        var collisionCoordinate: MineCoordinate? = null
        carts.filter { true }.sorted().forEach {
            it.move(track[it.y][it.x])
            collisionCoordinate = detectCollision(true)
            if (detectFirstColission && collisionCoordinate != null) {
                return collisionCoordinate
            }
        }
        return collisionCoordinate
    }

    fun detectCollision(withDeactivation: Boolean = false): MineCoordinate? {
        val result = carts
            .filter { it.active }
            .groupingBy { MineCoordinate(it.x, it.y) }.eachCount()
            .filter { it.value > 1 }
            .keys.firstOrNull()
        if (withDeactivation) {
            result?.let { deactivateCartsAt(it) }
        }
        return result
    }

    private fun deactivateCartsAt(collisionCoordinate: MineCoordinate) {
        carts.filter { cart -> cart.x == collisionCoordinate.x && cart.y == collisionCoordinate.y }
            .forEach { it.active = false }
    }

    fun printTrackWithCarts() {
        val trackWithCarts = mutableListOf<MutableList<Char>>()
        track.forEach {
            trackWithCarts.add(it.toMutableList())
        }
        carts.filter { it.active }.forEach { cart ->
            when (cart.currentDirection) {
                CartDirection.UP -> trackWithCarts[cart.y][cart.x] = '^'
                CartDirection.RIGHT -> trackWithCarts[cart.y][cart.x] = '>'
                CartDirection.DOWN -> trackWithCarts[cart.y][cart.x] = 'v'
                CartDirection.LEFT -> trackWithCarts[cart.y][cart.x] = '<'
            }
        }

        trackWithCarts.forEach { line -> println(line.joinToString(separator = "")) }
    }
}