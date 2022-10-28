package adventofcode2017

class Day21FractalArt

data class FractalRule(val original: String, val transformed: String)

class FractalArt(ruleLines: List<String>) {
    val rules: List<FractalRule>
    val fractals: MutableList<Fractal> = mutableListOf()
    val activePixels: Int
        get() = fractals.sumOf { it.activePixels }
    val startingFractal = Fractal(
        mutableListOf(
            mutableListOf('.', '#', '.'),
            mutableListOf('.', '.', '#'),
            mutableListOf('#', '#', '#')
        )
    )

    init {
        rules = ruleLines.map { FractalRule(it.substringBefore(" ="), it.substringAfter("> ")) }
        fractals.add(startingFractal)
    }

    fun enhance() {
        val result = mutableListOf<Fractal>()
        fractals.forEach { fractal ->
            result.addAll(transformFractal(fractal))
        }
        fractals.clear()
        fractals.addAll(result)
    }

    fun transformFractal(origin: Fractal): List<Fractal> {
        val result = mutableListOf<Fractal>()
        if (origin.size < 4) {
            result.add(Fractal.fromLine(transform(origin)))
        } else {
            result.add(Fractal.fromLine(transform(origin.topLeft)))
            result.add(Fractal.fromLine(transform(origin.topRight)))
            result.add(Fractal.fromLine(transform(origin.bottomLeft)))
            result.add(Fractal.fromLine(transform(origin.bottomRight)))
        }

        return result
    }

    fun printFractals() {
        fractals.forEach { print(it) }
    }

    private fun transform(origin: String): String {
        return transform(Fractal.fromLine(origin))
    }

    private fun transform(origin: Fractal): String {
        val patterns = mutableListOf<String>()
        repeat(if (origin.size == 2) 4 else 8) {
            origin.rotateRight()
            patterns.add(origin.toString())
        }
        origin.flipVertical()
        patterns.add(origin.toString())
        origin.flipVertical()
        origin.flipHorizontal()
        patterns.add(origin.toString())
        origin.flipHorizontal()

        val result = rules.firstOrNull { it.original in patterns }?.transformed ?: ""
        if (result == "") {
            println("No Match")
        }
        return result
    }
}

data class Fractal(var lines: List<MutableList<Char>>) {
    val size: Int = lines[0].size
    val activePixels: Int
        get() = lines.sumOf { line -> line.count { it == '#' } }

    val topLeft: String
        get() {
            return buildString {
                append(lines[0][0])
                append(lines[0][1])
                append("/")
                append(lines[1][0])
                append(lines[1][1])
            }
        }

    val topRight: String
        get() {
            return buildString {
                append(lines[0][2])
                append(lines[0][3])
                append("/")
                append(lines[1][2])
                append(lines[1][3])
            }
        }

    val bottomLeft: String
        get() {
            return buildString {
                append(lines[2][0])
                append(lines[2][1])
                append("/")
                append(lines[3][0])
                append(lines[3][1])
            }
        }

    val bottomRight: String
        get() {
            return buildString {
                append(lines[2][2])
                append(lines[2][3])
                append("/")
                append(lines[3][2])
                append(lines[3][3])
            }
        }

    fun rotateRight() {
        if (size == 2) {
            val tmp = lines[0][0]
            lines[0][0] = lines[1][0]
            lines[1][0] = lines[1][1]
            lines[1][1] = lines[0][1]
            lines[0][1] = tmp
        } else {
            val tmp = lines[0][0]
            lines[0][0] = lines[1][0]
            lines[1][0] = lines[2][0]
            lines[2][0] = lines[2][1]
            lines[2][1] = lines[2][2]
            lines[2][2] = lines[1][2]
            lines[1][2] = lines[0][2]
            lines[0][2] = lines[0][1]
            lines[0][1] = tmp
        }
    }

    fun rotateLeft() {
        if (size == 2) {
            val tmp = lines[0][0]
            lines[0][0] = lines[0][1]
            lines[0][1] = lines[1][1]
            lines[1][1] = lines[1][0]
            lines[1][0] = tmp
        } else {
            val tmp = lines[0][0]
            lines[0][0] = lines[0][1]
            lines[0][1] = lines[0][2]
            lines[0][2] = lines[1][2]
            lines[1][2] = lines[2][2]
            lines[2][2] = lines[2][1]
            lines[2][1] = lines[2][0]
            lines[2][0] = lines[1][0]
            lines[1][0] = tmp
        }
    }

    fun flipHorizontal() {
        if (size == 2) {
            var tmp = lines[0][0]
            lines[0][0] = lines[1][0]
            lines[1][0] = tmp
            tmp = lines[0][1]
            lines[0][1] = lines[1][1]
            lines[1][1] = tmp
        } else {
            var tmp = lines[0][0]
            lines[0][0] = lines[2][0]
            lines[2][0] = tmp
            tmp = lines[0][1]
            lines[0][1] = lines[2][1]
            lines[2][1] = tmp
            tmp = lines[0][2]
            lines[0][2] = lines[2][2]
            lines[2][2] = tmp
        }
    }

    fun flipVertical() {
        if (size == 2) {
            var tmp = lines[0][0]
            lines[0][0] = lines[0][1]
            lines[0][1] = tmp
            tmp = lines[1][0]
            lines[1][0] = lines[1][1]
            lines[1][1] = tmp
        } else {
            var tmp = lines[0][0]
            lines[0][0] = lines[0][2]
            lines[0][2] = tmp
            tmp = lines[1][0]
            lines[1][0] = lines[1][2]
            lines[1][2] = tmp
            tmp = lines[2][0]
            lines[2][0] = lines[2][2]
            lines[2][2] = tmp
        }
    }

    fun displayAsGrid() {
        lines.forEach { println(it.joinToString("")) }
    }

    override fun toString(): String {
        return lines.map { it.joinToString("") }.joinToString("/")
    }

    companion object {
        fun fromLine(input: String) = Fractal(input.split("/").map { it.toCharArray().toMutableList() })
    }
}