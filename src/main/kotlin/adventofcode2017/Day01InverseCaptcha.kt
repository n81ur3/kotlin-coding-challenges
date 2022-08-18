package adventofcode2017

class Day01InverseCaptcha(val captcha: String) {

    fun calculateCaptchaSum(): Int {
        val pairs = captcha.windowed(2, 1).map { it[0].toInteger() to it[1].toInteger() }.toMutableList()
        pairs.add(captcha.last().toInteger() to captcha.first().toInteger())
        return pairs.sumOfMatchingPairs()
    }

    fun calculateCaptchaSumPart2() = captcha.mapIndexed { index, char ->
        integerAtPos(index) to integerAtPos((index + captcha.length / 2) % captcha.length)
    }.sumOfMatchingPairs()

    private fun Char.toInteger(): Int = Integer.parseInt(this.toString())

    private fun List<Pair<Int, Int>>.sumOfMatchingPairs() =
        sumBy { (first, second) -> if (first == second) first else 0 }

    private fun integerAtPos(index: Int) = captcha[index].toInteger()
}